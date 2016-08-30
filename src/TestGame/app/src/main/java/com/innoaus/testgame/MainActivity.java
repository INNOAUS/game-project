package com.innoaus.testgame;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView scoreView;
    private RelativeLayout rootView;
    private ImageView bg1;
    private ImageView bg2;
    private int index = 0;
    private int score = 0;
    private SoundPool pool;
    private int soundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        rootView = (RelativeLayout) findViewById(R.id.rootview);
        rootView.setBackgroundResource(R.drawable.bg1);
        bg1 = (ImageView) findViewById(R.id.image_bg1);
        bg2 = (ImageView) findViewById(R.id.image_bg2);
        bg2.setImageResource(R.drawable.bg1);

        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(this);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/TFPironv2.otf");
        scoreView = (TextView) findViewById(R.id.tv_score);
        scoreView.setTypeface(face);
        scoreView.setText(String.format("%d", score));

        pool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundId = pool.load(this, R.raw.jump, 1);
    }

    @Override
    public void onClick(View v) {
        score++;
        scoreView.setText(String.format("%d", score));
        pool.play(soundId, 1, 1, 0, 0, 2); //2x

        if (index == 0) { //bg1 -> bg2
            index = 1;

            //Drawable currentBackground = rootView.getBackground();
            bg1.setImageDrawable(bg2.getDrawable());
            bg2.setImageResource(R.drawable.bg2);
            rootView.setBackgroundResource(R.drawable.bg2);
            Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            bg2.startAnimation(anim1);
        } else { //bg2 -> bg1
            index = 0;

            //Drawable currentBackground = rootView.getBackground();
            bg1.setImageDrawable(bg2.getDrawable());
            bg2.setImageResource(R.drawable.bg1);
            rootView.setBackgroundResource(R.drawable.bg1);
            Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            bg2.startAnimation(anim1);
        }
    }
}
