package com.example.splitit.ui.dashBoard.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;

import com.example.splitit.R;
import com.example.splitit.app.BaseFragment;
import com.example.splitit.database.data_models.Expense;
import com.example.splitit.database.data_models.Group;
import com.example.splitit.databinding.FragmentHomeBinding;
import com.example.splitit.ui.dashBoard.adapters.ExpensesAdapter;
import com.example.splitit.ui.dashBoard.dataModels.ExpensesDataModel;
import com.example.splitit.utils.AmountTextWatcher;
import com.example.splitit.utils.ViewUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {
    FragmentHomeBinding binding;
    ArrayList<ExpensesDataModel> expensesDataModelArrayList;
    ExpensesAdapter expensesAdapter;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
            binding.startGroupView.setBackground(ViewUtil.setViewBgDiffRadiusWithBorder(mContext, 5, 5, 5, 5, R.color.transparent, R.color.appYellow, 2));
            expensesDataModelArrayList = new ArrayList<>();
            expensesAdapter = new ExpensesAdapter(mContext, expensesDataModelArrayList);
            binding.expensesRev.setAdapter(expensesAdapter);
            binding.startGroupView.setOnClickListener(v -> {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.createGroupFragment);

            });
        }
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        getExistingGroups();
        return binding.getRoot();

    }

    private void getExistingGroups() {
        progressDialog.show();
        Query myTopPostsQuery = FirebaseDatabase.getInstance().getReference("Groups");
        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                expensesDataModelArrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Group group = postSnapshot.getValue(Group.class);
                    if (group != null) {
                        long totalExpenseAmount = 0L;
                        for (String key : group.expenses.keySet()) {
                            Expense expense = group.expenses.get(key);
                            if (expense != null) {
                                totalExpenseAmount += Long.parseLong(expense.expenseAmount.replace("$", "").replace(".", "").replace(",", ""));
                            }
                        }
                        expensesDataModelArrayList.add(new ExpensesDataModel(R.drawable.profile_img, group.groupName,
                                totalExpenseAmount > 0 ? ("Total expense: " + AmountTextWatcher.getThousandFormattedLong(totalExpenseAmount, "$")) : "no expenses"));
                    }
                }
                expensesAdapter.notifyDataSetChanged();
                myTopPostsQuery.removeEventListener(this);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

