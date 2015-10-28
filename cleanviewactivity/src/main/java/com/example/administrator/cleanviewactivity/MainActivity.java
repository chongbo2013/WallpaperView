package com.example.administrator.cleanviewactivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private WallpaperView wallpaperView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_wallpaper_view);
        wallpaperView=(WallpaperView)findViewById(R.id.mWallpaperView);
        wallpaperView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wallpaperView.startAnimation();
            }
        });

    }


}
