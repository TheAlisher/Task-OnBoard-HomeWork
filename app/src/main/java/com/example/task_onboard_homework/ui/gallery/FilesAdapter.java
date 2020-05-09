package com.example.task_onboard_homework.ui.gallery;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_onboard_homework.R;

import java.io.File;
import java.util.ArrayList;

public class FilesAdapter extends RecyclerView.Adapter<FilesViewHolder> {

    ArrayList<File> list;

    public FilesAdapter(ArrayList<File> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public FilesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_files, parent, false);
        return new FilesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilesViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
