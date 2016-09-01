package com.innoaus.thetower;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by darkark81 on 6/21/2016.
 */
public class Player extends GameObject {
    private Bitmap image;
    boolean jump;
    boolean collision;
    private int score;
    private boolean playing;

    public Player(Bitmap res, int w, int h) {
        x = (GamePanel.WIDTH / 2) - 14;
        y = 305;
        dy = 0;
        score = 0;
        height = h;
        width = w;

        image = res;
    }

    public void jump() {
        jump = true;
        collision = false;
        dy = 15;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public boolean getCollision() {
        return collision;
    }

    //needs modification
    public void update() {
        if(y > 512) {
            playing = false;
        }

        if (collision) {
            jump = false;
        }

        if (jump) {
            y -= dy;
            dy -= 1;
        } else {
            y += GamePanel.MOVESPEED;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public boolean getPlaying() {
        return playing;
    }

    public void setPlaying(boolean b) {
        playing = b;
    }

    public void reset() {
        y = 304;
        score = 0;
        jump = false;
    }


}
