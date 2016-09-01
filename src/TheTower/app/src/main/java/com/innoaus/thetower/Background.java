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

    public void drawbd(Canvas canvas) {
        canvas.drawBitmap(image, 0, 441, null);
    }

    public void drawlbg(Canvas canvas) {
        canvas.drawBitmap(image, 0, 0, null);
    }

    public void drawrbg(Canvas canvas) {
        canvas.drawBitmap(image, 288, 0, null);
    }

}
