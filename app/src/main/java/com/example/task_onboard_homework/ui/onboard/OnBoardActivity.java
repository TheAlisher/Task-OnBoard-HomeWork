package com.example.task_onboard_homework.ui.onboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.task_onboard_homework.MainActivity;
import com.example.task_onboard_homework.R;
import com.google.android.material.tabs.TabLayout;

public class OnBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);

        final ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));

        final Button skip = findViewById(R.id.button_Skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

        TabLayout TabDots = findViewById(R.id.tabLayout_dots);
        TabDots.setupWithViewPager(viewPager,true);
    }
}
