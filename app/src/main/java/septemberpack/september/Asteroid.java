package septemberpack.september;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.Random;

/**
 * Created by Vlady on 22.10.2015.
 */

// Данный класс реализует отрисовку астероидов

public class Asteroid {

    Bitmap bitmap;

    private int line1x;
    private int line1y;
    private int line2x;
    private int line2y;
    private int line3x;
    private int line3y;

    private Random random;

    public Asteroid(Bitmap bmp){
        this.bitmap = bmp;
        random = new Random();

        line1x = random.nextInt(1000);
        line2x = random.nextInt(1000);
        line3x = random.nextInt(1000);

        line1y = -random.nextInt(1000)-120;
        line2y = -random.nextInt(1000)-240;
        line3y = -random.nextInt(1000)-360;
    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(bitmap, line1x, line1y, null); // Первая линия
        canvas.drawBitmap(bitmap, line2x, line2y, null); // Вторая линия
        canvas.drawBitmap(bitmap, line3x, line3y, null); // Третья линия
    }

    public void update(){
        if(line1y > 1400) {
            line1y = -80;
            line1x = random.nextInt(1000);
        }
        else if(line2y > 1400) {
            line2y = -80;
            line2x = random.nextInt(1000);
        }
        else if(line3y > 1400) {
            line3y = -80;
            line3x = random.nextInt(1000);
        }

        line1y += GamePanel.speed;
        line2y += GamePanel.speed;
        line3y += GamePanel.speed;
    }

}
