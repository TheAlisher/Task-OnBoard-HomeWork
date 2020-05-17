package com.example.task_onboard_homework.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_onboard_homework.App;
import com.example.task_onboard_homework.R;
import com.example.task_onboard_homework.ui.models.Task;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private TaskAdapter adapter;
    private ArrayList<Task> list = new ArrayList<>();
    LinearLayoutManager LayoutManagerTask;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list.addAll(App.getInstance().getDatabase().taskDao().getAll());
        adapter = new TaskAdapter(list);
        recyclerView.setAdapter(adapter);
        LayoutManagerTask = new LinearLayoutManager(getContext());
        LayoutManagerTask.setReverseLayout(true);
        LayoutManagerTask.setStackFromEnd(true);
        recyclerView.setLayoutManager(LayoutManagerTask);
        loadData();
    }

    private void loadData() {
        App
                .getInstance()
                .getDatabase()
                .taskDao()
                .getAllLive()
                .observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
                    @Override
                    public void onChanged(List<Task> tasks) {
                        list.clear();
                        list.addAll(tasks);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    public void sortList() {
        list.clear();
        list.addAll(App.getInstance().getDatabase().taskDao().sort());
        adapter.notifyDataSetChanged();
        LayoutManagerTask.setReverseLayout(false);
        LayoutManagerTask.setStackFromEnd(false);
    }

    public void initList() {
        list.clear();
        list.addAll(App.getInstance().getDatabase().taskDao().getAll());
        adapter.notifyDataSetChanged();
    }

    /*@Override • PREVIOUS VERSION…
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Task task = (Task) data.getSerializableExtra(FormActivity.TASK_KEY);
        list.add(task);
        adapter.update(list);
        adapter.notifyDataSetChanged();
    }*/
}
