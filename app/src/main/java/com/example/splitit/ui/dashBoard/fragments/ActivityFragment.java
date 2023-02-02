package com.example.splitit.ui.dashBoard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.splitit.R;
import com.example.splitit.app.BaseFragment;
import com.example.splitit.databinding.FragmentActivityBinding;
import com.example.splitit.ui.dashBoard.adapters.ActivityAdapter;
import com.example.splitit.ui.dashBoard.dataModels.ActivityDataModel;

import java.util.ArrayList;

public class ActivityFragment extends BaseFragment {

    FragmentActivityBinding binding;
    ActivityAdapter activityAdapter;
    ArrayList<ActivityDataModel> activityDataModelArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activity, container, false);

            activityDataModelArrayList = new ArrayList<>();
            activityDataModelArrayList.add(new ActivityDataModel(R.drawable.profile_img,"You created the group 'Raah'","Today, 6:31 AM","You get Back INR 100.00"));
            activityDataModelArrayList.add(new ActivityDataModel(R.drawable.profile_img,"You created the group 'Raah'","Today, 6:31 AM","You get Back INR 100.00"));
            activityDataModelArrayList.add(new ActivityDataModel(R.drawable.profile_img,"You created the group 'Raah'","Today, 6:31 PM","You get Back INR 100.00"));
            activityDataModelArrayList.add(new ActivityDataModel(R.drawable.profile_img,"You created the group 'Raah'","Today, 6:31 AM","You get Back INR 100.00"));
            activityDataModelArrayList.add(new ActivityDataModel(R.drawable.profile_img,"You created the group 'Raah'","Today, 6:31 AM","You get Back INR 100.00"));
            activityDataModelArrayList.add(new ActivityDataModel(R.drawable.profile_img,"You created the group 'Raah'","Today, 6:31 PM","You get Back INR 100.00"));
            activityDataModelArrayList.add(new ActivityDataModel(R.drawable.profile_img,"You created the group 'Raah'","Today, 6:31 AM","You get Back INR 100.00"));
            activityDataModelArrayList.add(new ActivityDataModel(R.drawable.profile_img,"You created the group 'Raah'","Yesterday, 6:31 PM","You get Back INR 100.00"));
            activityDataModelArrayList.add(new ActivityDataModel(R.drawable.profile_img,"You created the group 'Raah'","Yesterday, 6:31 AM","You get Back INR 100.00"));
            activityDataModelArrayList.add(new ActivityDataModel(R.drawable.profile_img,"You created the group 'Raah'","Yesterday, 6:31 PM","You get Back INR 100.00"));
            activityDataModelArrayList.add(new ActivityDataModel(R.drawable.profile_img,"You created the group 'Raah'","Yesterday, 6:31 AM","You get Back INR 100.00"));
             activityAdapter = new ActivityAdapter(mContext,activityDataModelArrayList);
             binding.activityRev.setAdapter(activityAdapter);
        }
        return binding.getRoot();
    }
}
