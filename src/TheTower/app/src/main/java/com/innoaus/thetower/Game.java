package com.innoaus.thetower;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Game extends Activity {
    private boolean playing;
    TextView tvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //turn off the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);

        //layout1 = (LinearLayout) findViewById(R.id.box1);
        //layout2 = (LinearLayout) findViewById(R.id.box2);

        Typeface face = Typeface.createFromAsset(getAssets(), "font/TFPironv2.otf");
        tvScore = (TextView) findViewById(R.id.text_score);
        tvScore.setTypeface(face);

        FrameLayout frame = (FrameLayout) findViewById(R.id.surfaceframe);
        GamePanel gamePanel = new GamePanel(this);
        frame.addView(gamePanel);
    }

    public void startGame() {
        playing = true;
        tvScore.setVisibility(View.INVISIBLE);
    }

    public void stopGame() {
        playing = false;
        Log.d("Game", "aaa");
        tvScore.setVisibility(View.VISIBLE);
    }
}
