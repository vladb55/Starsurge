package septemberpack.september;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Vlady on 10.10.2015.
 */

/* Данный класс содержит методы отрисовки графики в игре
* Особенность класса SurfaceView, от которого наследован наш класс, заключается в том, что он предоставляет
* отдельную область для рисования, действия с которой должны быть вынесены в отдельный
* поток приложения (класс MainThread)
* Также, GamePanel реализует интерфейс SurfaceHolder.Callback
* Этот интерфейс предлагает реализовать три метода: surfaceCreated(), surfaceChanged() и surfaceDestroyed()
* вызываемые соответственно при создании области для рисования, ее изменении и разрушении.
*/

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public static final int WIDTH = 1000; // Ширина фона
    public static final int HEIGHT = 1400; // Высота фона

    public MainThread thread; // Поток для отрисовки игрового процесса
    public boolean Pause_game; // Флаг для проверки не стоит ли пауза
    private Background background; // Объект для обращения к фону
    private Asteroid asteroid; // Объект для обращения к классу астероида
    public static int speed = 10; // Скорость движение
    public static int score = 0;
    public static int best = 0;
    private Player player;
    private Paint paint;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    void Draw(Canvas canvas){

        final float scaleFactorX = getWidth()/(WIDTH*1.f); // Подстраивание фона под размер экрана по ширине
        final float scaleFactorY = getHeight()/(HEIGHT*1.f); // Подстраивание фона под размер экрана по высоте

        if(!Pause_game) // Если не стоит пауза, рисуем
            if(canvas != null) {
                canvas.scale(scaleFactorX, scaleFactorY);
                background.draw(canvas);
                asteroid.draw(canvas);
                player.draw(canvas);
                drawText(canvas);
            }
    }

    void Update(){
        background.update();
        asteroid.update();
        player.update();
        if(collision()){
            Pause_game = true;
            setBest();
        }
        score = speed - 10;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        Game.fonSong.start();
        Game.fonSong.setLooping(true);
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.sky));
        asteroid = new Asteroid(BitmapFactory.decodeResource(getResources(), R.drawable.asteroid120px));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.ship));
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
                Game.fonSong.reset();
                retry = false;
            } catch(InterruptedException ex){}
        }
    }

    /**
     * Проверяет объекты на соударение
     * @return boolean
     */
    public boolean collision(){
        if(Rect.intersects(player.getShip(), asteroid.getAsteroid1())) {
            return true;
        }
        else if(Rect.intersects(player.getShip(), asteroid.getAsteroid2())) {
            return true;
        }
        else if(Rect.intersects(player.getShip(), asteroid.getAsteroid3())) {
            return true;
        }
        return false;
    }

    /**
     * Выводит количество очков на экран
     * @param canvas - прямоугольная область экрана для рисования
     */
    public void drawText(Canvas canvas){
        paint = new Paint();
        paint.setColor(Color.rgb(68, 201, 235));
        paint.setTextSize(72);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Score: " + score, 350, 72, paint);
        canvas.drawText("Best: " + best, 700, 72, paint);
    }

    private void setBest(){
        if(score > best)
            best = score;
    }
}
