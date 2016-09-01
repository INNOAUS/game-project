package com.innoaus.thetower;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by darkark81 on 2016-07-11.
 */
public class Pineapple extends GameObject {
    private Random rand = new Random();
    private int forResetX, forResetY;
    private Bitmap image;
    private boolean left;
    private boolean forResetL;
    private boolean collision;

    public Pineapple(Bitmap res, int y, boolean left) {
        image = res;
        super.y = y;
        forResetY = y;
        this.left = left;
        forResetL = left;
        height = 32;
        width = 210;
        dy = GamePanel.MOVESPEED;
        dx = GamePanel.MOVESPEED*2;

        x = rand.nextInt(125) - 116;
    }

    public Pineapple(Bitmap res, int x, int y, boolean left) {
        image = res;
        super.x = x;
        forResetX = x;
        super.y = y;
        forResetY = y;
        this.left = left;
        forResetL = left;
        height = 32;
        width = 210;
        dy = GamePanel.MOVESPEED;
        dx = GamePanel.MOVESPEED*2;
    }

    public void collision(boolean b) {
        collision = b;
    }

    public void update() {
        y += dy;
        if(y >= 624) {
            y = -144;
            collision = false;
        }
        if(!collision) {
            if (left) {
                x -= dx;
                if (x <= -116) {
                    left = false;
                }
            } else {
                x += dx;
                if (x >= 8) {
                    left = true;
                }
            }
        }
    }

    public void update(int x) {
        y+= dy;
        if(y >= 624) {
            y = -144;
        }
            this.x = x;
    }

    public void draw(Canvas canvas) {
        try {
                canvas.drawBitmap(image, x, y, null);
        } catch (Exception e) {}
    }

    public int resetFirst() {
        y = forResetY;
        x = 5;
        left = forResetL;
        collision = false;
        return x;
    }

    public int resetL() {
        y = forResetY;
        x = rand.nextInt(125) - 116;
        left = forResetL;
        collision = false;
        return x;
    }

    public void resetR(int x) {
        y = forResetY;
        this.x = x + 256;
        left = forResetL;
    }

    public Rect getRectangle() {
        return new Rect(x, y, x + width, y + height);
    }

}
