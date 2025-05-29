package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
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

public class Join_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_join);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button joinButton = findViewById(R.id.Join_Button);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getString(R.string.zork_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                EditText room_password = findViewById(R.id.editTextPassword);
                EditText room_name = findViewById(R.id.editTextRoomCode);
                EditText username = findViewById(R.id.editTextUsername);
                ApiService service = retrofit.create(ApiService.class);
                Call<Room> call = service.getRoomByName(room_name.getText().toString());
                call.enqueue(new Callback<Room>() {
                    @Override
                    public void onResponse(Call<Room> call, Response<Room> response) {
                        if(response.body().getPassword().equals(room_password.getText().toString())){
                            Call<User> call1 = service.createUser(username.getText().toString(), response.body().getId());
                            Room room = response.body();
                            call1.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    Intent intent = new Intent(Join_Activity.this, JoinedActivity.class);
                                    intent.putExtra("user", response.body());
                                    intent.putExtra("Room", room);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {

                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Room> call, Throwable t) {

                    }
                });
            }
        });
    }
}