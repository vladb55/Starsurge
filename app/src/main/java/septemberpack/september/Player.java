package septemberpack.september;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import junit.framework.TestCase;

/**
 * Created by Vlady on 09.11.2015.
 */
public class Player {


    Bitmap bitmap;

    int x, y;

    public static boolean right = false;
    public static boolean left = false;

    public Player(Bitmap bmp){
        this.bitmap = bmp;

        x = 500;
        y = 1000;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, x, y, null);
    }

    public void update(){
        if(right) {
            if(x >= GamePanel.WIDTH - 100)
                x = GamePanel.WIDTH - 100;
            else {
                x += 40;
            }
            right = false;
        }

        if(left) {
            if(x <= 0)
                x = 0;
            else {
                x -= 40;
            }
            left = false;
        }
    }

    public Rect getShip(){
        return new Rect(x-10, y+30, x + 80, y + 80);
    }

}
