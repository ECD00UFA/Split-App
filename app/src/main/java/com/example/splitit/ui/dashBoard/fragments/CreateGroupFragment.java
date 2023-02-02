package com.example.splitit.ui.dashBoard.fragments;

import static com.example.splitit.apis.repositories.AuthenticationRepository.ERROR;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;

import com.example.splitit.R;
import com.example.splitit.app.BaseActivity;
import com.example.splitit.app.BaseFragment;
import com.example.splitit.app.SplititApplication;
import com.example.splitit.database.data_models.Friend;
import com.example.splitit.database.data_models.Group;
import com.example.splitit.databinding.FragmentCreateGroupBinding;
import com.example.splitit.ui.dashBoard.adapters.GroupsTypeAdapter;
import com.example.splitit.ui.dashBoard.dataModels.GroupsTypeDataModel;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupFragment extends BaseFragment {
    FragmentCreateGroupBinding binding;
    GroupsTypeAdapter groupsTypeAdapter;
    ArrayList<GroupsTypeDataModel> groupsTypeDataModels;
    private List<Friend> groupMemberList;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_group, container, false);
            binding.headerView.headerSideImg.setImageResource(R.drawable.ic_close);
            binding.headerView.doneTxt.setVisibility(View.VISIBLE);
            binding.headerView.settingImg.setVisibility(View.GONE);

            groupsTypeDataModels = new ArrayList<>();
            groupsTypeDataModels.add(new GroupsTypeDataModel(R.drawable.ic_trip, "Trip"));
            groupsTypeDataModels.add(new GroupsTypeDataModel(R.drawable.ic_home, "Home"));
            groupsTypeDataModels.add(new GroupsTypeDataModel(R.drawable.ic_couple, "Couple"));
            groupsTypeDataModels.add(new GroupsTypeDataModel(R.drawable.ic_others, "Other"));
            groupsTypeAdapter = new GroupsTypeAdapter(mContext, groupsTypeDataModels);
            binding.groupTypeRev.setAdapter(groupsTypeAdapter);
        }
        init();
        setEvents();
        return binding.getRoot();
    }

    private void init() {
        addAChip(SplititApplication.getPreferenceManger().getUserDetails().getName());
        if (groupMemberList == null) {
            groupMemberList = new ArrayList<>();
            groupMemberList.add(new Friend(SplititApplication.getPreferenceManger().getUserDetails().getName(), ""));
        }
        if (getArguments() != null) {
            String items = getArguments().getString("selected_contacts");
            List<Friend> groupMembers = new Gson().fromJson(items, new TypeToken<List<Friend>>() {
            }.getType());
            groupMemberList.addAll(groupMembers);
            for (Friend groupMember : groupMembers) {
                addAChip(groupMember.friendName);
            }
        }

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
    }

    private void addAChip(String title) {
        Chip chip = new Chip(requireContext());
        chip.setText(title);
        chip.setChipBackgroundColorResource(R.color.appIconColor);
        chip.setCloseIconVisible(true);
        chip.setTextColor(getResources().getColor(R.color.appTextColor));
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.rvGroupMembers.removeView(view);
            }
        });
        binding.rvGroupMembers.addView(chip);
    }

    private void setEvents() {
        binding.headerView.doneTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.groupNameEdt.getText().toString().isEmpty()) {
                    progressDialog.show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();
                    Group myGroup = new Group(binding.groupNameEdt.getText().toString(), binding.etDescription.getText().toString(), groupsTypeAdapter.selectedType.getTypeTxt());
                    myRef.child("Groups").child(myGroup.groupId).setValue(myGroup);
                    for(Friend item: groupMemberList){
                        myRef.child("Groups").child(myGroup.groupId).child("groupMembers").child(item.friendId).setValue(item);
                    }

                    progressDialog.dismiss();
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.homeFragment);
                } else {
                    ((BaseActivity) mContext).showAlertDialog(ERROR, "Please add a group title!", "OK", "");
                }
            }
        });

        binding.headerView.headerSideImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.homeFragment);
            }
        });

        binding.icAddMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddMembersClicked();
            }
        });

        binding.tvLblAddMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddMembersClicked();
            }
        });

        binding.icInviteMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inviteMembers();
            }
        });

        binding.tvLblInviteMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inviteMembers();
            }
        });

        binding.profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void onAddMembersClicked() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            moveToContactListFragment();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS);

        }
    }

    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result) {
                moveToContactListFragment();
            } else {
                // PERMISSION NOT GRANTED
            }
        }
    });

    private void inviteMembers() {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:"));
        sendIntent.putExtra("sms_body", "custom link to invite people to group will appear here as sms body");
        startActivity(sendIntent);

    }

    private void moveToContactListFragment() {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.addMembersFragment);
    }
}