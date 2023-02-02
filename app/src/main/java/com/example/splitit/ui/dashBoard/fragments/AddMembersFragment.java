package com.example.splitit.ui.dashBoard.fragments;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.splitit.R;
import com.example.splitit.app.BaseFragment;
import com.example.splitit.database.data_models.Friend;
import com.example.splitit.databinding.FragmentAddMembersBinding;
import com.example.splitit.ui.dashBoard.activities.MainActivity;
import com.example.splitit.ui.dashBoard.adapters.FriendListAdapter;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AddMembersFragment extends BaseFragment implements FriendListAdapter.onItemClicked {
    FragmentAddMembersBinding binding;
    FriendListAdapter adapter;
    ArrayList<Friend> contacts;
    ArrayList<Friend> selectedContacts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_members, container, false);
        }
        if (getActivity() != null) {
            ((MainActivity) getActivity()).hideExpenseViewFab();
        }
        setupRecyclerView();
        setEvents();
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        binding.rvContacts.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new FriendListAdapter(requireContext(), getContacts(), this);
        binding.rvContacts.setAdapter(adapter);
    }

    private void setEvents() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        binding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String items = new Gson().toJson(selectedContacts);
                Bundle b = new Bundle();
                b.putString("selected_contacts", items);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.createGroupFragment, b);
            }
        });
    }

    private ArrayList<Friend> getContacts() {
        ContentResolver cr = requireActivity().getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        contacts = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contacts.add(new Friend(name, number));
            }
            cursor.close();
        }

        return contacts;
    }

    @Override
    public void onItemClicked(Friend friend) {
        if (selectedContacts == null) {
            selectedContacts = new ArrayList<>();
        }
        selectedContacts.add(friend);
        Chip chip = new Chip(requireContext());
        chip.setText(friend.friendName);
        chip.setChipBackgroundColorResource(R.color.appIconColor);
        chip.setCloseIconVisible(true);
        chip.setTextColor(getResources().getColor(R.color.appTextColor));
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Friend item = new Friend(chip.getText().toString(), "");
                selectedContacts.remove(item);
                binding.chipGroup.removeView(view);
            }
        });
        binding.chipGroup.addView(chip);
    }

    private void filter(String text) {
        ArrayList<Friend> filteredlist = new ArrayList<Friend>();
        for (Friend item : contacts) {
            if (item.friendName.toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {

        } else {
            adapter.filterList(filteredlist);
        }
    }
}