package com.innoaus.thetower;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by darkark81 on 6/21/2016.
 */
public class Background {
    private Bitmap image;

    public Background(Bitmap res) {
        image = res;
    }

    public void drawbg(Canvas canvas) {
        canvas.drawBitmap(image, 0, 0, null);
    }

    public void drawbd1(Canvas canvas) {
        canvas.drawBitmap(image, 0, 1620, null);
    }

    public void drawbd2(Canvas canvas) {
        canvas.drawBitmap(image, 0, 1731, null);
    }

    public void drawtbg(Canvas canvas) {
        canvas.drawBitmap(image, (GamePanel.WIDTH / 2) - 350, 0, null);
    }

}
