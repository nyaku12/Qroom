package com.example.myapplication.ui.theme;

import com.example.myapplication.JoinedUserFragment;
import com.example.myapplication.Room;
import com.example.myapplication.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    //создание/удаление комнаты
    @POST("api/rooms/create")
    Call<Room> createRoom(@Query("name") String name, @Query("password") String password);

    @DELETE("api/rooms/delete")
    Call<Void> deleteRoom(@Query("room_id") Long id);

    @GET("api/rooms/get-room-by-name")
    Call<Room> getRoomByName(@Query("name") String name);

    @POST("api/rooms/get-answers")
    Call<List<JoinedUserFragment>> getAnswers(@Query("room_id") Long id);

    //создание пользователя
    @POST("api/users/create")
    Call<User> createUser(@Query("username") String username, @Query("room_id") Long id);


    //не оч эффективно, перепиши лучше
    @GET("api/rooms/get-users-ammount")
    Call<Integer> getAmmount(@Query("room_id") Long id);

    @POST("api/answers/create")
    Call<Void> sendAnswer(@Query("user_id") Long uId, @Query("room_id") Long rId, @Query("answ") String answ);

}
