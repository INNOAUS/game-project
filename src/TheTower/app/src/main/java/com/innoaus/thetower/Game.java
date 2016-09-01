package com.innoaus.thetower;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity {
    private boolean playing;
    private TextView tvScore;
    private ImageButton btnStart;
    private ViewGroup viewGroupStart;
    private ViewGroup viewGroupGameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //turn off the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);

        viewGroupStart = (ViewGroup) findViewById(R.id.layout_start);
        viewGroupGameOver = (ViewGroup) findViewById(R.id.layout_gameover);
        viewGroupStart.setVisibility(View.VISIBLE);
        viewGroupGameOver.setVisibility(View.INVISIBLE);
        btnStart = (ImageButton) findViewById(R.id.button_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        Typeface face = Typeface.createFromAsset(getAssets(), "font/TFPironv2.otf");
        tvScore = (TextView) findViewById(R.id.text_score);
        tvScore.setTypeface(face);

        FrameLayout frame = (FrameLayout) findViewById(R.id.surfaceframe);
        GamePanel gamePanel = new GamePanel(this);
        frame.addView(gamePanel);
    }

    public void startGame() {
        playing = true;
        tvScore.setVisibility(View.INVISIBLE); //test
        viewGroupStart.setVisibility(View.INVISIBLE);
        viewGroupGameOver.setVisibility(View.INVISIBLE);
    }

    public void stopGame() {
        playing = false;
        runOnUiThread(runnableStop);
    }

    Runnable runnableStop = new Runnable() {
        @Override
        public void run() {
            tvScore.setVisibility(View.VISIBLE); //test
            Toast.makeText(Game.this, "Game Over", Toast.LENGTH_SHORT).show(); //test

            viewGroupGameOver.setVisibility(View.VISIBLE);
        }
    };
}
