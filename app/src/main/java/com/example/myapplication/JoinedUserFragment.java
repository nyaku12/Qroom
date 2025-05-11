package com.example.myapplication;

public class JoinedUserFragment {
    private String username;
    private String lastMessage;
    private Long id;

    public JoinedUserFragment(String username, String lastMessage) {
        this.username = username;
        this.lastMessage = lastMessage;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
