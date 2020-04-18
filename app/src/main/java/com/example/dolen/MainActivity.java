package com.example.dolen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.dolen.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager mViewPager = findViewById(R.id.view_pager);
        setupViewPager(mViewPager);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //hier werden die verschiedenen Tabs initialisiert und vergeben
    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(new ToDo(), "ToDo");
        adapter.addFragment(new Chat(), "Chat");
        viewPager.setAdapter(adapter);
    }
}