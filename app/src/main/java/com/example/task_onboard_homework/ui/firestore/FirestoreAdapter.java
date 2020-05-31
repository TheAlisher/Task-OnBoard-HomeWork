package com.example.task_onboard_homework.ui.firestore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_onboard_homework.R;
import com.example.task_onboard_homework.ui.models.Task;

import java.util.ArrayList;

public class FirestoreAdapter extends RecyclerView.Adapter<FirestoreViewHolder> {

    ArrayList<Task> list;

    public FirestoreAdapter(ArrayList<Task> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public FirestoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_firestore, parent, false);
        return new FirestoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FirestoreViewHolder holder, int position) {
        holder.onBind(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
