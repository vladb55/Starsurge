package construction.ru.adventurespace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.jar.Attributes;

/**
 * Created by USER on 30.09.2015.
 */
public class Paint extends SurfaceView {
    private SurfaceHolder holder;
    private Bitmap bmp;
    Sprite sp;
    public Paint(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
    }
    public Paint(Context context) {
        super(context);
        holder=getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas canvas=holder.lockCanvas(null);
                OnDraw(canvas);
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
        bmp= BitmapFactory.decodeResource(getResources(),R.drawable.fregat);
        sp=new Sprite(this,bmp);
    }
    protected void OnDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        sp.DrawImage(canvas);
    }
}
