package septemberpack.september;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Vlady on 10.10.2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public MainThread thread;
    public boolean Pause_game;
    private Background background;
    public float ShipSpeed;

    public GamePanel(Context context, Game game, int ScreenWidth) {
        super(context);
        getHolder().addCallback(this);
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.back_game), ScreenWidth, this);
        setFocusable(true);
        ShipSpeed = ScreenWidth/2.f;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    void Draw(Canvas canvas){
        if(!Pause_game)
            if(canvas != null) {
                background.draw(canvas);
            }
    }

    void Update(float dt){
        background.update(dt);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try{
                thread.join();
                retry = false;
            } catch(Exception ex){}
        }
    }
}
