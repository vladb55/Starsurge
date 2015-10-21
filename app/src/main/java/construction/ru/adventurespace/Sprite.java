package construction.ru.adventurespace;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by USER on 01.10.2015.
 */
public class Sprite {

    private Bitmap bmp;
    private Paint paint;
    private int x=120;
    private final int y=500;
    private int Speed=5;

    public Sprite(Paint p,Bitmap bmp){
        this.bmp=bmp;
        this.paint=p;
    }
    public void DrawImage(Canvas canvas){
        canvas.drawBitmap(bmp,x,y,null);
    }
}
