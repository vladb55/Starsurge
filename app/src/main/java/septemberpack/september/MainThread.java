package septemberpack.september;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Vlady on 10.10.2015.
 */

/**
 * Данный класс представляет собой отдельный поток отрисовки
 */
public class MainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    /**
     * Конструктор принимающий объект холдера и объект геймпенела
     * @param holder - объект холдера для работы с полотном и синхронизации методов рисования
     * @param gamePanel - объект с панелью для вызова методов рисования и их синхронизации
     */
    public MainThread(SurfaceHolder holder, GamePanel gamePanel){
        super();
        this.surfaceHolder = holder;
        this.gamePanel = gamePanel;
    }

    /**
     * Метод установки флага потока в активное состояние
     * @param running = true - поток работает
     */
    public void setRunning(boolean running){
        this.running = running;
    }

    /**
     * Метод, выполняющийся в потоке
     */
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
