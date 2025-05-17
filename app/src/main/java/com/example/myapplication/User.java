package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private Long id;
    private String username;
    private int roomId;

    public User() {}

    public User(String username, int roomId) {
        this.username = username;
        this.roomId = roomId;
    }

    // Parcelable constructor
    protected User(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        username = in.readString();
        roomId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(username);
        dest.writeInt(roomId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
