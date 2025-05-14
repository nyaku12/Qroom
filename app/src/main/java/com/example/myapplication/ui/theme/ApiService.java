package com.example.myapplication.ui.theme;

import com.example.myapplication.JoinedUserFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("rooms/get-answers") // Заменить на свой эндпоинт
    Call<List<JoinedUserFragment>> getUsers();
}
