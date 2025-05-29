package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class JoinedUserFragment {

    @SerializedName("username")
    private String username;

    @SerializedName("answer")
    private String lastMessage;

    @SerializedName("userId")
    private Long id;

    public JoinedUserFragment(String username, String lastMessage, Long id) {
        this.username = username;
        this.lastMessage = lastMessage;
        this.id = id;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
