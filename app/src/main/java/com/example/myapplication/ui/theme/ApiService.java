package com.example.myapplication.ui.theme;

import com.example.myapplication.JoinedUserFragment;
import com.example.myapplication.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("api/rooms/create")
    Call<Room> createRoom(@Query("name") String name, @Query("password") String password);

    @DELETE("api/rooms/delete")
    Call<Void> deleteRoom(@Query("room_id") Long id);

    @GET("rooms/get-answers")
    Call<List<JoinedUserFragment>> getUsers();
}
