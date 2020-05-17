package com.example.task_onboard_homework.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_onboard_homework.App;
import com.example.task_onboard_homework.FormActivity;
import com.example.task_onboard_homework.R;
import com.example.task_onboard_homework.ui.OnItemClickListener;
import com.example.task_onboard_homework.ui.models.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    public static final String EDIT_TEXT_KEY = "edit_text_key";
    public static final String POSITION_KEY = "position_key";
    private ArrayList<Task> list;
    private OnItemClickListener onItemClickListener;

    public TaskAdapter(ArrayList<Task> list) {
        this.list = list;
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
        holder.setIsRecyclable(true);
        if (position % 2 == 0) {
            holder.linearLayout.setBackgroundResource(R.color.White);
        } else {
            holder.linearLayout.setBackgroundResource(R.color.LightGrey);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView textHeading;
        private TextView textDescription;
        private LinearLayout linearLayout;

        public TaskViewHolder(@NonNull final View itemView) {
            super(itemView);
            textHeading = itemView.findViewById(R.id.text_Heading);
            textDescription = itemView.findViewById(R.id.text_Description);
            linearLayout = itemView.findViewById(R.id.linearLayout_in_listTask);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), FormActivity.class);
                    Task task = new Task(textHeading.getText().toString(), textDescription.getText().toString());
                    int position = list.get(getAdapterPosition()).getId();
                    intent.putExtra(EDIT_TEXT_KEY, task);
                    intent.putExtra(POSITION_KEY, position);
                    v.getContext().startActivity(intent);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(v.getContext());
                    deleteDialog
                            .setIcon(R.drawable.ic_delete_black)
                            .setTitle(R.string.information_message)
                            .setMessage(R.string.are_you_sure)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int position = list.get(getAdapterPosition()).getId();
                                    App.getInstance().getDatabase().taskDao().deleteById(position);
                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    deleteDialog.show();
                    return true;
                }
            });
        }

        public void onBind(Task task) {
            textHeading.setText(task.getTitle());
            textDescription.setText(task.getDesc());
        }
    }
}
