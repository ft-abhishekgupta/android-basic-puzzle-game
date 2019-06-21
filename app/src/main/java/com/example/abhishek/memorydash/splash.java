package com.example.abhishek.memorydash;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class splash extends AppCompatActivity {

    MediaPlayer right,win;
    AnimationDrawable logoAnimation;ImageView logoImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        logoImage = (ImageView) findViewById(R.id.imageView);
        logoImage.setBackgroundResource(R.drawable.logo);
        logoAnimation = (AnimationDrawable) logoImage.getBackground();

        win= MediaPlayer.create(this,R.raw.win);
        right = MediaPlayer.create(this,R.raw.right);
        win.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            public void onCompletion(MediaPlayer bkk) {
                win.start();
            }
        });
        win.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        win.stop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            logoAnimation.start();
        }
        CountDownTimer ccc=new CountDownTimer(3000,1000) {
            Button b=(Button)(findViewById(R.id.button));
            @Override
            public void onTick(long millisUntilFinished) {
                b.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFinish() {
                b.setVisibility(View.VISIBLE);
            }
        };
        ccc.start();

        super.onWindowFocusChanged(hasFocus);
    }




    public void startgame(View view) {
        win.stop();
        right.start();
        Intent intent=new Intent(this,MainActivity.class);
        finish();
        startActivity(intent);
    }
}
