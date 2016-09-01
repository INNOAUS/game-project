package com.innoaus.thetower;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by darkark81 on 6/21/2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int WIDTH = 360;
    public static final int HEIGHT = 640;
    public static final int MOVESPEED = 1;
    private Game parent;
    private Context context;
    private MainThread thread;
    private Background bg;
    private Background bd;
    private Background lbg;
    private Background rbg;
    private Player player;
    private Tower tower1;
    private Tower tower2;
    private ArrayList<Pineapple> pineappleL;
    private ArrayList<Pineapple> pineappleR;
    private boolean createNewGame;


    public GamePanel(Game activity) {
        super(activity.getApplicationContext());
        this.context = activity.getApplicationContext();
        this.parent = activity;
        getHolder().addCallback(this);
        setFocusable(true);
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {e.printStackTrace();}
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.bg1));
        bd = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.botborder1));
        lbg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.leftbg));
        rbg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.rightbg));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.prince1),
                25, 65);
        tower1 = new Tower(BitmapFactory.decodeResource(getResources(), R.drawable.tower1), 1);
        tower2 = new Tower(BitmapFactory.decodeResource(getResources(), R.drawable.tower1), 2);

        pineappleL = new ArrayList<Pineapple>();
        pineappleR = new ArrayList<Pineapple>();

        pineappleL.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), 496, true));
        pineappleR.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), pineappleL.get(0).getX() + 256, 496, true));

        pineappleL.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), 5, 368, false));
        pineappleR.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), pineappleL.get(1).getX() + 256, 368, false));

        pineappleL.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), 240, true));
        pineappleR.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), pineappleL.get(2).getX() + 256, 240, true));

        pineappleL.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), 112, false));
        pineappleR.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), pineappleL.get(3).getX() + 256, 112, false));

        pineappleL.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), -16, true));
        pineappleR.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), pineappleL.get(4).getX() + 256, -16, true));

        pineappleL.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), -144, false));
        pineappleR.add(new Pineapple(BitmapFactory.decodeResource(getResources(), R.drawable
                .pineapple1), pineappleL.get(5).getX() + 256, -144, false));


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
                if (createNewGame) {
                    newGame();
                }
                parent.startGame();
                player.setPlaying(true);
                createNewGame = false;
            } else if (player.getCollision()) {
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
                pineappleR.get(i).update(pineappleL.get(i).getX() + 256);
                if (collision(player, pineappleL.get(i)) || collision(player, pineappleR.get(i))) {
                    if (collision(player, pineappleL.get(i)) &&
                            player.getY() + 65 >= pineappleL.get(i).getY() &&
                            player.getY() + 65 <= pineappleL.get(i).getY() + 4 &&
                            player.getX() < pineappleL.get(i).getX() + 206) {
                        player.setCollision(true);
                        pineappleL.get(i).collision(true);
                    } else if (collision(player, pineappleR.get(i)) &&
                            player.getY() + 65 >= pineappleR.get(i).getY() &&
                            player.getY() + 65 <= pineappleR.get(i).getY() + 4 &&
                            player.getX() + 25 > pineappleR.get(i).getX() + 6) {
                        player.setCollision(true);
                        pineappleL.get(i).collision(true);
                    } else {
                        parent.stopGame();
                        player.setPlaying(false);
                    }
                }
            }
        } else {
            createNewGame = true;
        }

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
            tower1.draw(canvas);
            tower2.draw(canvas);
            for (int i = 0; i < 6; i++) {
                pineappleL.get(i).draw(canvas);
                pineappleR.get(i).draw(canvas);
            }
            lbg.drawlbg(canvas);
            rbg.drawrbg(canvas);
            player.draw(canvas);
            bd.drawbd(canvas);

            canvas.restoreToCount(savedState);
        }
    }

    public void newGame() {
        player.reset();
        tower1.reset();
        tower2.reset();
        for (int i = 0; i < 6; i++) {
            if (i == 1) {
                int x = pineappleL.get(i).resetFirst();
                pineappleR.get(i).resetR(x);
            } else {
                int x = pineappleL.get(i).resetL();
                pineappleR.get(i).resetR(x);
            }
        }
    }
}
