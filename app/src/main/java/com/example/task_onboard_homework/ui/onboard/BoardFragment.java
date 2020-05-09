package com.example.task_onboard_homework.ui.onboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.task_onboard_homework.MainActivity;
import com.example.task_onboard_homework.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {

    public BoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int pos = getArguments().getInt("pos");
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textHeading = view.findViewById(R.id.textHeading);
        TextView textDescription = view.findViewById(R.id.textDescription);
        Button getStart = view.findViewById(R.id.button_GetStarted);
        ConstraintLayout fragmentBoard = view.findViewById(R.id.fragment_board);

        switch (pos) {
            case 0:
                imageView.setImageResource(R.drawable.image_apple_tv);
                textHeading.setText("Покупайте. Берите напрокат. Смотрите. Всё в одном приложении.");
                textDescription.setText("Представляем новую видеотеку, в которой собраны тысячи фильмов, " +
                        "включая свежие блокбастеры. Теперь их можно покупать, " +
                        "брать напрокат и смотреть в одном приложении. " +
                        "В нём же вы найдёте то, что приобрели ранее в iTunes.");
                getStart.setVisibility(View.GONE);
                fragmentBoard.setBackgroundResource(R.drawable.image_stories_1);
                break;
            case 1:
                imageView.setImageResource(R.drawable.image_apple_tv_plus);
                textHeading.setText("Вселенная кино у вас под рукой");
                textDescription.setText("Сервис Apple TV+ - " +
                        "это признанные критиками оригинальные фильмы и сериалы от Apple, " +
                        "только в одном приложении Apple TV");
                getStart.setVisibility(View.GONE);
                fragmentBoard.setBackgroundResource(R.drawable.image_stories_15);
                break;
            case 2:
                textHeading.setText("Приложение Apple TV");
                imageView.setImageResource(R.drawable.image_apple_tv_4k_remote);
                textDescription.setText("Смотрите всё в одном приложении.");
                getStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveIsShown();
                        /*Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);*/
                        getActivity().finish();
                    }
                });
                fragmentBoard.setBackgroundResource(R.drawable.image_stories_6);
                break;
        }
    }

    private void saveIsShown() {
        SharedPreferences preferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("isShown", true).apply();
    }

}
