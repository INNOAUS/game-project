package com.innoaus.testgame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

/**
 * Created by ryusei on 8/30/16.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private Game parent;
    private Context context;

    private ArrayList<Bitmap> bglist = new ArrayList<>();
    private Bitmap bg1, bg2;
    int index = 0;
    int alpha = 255;

    public GamePanel(Game activity) {
        super(activity.getApplicationContext());
        this.context = activity.getApplicationContext();
        this.parent = activity;
        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bglist == null)
            return;

        Paint p = new Paint();
        p.setARGB(alpha, 255, 255, 255);
        Paint p2 = new Paint();
        p2.setARGB(255 - alpha, 255, 255, 255);

        canvas.drawBitmap(bg1, 0, 0, p); // draw the background
        canvas.drawBitmap(bg2, 0, 0, p2); // draw the background
    }

    private void initBackground() {
        setBackgroundColor(Color.GRAY);
        bg1 = bglist.get(0);
        bg2 = bglist.get(0);
    }

    private void setBackground() {
        index = (index + 1) % 5;
        log("index: " + index);
        bg1 = bg2;
        bg2 = bglist.get(index);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setBackground();
            alpha = 255;
            if (parent.isPlaying()) {
                parent.stopGame();
                handler.postDelayed(runnableBackground, 10);
            } else {
                parent.startGame();
                handler.postDelayed(runnableBackground, 10);
            }
            return true;
        }

        return super.onTouchEvent(event);
    }

    Handler handler = new Handler();
    Runnable runnableBackground = new Runnable() {
        @Override
        public void run() {
            if (alpha <= 0) {
                return;
            }
            alpha -= 5;
            handler = new Handler();
            postInvalidate(); //call onDraw()
            handler.postDelayed(runnableBackground, 5);
        }
    };

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bglist.add(BitmapFactory.decodeResource(getResources(), R.drawable.bg1));
        bglist.add(BitmapFactory.decodeResource(getResources(), R.drawable.bg2));
        bglist.add(BitmapFactory.decodeResource(getResources(), R.drawable.bg3));
        bglist.add(BitmapFactory.decodeResource(getResources(), R.drawable.bg4));
        bglist.add(BitmapFactory.decodeResource(getResources(), R.drawable.bg5));

        initBackground();
        parent.stopGame();
        handler.postDelayed(runnableBackground, 10);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void log(String msg) {
        Log.d("GamePanel", "@@" + msg);
    }
}
