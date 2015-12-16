package septemberpack.september;

import android.graphics.Bitmap;

/**
 * Created by Vlady on 16.12.2015.
 */

/**
 * Класс упрощающий работу с анимацией
 */
public class Animation {

    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    /**
     * Метод выполняющий инициализацию массива с кадрами
     * @param frames - массив с кадрами
     */
    public void setFrames(Bitmap[] frames){
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }

    /**
     * Метод установки задержки перехода между кадрами
     * @param d - значение задержки в мс
     */
    public void setDelay(long d){delay = d;}
    public void setFrame(int i){currentFrame = i;}

    /**
     * Метод обновляющий кадр
     */
    public void update(){
        long elapsed = (System.nanoTime() - startTime) / 1000000;

        if(elapsed > delay){
            currentFrame++;
            startTime = System.nanoTime();
        }

        if(currentFrame == frames.length){
            currentFrame = 0;
            playedOnce = true;
        }
    }

    /**
     * Метод возвращает значение текущего кадра
     * @return - значение текущего кадра
     */
    public Bitmap getImage(){
        return frames[currentFrame];
    }

    public int getFrame(){return currentFrame;}

    /**
     * Метод возвращающий значение флага
     * @return - значение флага
     */
    public boolean playedOnce(){return playedOnce;}

}
