package com.example.myapplication;

public class Room {
    private Long id;
    private String name;
    private String password;
    // Конструкторы, геттеры и сеттеры
    public Room(){};

    public Room(String name, String password){
        this.name = name;
        this.password = password;
    };

    public void print(){
        System.out.println(this.name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
