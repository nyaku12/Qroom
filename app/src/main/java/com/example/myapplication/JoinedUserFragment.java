package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class JoinedUserFragment {

    @SerializedName("username")  // Если имя в JSON отличается, укажите его в аннотации
    private String username;

    @SerializedName("answer")  // Явное указание на поле, если имя отличается от поля
    private String lastMessage;

    @SerializedName("userId")  // Явное указание на поле id, если имя отличается
    private Long id;

    // Конструктор с параметрами
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

    // Сеттеры для обновления значений (если нужно)
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
