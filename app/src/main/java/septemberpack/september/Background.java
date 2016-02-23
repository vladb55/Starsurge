package septemberpack.september;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Vlady on 11.10.2015.
 */

/**
 * Данный класс реализует отрисовку фона игры
 */
public class Background {

    Bitmap bitmap; // Объект нашего фона - картинка
    int x, y, dy; // Координата x, y для фона. Скорость движения фона

    /**
     * Конструктор получаем объект будущей картинки, устанавливаем координаты на ноль,
     * устанавливаем начальную скорость
     * @param bmp
     */
    public Background(Bitmap bmp) {
        bitmap = bmp;
        x = 0;
        y = 0;
        dy = GamePanel.speed;
    }

    /**
     *   Рисуем фон. При смещении (изменении) y в методе update,
     *   рисуем картинку фона снова, но уже по координате y-2800. 2800 - длинна картинки фона.
     *   Иными словами, мы нарисовали фон по координатам (0, 0), сместили y,
     *   Нарисовали на смещенном месте и опять сместили.
     *   Создается впечатление, что фон плавно (в зависимости от устройства) движется вниз
     *   @param canvas - прямоугольная область экрана для рисования
     */
    public void draw(Canvas canvas) {
        if(canvas != null)
            canvas.drawBitmap(bitmap, x, y, null);
        if(y > 0)
            canvas.drawBitmap(bitmap, x, y - 1400, null);
    }

    /**
     * Изменение координаты y, после каждого пролета обнуляем координату
     * Создается впечатление движения фона
     * Скорость увеличивается на одну единицу спустя каждый прогон картинки в ее полную длину
     */
    public void update() {
        y += dy;
        if(y > 1400) {
            y = 0;
            GamePanel.speed += 1;
            GamePanel.score += 1;
        }
    }

}
