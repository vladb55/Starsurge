package septemberpack.september;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Vlady on 16.12.2015.
 */

/**
 * Класс реализует анимацию взрыва
 */
public class Explosion {
    private int x;
    private int y;
    private int width;
    private int height;
    private int row;
    private Animation animation;
    private Bitmap bitmap;

    /**
     * Конструктор, инициализируем массив с кадрами анимации, задаем задержку между кадрами
     * @param bmp - картинка со взрывами
     * @param x - позиция по х
     * @param y - позиция по у
     * @param w - ширина
     * @param h - высота
     * @param numFrames - количество кадров
     */
    public Explosion(Bitmap bmp, int x, int y, int w, int h, int numFrames){
        animation = new Animation();
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;

        Bitmap[] image = new Bitmap[numFrames];

        bitmap = bmp;

        for(int i = 0; i < image.length; i++){
            if(i % 5 == 0 && i > 0) row++;
            image[i] = Bitmap.createBitmap(bitmap, (i - (5 * row)) * width, row * height, width, height);
        }
        animation.setFrames(image);
        animation.setDelay(10);
    }

    /**
     * Метод отрисовки графики
     * @param canvas - прямоугольная область экрана для рисования
     */
    public void draw(Canvas canvas){
        if(!animation.playedOnce()){
            canvas.drawBitmap(animation.getImage(), x, y, null);
        }
    }

    /**
     * Обновление кадра
     */
    public void update(){
        if(!animation.playedOnce()){
            animation.update();
        }
    }

    /**
     * Метод возвращающий высоту кадра
     * @return - высота кадра
     */
    public int getHeight(){return height;}
}
