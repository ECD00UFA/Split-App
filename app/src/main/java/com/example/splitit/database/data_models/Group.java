package com.example.splitit.database.data_models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@IgnoreExtraProperties
public class Group {
    public String groupId;
    public String groupName;
    public String description;
    public String groupType;
    public HashMap<String, Friend> groupMembers;
    public HashMap<String, Expense> expenses;
    public String creationData;
    public Long groupTotalAmount;
    public Long groupBalanceAmount;

    public Group() {

    }

    public Group(String groupName, String description, String groupType) {
        this.groupId = UUID.randomUUID().toString();
        this.groupName = groupName;
        this.description = description;
        this.groupType = groupType;
        this.groupMembers = new HashMap<>();
        this.expenses = new HashMap<>();
        this.creationData = generateCreateDate();
        this.groupTotalAmount = 0L;
        this.groupBalanceAmount = 0L;
    }

    private String generateCreateDate() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
        return format.format(currentTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return groupId.equalsIgnoreCase(group.groupId);
    }

}
