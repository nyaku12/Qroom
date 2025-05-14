package com.example.myapplication.ui.theme;

import com.example.myapplication.JoinedUserFragment;
import com.example.myapplication.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("rooms/create")
    Call<Room> createRoom(@Query("name") String name, @Query("password") String password);
    @GET("rooms/get-answers") // Заменить на свой эндпоинт
    Call<List<JoinedUserFragment>> getUsers();
}
