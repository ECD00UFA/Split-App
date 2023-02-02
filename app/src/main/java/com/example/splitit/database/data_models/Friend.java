package com.example.splitit.database.data_models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Objects;
import java.util.UUID;

@IgnoreExtraProperties
public class Friend {
    public String friendId;
    public String friendName;
    public String friendContact;

    public Friend() {

    }

    public Friend(String friendName, String friendContact) {
        this.friendId = UUID.randomUUID().toString();
        this.friendName = friendName;
        this.friendContact = friendContact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend friend = (Friend) o;
        return friendName.contentEquals(friend.friendName);
    }

}
