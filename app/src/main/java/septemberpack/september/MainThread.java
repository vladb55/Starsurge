package septemberpack.september;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Vlady on 10.10.2015.
 */

// Данный класс представляет собой отдельный поток отрисовки

public class MainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running; // Флаг, если true - поток работает, если false - поток завершен
    public static Canvas canvas; // Класс-полотно, предоставляющий методы для рисования

    public MainThread(SurfaceHolder holder, GamePanel gamePanel){
        super();
        this.surfaceHolder = holder;
        this.gamePanel = gamePanel;
    }

    // Метод установки флага потока в активное состояние
    void setRunning(boolean running){
        this.running = running;
    }

    // Метод, выполняющийся в потоке
    @Override
    public void run(){
        while(running){
            if(!gamePanel.pauseGame){ // Если не пауза, то работаем

                canvas = null;
                try{
                    // Получаем объект canvas и выполняем отрисовку
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder){
                        gamePanel.Update();
                        gamePanel.Draw(canvas);
                    }
                } catch(Exception ex){}
                finally {
                    if(canvas != null)
                        try {
                            // Отрисовка выполнена. Выводим результат на экран
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch(Exception e){}
                }

            }
        }
    }

}
