package septemberpack.september;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Vlady on 10.10.2015.
 */
public class MainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder holder, GamePanel gamePanel){

        super();
        this.surfaceHolder = holder;
        this.gamePanel = gamePanel;
    }

    void setRunning(boolean running){
        this.running = running;
    }

    @Override
    public void run(){


        while(running){
            if(!gamePanel.Pause_game){
                long StartDraw = System.currentTimeMillis();

                canvas = null;
                try{
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder){
                        gamePanel.Update();
                        gamePanel.Draw(canvas);
                    }
                } catch(Exception ex){}
                finally {
                    if(canvas != null)
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch(Exception e){}
                }


                long EndDraw = System.currentTimeMillis();
            }
        }
    }

}
