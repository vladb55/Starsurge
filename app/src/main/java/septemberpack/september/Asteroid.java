package septemberpack.september;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Vlady on 22.10.2015.
 */

// Данный класс реализует отрисовку астероидов

public class Asteroid {

    Bitmap bitmap; // Картинка астероида
    private int x; // Координата по x
    private int y; // Координата по y

    public Asteroid(Bitmap bmp){
        this.bitmap = bmp;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, 500, y, null);
    }

    public void update(){
        y += GamePanel.speed;
    }

}
