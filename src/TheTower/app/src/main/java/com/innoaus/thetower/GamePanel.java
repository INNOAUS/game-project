package com.innoaus.thetower;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by darkark81 on 6/21/2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int WIDTH = 1440;
    public static final int HEIGHT = 2560;
    public static final int MOVESPEED = 5;
    private MainThread thread;
    private Background bg;
    private Background bd1;
    private Background bd2;
    private Background tbg;
    private Player player;
    private Tower tower1;
    private Tower tower2;
    private ArrayList<Pineapple> pineappleL;
    private ArrayList<Pineapple> pineappleR;
    private boolean createNewGame;


    public GamePanel(Context context) {
        super(context);

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    boolean retry = true;
        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            }catch (InterruptedException e) {e.printStackTrace();}
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.bg1));
        bd1 = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.bg2));
        bd2 = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.botborder1));
        tbg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.towerb));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.prince1),
                100, 260);
        tower1 = new Tower(BitmapFactory.decodeResource(getResources(), R.drawable.tower1), 1);
        tower2 = new Tower(BitmapFactory.decodeResource(getResources(), R.drawable.tower1), 2);

        pineappleL = new ArrayList<Pineapple>();
        pineappleR = new ArrayList<Pineapple>();

        pineappleL.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), 1984, true));
        pineappleR.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), pineappleL.get(0).getX() + 1040, 1984, true));

        pineappleL.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), 20, 1472, false));
        pineappleR.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), pineappleL.get(1).getX() + 1040, 1472, false));

        pineappleL.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), 960, true));
        pineappleR.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), pineappleL.get(2).getX() + 1040, 960, true));

        pineappleL.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), 448, false));
        pineappleR.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), pineappleL.get(3).getX() + 1040, 448, false));

        pineappleL.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), -64, true));
        pineappleR.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), pineappleL.get(4).getX() + 1040, -64, true));

        pineappleL.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), -576, false));
        pineappleR.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), pineappleL.get(5).getX() + 1040, -576, false));


        thread = new MainThread(getHolder(), this);
        //safely start the game loop
        thread.setRunning(true);
        thread.start();
    }

    //needs modification
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!player.getPlaying()) {
                if(createNewGame) {
                    newGame();
                }
                player.setPlaying(true);
                createNewGame = false;
            } else {
                player.jump();
            }
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void update() {
        if (player.getPlaying()) {
            tower1.update();
            tower2.update();
            player.update();
            for (int i = 0; i < 6; i++) {
                pineappleL.get(i).update();
                pineappleR.get(i).update(pineappleL.get(i).getX() + 1040);
                if (collision(player, pineappleL.get(i)) || collision(player, pineappleR.get(i))) {
                    if(player.getY()+260 >= pineappleL.get(i).getY() &&
                            player.getY()+260 <= pineappleL.get(i).getY()+ 25) {
                        player.collision(true);
                        pineappleL.get(i).collision(true);
                    } else player.setPlaying(false);
                }
            }
        } else createNewGame = true;

    }

    public boolean collision(GameObject a, GameObject b) {
        if (Rect.intersects(a.getRectangle(), b.getRectangle())) {
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        final float scaleFactorX = (float) getWidth() / WIDTH;
        final float scaleFactorY = (float) getHeight() / HEIGHT;

        if (canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.drawbg(canvas);
            bd1.drawbd1(canvas);
            tbg.drawtbg(canvas);
            tower1.draw(canvas);
            tower2.draw(canvas);
            for(int i = 0; i < 6; i++) {
                pineappleL.get(i).draw(canvas);
                pineappleR.get(i).draw(canvas);
            }
            player.draw(canvas);
            bd2.drawbd2(canvas);

            canvas.restoreToCount(savedState);
        }
    }

    public void newGame() {
        player.reset();
        tower1.reset();
        tower2.reset();
        for(int i = 0; i < 6; i++) {
            int x = pineappleL.get(i).resetL();
            pineappleR.get(i).resetR(x);
        }
    }

}
