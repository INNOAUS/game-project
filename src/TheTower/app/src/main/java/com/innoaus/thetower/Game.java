package com.innoaus.thetower;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity implements View.OnClickListener {
    private boolean playing;

    private ViewGroup viewGroupStart;
    private ViewGroup viewGroupGamePanel;
    private ViewGroup viewGroupGameOver;
    private GamePanel gamePanel;

    private ImageButton btnStart;
    private ImageButton btnRetry;
    private TextView tvGamePanelScore;
    private TextView tvFinalScore;
    private TextView tvFinalHighScore;

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
        viewGroupGamePanel = (ViewGroup) findViewById(R.id.layout_gamepanel);
        viewGroupGameOver = (ViewGroup) findViewById(R.id.layout_gameover);

        viewGroupStart.setVisibility(View.VISIBLE);
        viewGroupGamePanel.setVisibility(View.GONE);
        viewGroupGameOver.setVisibility(View.GONE);

        btnStart = (ImageButton) findViewById(R.id.button_start);
        btnStart.setOnClickListener(this);
        btnRetry = (ImageButton) findViewById(R.id.button_retry);
        btnRetry.setOnClickListener(this);

        Typeface face = Typeface.createFromAsset(getAssets(), "font/TFPironv2.otf");
        TextView tvStartButton = (TextView) findViewById(R.id.text_startbutton);
        tvStartButton.setTypeface(face);
        tvGamePanelScore = (TextView) findViewById(R.id.text_score);
        tvGamePanelScore.setTypeface(face);
        tvFinalScore = (TextView) findViewById(R.id.text_finalscore);
        tvFinalScore.setTypeface(face);
        tvFinalHighScore = (TextView) findViewById(R.id.text_finalhighscore);
        tvFinalHighScore.setTypeface(face);

        FrameLayout frame = (FrameLayout) findViewById(R.id.surfaceframe);
        gamePanel = new GamePanel(this);
        frame.addView(gamePanel);
    }

    public void startGame() {
        gamePanel.startGame();

        viewGroupStart.setVisibility(View.GONE);
        viewGroupGamePanel.setVisibility(View.VISIBLE);
        tvGamePanelScore.setText(String.format("%d", 222)); //display integer
    }

    public void stopGame() {
        playing = false;
        runOnUiThread(runnableStop);
    }

    private void home() {
        viewGroupStart.setVisibility(View.VISIBLE);
        viewGroupGameOver.setVisibility(View.GONE);
    }

    Runnable runnableStop = new Runnable() {
        @Override
        public void run() {
            viewGroupGameOver.setVisibility(View.VISIBLE);
            viewGroupGamePanel.setVisibility(View.GONE);

            tvFinalHighScore.setText("222");
            tvFinalScore.setText("999");
        }
    };

    @Override
    public void onClick(View v) {
        int vid = v.getId();
        if (vid == R.id.button_retry) {
            home();
        } else if (vid == R.id.button_start) {
            startGame();
        }
    }


}
