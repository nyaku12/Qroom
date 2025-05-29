package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.ui.theme.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_room_activity_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText room_name = findViewById(R.id.room_name_input);
        EditText room_password = findViewById(R.id.room_password_input);
        Button create_button = findViewById(R.id.create_room_button);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getString(R.string.zork_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService service = retrofit.create(ApiService.class);
                Call<Room> call = service.createRoom(room_name.getText().toString(), room_password.getText().toString());
                call.enqueue(new Callback<Room>() {
                    @Override
                    public void onResponse(Call<Room> call, Response<Room> response) {
                        if (response.isSuccessful() && !response.body().getName().equals("ERR: already exist or null nickname")) {
                            Room createdRoom = response.body();
                            Log.d("API", "Room created: " + createdRoom);
                            Intent intent = new Intent(CreateActivity.this, HostActivity.class);
                            intent.putExtra("key_room", response.body());
                            startActivity(intent);
                        }
                        else if(response.body().getName().equals("ERR: already exist or null nickname")){
                            Log.d("API", "Room anready defined");
                            Toast.makeText(getApplicationContext(), "Room with theese parametres already exist", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Log.e("API", "Server returned error: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Room> call, Throwable t) {
                        Log.e("API", "Network error: ", t);
                    }
                });
            }
        });
    }
}