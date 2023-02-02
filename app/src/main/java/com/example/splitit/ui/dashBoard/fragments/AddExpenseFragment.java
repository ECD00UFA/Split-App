package com.example.splitit.ui.dashBoard.fragments;

import static com.example.splitit.apis.repositories.AuthenticationRepository.ERROR;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;

import com.example.splitit.R;
import com.example.splitit.app.BaseActivity;
import com.example.splitit.app.BaseFragment;
import com.example.splitit.app.SplititApplication;
import com.example.splitit.database.data_models.Expense;
import com.example.splitit.database.data_models.Group;
import com.example.splitit.databinding.LayoutFragmentAddExpenseBinding;
import com.example.splitit.ui.dashBoard.activities.MainActivity;
import com.example.splitit.utils.AmountTextWatcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddExpenseFragment extends BaseFragment {
    LayoutFragmentAddExpenseBinding binding;
    ArrayList<Group> groupsList;
    ProgressDialog progressDialog;
    private Group selectedGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_add_expense, container, false);
            binding.headerView.headTxt.setText("Add expense");
            binding.etExpense.addTextChangedListener(new AmountTextWatcher(binding.etExpense, "$"));
        }

        if (getActivity() != null) {
            ((MainActivity) getActivity()).hideExpenseViewFab();
        }

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        getExistingGroups();
        setEvents();
        return binding.getRoot();

    }

    private void setEvents() {
        binding.headerView.doneTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.etExpense.getText().toString().isEmpty()) {
                    progressDialog.show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();
                    Expense expense = new Expense(binding.etExpense.getText().toString(), SplititApplication.getPreferenceManger().getUserDetails().getName());
                    myRef.child("Groups").child(selectedGroup.groupId).child("expenses").child(expense.expenseId).setValue(expense);
                    progressDialog.dismiss();
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.homeFragment);
                } else {
                    ((BaseActivity) mContext).showAlertDialog(ERROR, "Please add an amount!", "OK", "");
                }
            }
        });

        binding.headerView.headerSideImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.homeFragment);
            }
        });

        binding.icSelectGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGroupSelectDialog();
            }
        });

        binding.groupNameEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGroupSelectDialog();
            }
        });
    }

    private void showGroupSelectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select a group").setItems(getGroupTitles(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                selectedGroup = groupsList.get(which);
                binding.groupNameEdt.setText(selectedGroup.groupName);
                dialog.dismiss();
            }
        });
        builder.create().show();

    }

    private CharSequence[] getGroupTitles() {
        List<CharSequence> groupNames = new ArrayList<>();
        for (Group item : groupsList) {
            groupNames.add(item.groupName);
        }
        return groupNames.toArray(new CharSequence[0]);
    }

    private void getExistingGroups() {
        progressDialog.show();
        Query myTopPostsQuery = FirebaseDatabase.getInstance().getReference("Groups");
        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (groupsList == null) {
                    groupsList = new ArrayList<>();
                }
                groupsList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    groupsList.add(postSnapshot.getValue(Group.class));
                }
                myTopPostsQuery.removeEventListener(this);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
