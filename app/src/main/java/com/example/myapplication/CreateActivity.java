package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.theme.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateActivity extends AppCompatActivity {

    private EditText roomNameInput;
    private EditText roomPasswordInput;
    private Button createRoomButton;

    // Retrofit
    private static final String BASE_URL = "https://your-api-url.com/"; // Укажите URL вашего сервера
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create);

        // Инициализация полей ввода и кнопки
        roomNameInput = findViewById(R.id.room_name_input);
        roomPasswordInput = findViewById(R.id.room_password_input);
        createRoomButton = findViewById(R.id.create_room_button);

        // Инициализация Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        // Установка слушателя на кнопку для создания комнаты
        createRoomButton.setOnClickListener(v -> createRoom());
    }

    private void createRoom() {
        // Получаем данные из полей ввода
        String roomName = roomNameInput.getText().toString().trim();
        String roomPassword = roomPasswordInput.getText().toString().trim();

        // Проверка на пустые значения
        if (roomName.isEmpty() || roomPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Вызов API для создания комнаты
        Call<Room> call = apiService.createRoom(roomName, roomPassword);
        call.enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    // Если комната успешно создана, передаем данные в HostActivity
                    Room createdRoom = response.body();
                    if (createdRoom != null) {
                        Intent intent = new Intent(CreateActivity.this, HostActivity.class);
                        intent.putExtra("ROOM_NAME", createdRoom.getName());
                        intent.putExtra("ROOM_PASSWORD", createdRoom.getPassword());
                        intent.putExtra("ROOM_ID", createdRoom.getId());
                        startActivity(intent);
                    }
                } else {
                    // Обработка ошибок, если запрос не был успешным
                    Toast.makeText(CreateActivity.this, "Failed to create room", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                // Обработка ошибки при подключении или при сбое запроса
                Toast.makeText(CreateActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
