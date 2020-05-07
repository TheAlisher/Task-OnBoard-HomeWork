package com.example.task_onboard_homework.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_onboard_homework.R;
import com.example.task_onboard_homework.ui.OnItemClickListener;
import com.example.task_onboard_homework.ui.models.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private ArrayList<Task> list;
    private OnItemClickListener onItemClickListener;

    public TaskAdapter(ArrayList<Task> list) {
        this.list = list;
    }

    public void update(ArrayList<Task> task) {
        list = task;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.onBind(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
