package septemberpack.september;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Vlady on 23.02.2016.
 */
public class Bonus {

    Random rnd;
    Bitmap bitmap;
    int x, y;

    public Bonus(Bitmap bmp){
        this.bitmap = bmp;
        rnd = new Random();
        x = rnd.nextInt(900);
        y = -rnd.nextInt(10) - 100;
    }

    public void draw(Canvas canvas){
        if(readyToGo()) {
            canvas.drawBitmap(bitmap, x, y, null);
        }
    }

    public void update(){
        if(!readyToGo()){
            x = rnd.nextInt(900);
            y = -100;
        }
        y += GamePanel.speed;
    }

    /**
     * Запускает бонус
     * Убирает бонус после взятия, т.к. остаток становится не равным нулю
     * @return
     */
    private boolean readyToGo(){
        if(GamePanel.score % 4 == 0 && GamePanel.score != 0){
            return true;
        }
        return false;
    }

    /**
     * Возвращает область, которую занимает бонус в данный момент
     * @return
     */
    public Rect getBonus(){
        return new Rect(x, y, x + 100, y + 100);
    }

}
