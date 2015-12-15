package septemberpack.september;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Vlady on 09.11.2015.
 */

/**
 * Класс корабля
 */
public class Player {

    /**
     * Будущая картинка корабля
     */
    Bitmap bitmap;

    /**
     * Координаты корабля
     */
    static int x, y;

    /**
     * Флаги, отвечающие за перемещение вправо/влево
     * right = true - перемещение вправо
     * left = true - перемещение влево
     */
    public static boolean right = false;
    public static boolean left = false;

    /**
     * Конструктор принимает картинку корабля и задает начальные координаты
     * @param bmp - картинка игрока
     */
    public Player(Bitmap bmp){
        this.bitmap = bmp;

        x = 500;
        y = 1000;
    }

    /**
     * Метод отрисовки корабля
     * @param canvas - объект полотна для рисования
     */
    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, x, y, null);
    }

    /**
     * Метод обновляющий координаты перемещения игрока
     */
    public void update(){
        if(right) {
            if(x >= GamePanel.WIDTH - 100)
                x = GamePanel.WIDTH - 100;
            else {
                x += 80;
            }
            right = false;
        }

        if(left) {
            if(x <= 0)
                x = 0;
            else {
                x -= 80;
            }
            left = false;
        }
    }


    /**
     * Метод возвращает прямоугольную область корабля по его координатам
     * @return - возвращает прямоугольную область корабля по его координатам
     */
    public Rect getShip(){
        return new Rect(x, y+30, x + 80, y + 80);
    }

    /**
     * Метод возвращает координату x корабля
     * @return - возвращает координату x корабля
     */
    public int getX(){
        return x;
    }

    /**
     * Метод возвращает координату y корабля
     * @return - возвращает координату y корабля
     */
    public int getY(){
        return y;
    }

}
