package com.innoaus.testgame;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Game extends Activity {
    private ImageView bg1;
    private ImageView bg2;
    private int index = 0;

    private boolean playing = false;
    private LinearLayout layout1; //start box
    private LinearLayout layout2; //stop box

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //turn off the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);

        layout1 = (LinearLayout) findViewById(R.id.box1);
        layout2 = (LinearLayout) findViewById(R.id.box2);

        FrameLayout frame = (FrameLayout) findViewById(R.id.surfaceframe);
        GamePanel gamePanel = new GamePanel(this);
        frame.addView(gamePanel);
    }

    public boolean isPlaying() {
        return playing;
    }

    public void startGame() {
        playing = true;
        layout1.setVisibility(View.INVISIBLE);
    }

    public void stopGame() {
        playing = false;
        layout1.setVisibility(View.VISIBLE);
    }
}
