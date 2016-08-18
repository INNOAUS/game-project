package com.innoaus.thetower;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by darkark81 on 2016-07-06.
 */
public class Tower {
    private Bitmap image;
    private int x, y, dy;
    private int forReset;

    public Tower(Bitmap res, int number) {
        image = res;
        x = (GamePanel.WIDTH / 2) - 350;
        forReset = number;
        if (number == 1) {y = -512;} else y = -512 - GamePanel.HEIGHT;
        dy = GamePanel.MOVESPEED;
    }

    public void update() {
        y += dy;
        if (y >= GamePanel.HEIGHT) {
            y = -GamePanel.HEIGHT;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void reset() {
        if (forReset == 1) {y = -512;} else y = -512 - GamePanel.HEIGHT;
    }
}
