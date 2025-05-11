package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JoinedUserAdapter extends RecyclerView.Adapter<JoinedUserAdapter.UserViewHolder> {
    private List<JoinedUserFragment> userList;

    public JoinedUserAdapter(List<JoinedUserFragment> userList) {
        this.userList = userList;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textUsername;

        TextView textLastMessage;
        TextView textUserId;

        public UserViewHolder(View itemView) {
            super(itemView);
            textUsername = itemView.findViewById(R.id.textUsername);
            textLastMessage = itemView.findViewById(R.id.textLastMessage);
            textUserId = itemView.findViewById(R.id.textUserId);
        }
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_joined_user_fragment, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        JoinedUserFragment user = userList.get(position);
        holder.textUsername.setText(user.getUsername());
        holder.textUserId.setText("ID: " + user.getId());
        holder.textLastMessage.setText(user.getLastMessage());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}