package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.theme.ApiService;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HostActivity extends AppCompatActivity {

    private Room room;
    boolean running = true;
    private RecyclerView recyclerViewUsers;
    private JoinedUserAdapter adapter;
    private List<JoinedUserFragment> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_host);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.room_activity_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        room = getIntent().getParcelableExtra("key_room");
        TextView room_name = findViewById(R.id.textRoomName);
        room_name.setText(room.getName().toString());
        Thread updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    runOnUiThread(() -> updateUI()); // вызываем метод в UI-потоке

                    try {
                        Thread.sleep(5000); // ждем 5 секунд
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JoinedUserAdapter(userList);
        recyclerViewUsers.setAdapter(adapter);

        updateThread.start();
        setUserCount();

    }

    @Override
    protected void onDestroy() {
        deleteRoom();
        running = false;
        super.onDestroy();
    }
    private void updateUI(){
        setUserCount();
        loadUsers();
    }
    private void loadUsers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<List<JoinedUserFragment>> call = service.getAnswers(room.getId());

        call.enqueue(new Callback<List<JoinedUserFragment>>() {
            @Override
            public void onResponse(Call<List<JoinedUserFragment>> call, Response<List<JoinedUserFragment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userList.clear();
                    userList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Log.d("API", "Users updated: " + userList.size());
                    Log.d("API", "body: " + response.body().toString());
                } else {
                    Log.e("API", "Response empty or failed");
                    Log.e("API", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<JoinedUserFragment>> call, Throwable t) {
                Log.e("API", "Failed to get users", t);
            }
        });
    }

    private void deleteRoom(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service =retrofit.create(ApiService.class);
        Call<Void> call = service.deleteRoom(room.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("API", "Room deleted successfully");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API", "Failed to delete room", t);
            }
        });
        Log.d("API", "Room: " + room.getName() + " deleted");
    }
    private void setUserCount (){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service =retrofit.create(ApiService.class);
        Call<Integer> call = service.getAmmount(room.getId());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                TextView textView = findViewById(R.id.textUserCount);
                if(response.body()!=null){
                    textView.setText("joined users: " + response.body().toString());
                    Log.d("API", "Response code: " + response.code());
                    Log.d("API", "Response body: " + response.body().toString());
                }
                else Log.e("API", "response null");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }
}