package septemberpack.september;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Vlady on 11.10.2015.
 */
public class Background {

    Bitmap bitmap;
    int x, y, dy;

    public Background(Bitmap bmp){
        bitmap = bmp;
        x = 0;
        y = 0;
        dy = 5;
    }

    public void draw(Canvas canvas){
        if(canvas != null)
            canvas.drawBitmap(bitmap, x, y, null);
        if(y > 0)
            canvas.drawBitmap(bitmap, x, y-2800, null);
    }

    public void update(){
        y+=dy;
        if(y > 2800) y = 0;
    }

}
