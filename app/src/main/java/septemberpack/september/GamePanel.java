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
    public boolean pauseGame; // Флаг для проверки не стоит ли пауза
    private Background background; // Объект для обращения к фону
    private Asteroid asteroid; // Объект для обращения к классу астероида
    private Player player; // Объект для обращения к классу корабля
    private Explosion explosion;
    private Bonus bonus;
    public static int speed = 10; // Скорость движение
    public static int score = 0;
    public static int best = 0;
    private Paint paint;
    public boolean gameFailed;


    /**
     * Конструктор получающий объект холдера для работы с полотном
     * @param context - активити с которым работаем
     */
    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * Метод отрисовки графики
     * @param canvas - прямоугольная область экрана для рисования
     */
    void Draw(Canvas canvas){

        final float scaleFactorX = getWidth() / (WIDTH * 1.f); // Подстраивание фона под размер экрана по ширине
        final float scaleFactorY = getHeight() / (HEIGHT * 1.f); // Подстраивание фона под размер экрана по высоте

        if(!pauseGame)
            if(canvas != null) {
                canvas.scale(scaleFactorX, scaleFactorY);
                background.draw(canvas);
                asteroid.draw(canvas);
                player.draw(canvas);
                bonus.draw(canvas);
                drawScore(canvas);
            }
        if(canvas != null) {
            if (gameFailed) {
                drawLoseText(canvas);
                explosion.draw(canvas);
            }
        }
    }

    /**
     * Метод выполняющий обновление координат объектов на экране
     */
    void Update(){
        if(!gameFailed) {
            background.update();
            asteroid.update();
            player.update();
            bonus.update();
            checkCollision();
        }
        else {
            explosion.update();
            setBest();
            stopMusic();
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        playMusic();
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.sky));
        asteroid = new Asteroid(BitmapFactory.decodeResource(getResources(), R.drawable.asteroid));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.ship));
        bonus = new Bonus(BitmapFactory.decodeResource(getResources(), R.drawable.bonus));
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
                stopMusic();
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

    public boolean bonusTaken(){
        if(Rect.intersects(player.getShip(), bonus.getBonus())) {
            return true;
        }
        return false;
    }

    /**
     * Выводит количество очков на экран
     * @param canvas - прямоугольная область экрана для рисования
     */
    private void drawScore(Canvas canvas){
        paint = new Paint();
        paint.setColor(Color.rgb(68, 201, 235));
        paint.setTextSize(72);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Score: " + score, 350, 72, paint);
        canvas.drawText("Best: " + best, 700, 72, paint);
    }

    /**
     * Метод выводящий сообщение о проигрыше
     * @param canvas - прямоугольная область экрана для рисования
     */
    private void drawLoseText(Canvas canvas){
        if(!pauseGame) {
            paint = new Paint();
            paint.setColor(Color.rgb(68, 201, 235));
            paint.setTextSize(144);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("F A I L E D", 200, 700, paint);
        }
    }

    /**
     * Метод устанавливающий лучший результат
     */
    private void setBest(){
        if(score > best)
            best = score;
    }

    /**
     * Метод запуска музыки
     */
    public void playMusic(){
        Game.fonSong.start();
        Game.fonSong.setLooping(true);
    }

    /**
     * Метод остановки музыки
     */
    private void stopMusic(){
        Game.fonSong.reset();
    }

    /**
     * Метод проверки на проигрыш
     */
    private void checkCollision(){
        if(collision()){
            gameFailed = true;
            explosion = new Explosion(BitmapFactory.decodeResource(getResources(), R.drawable.explosion), player.getX(),
                    player.getY() - 30, 100, 100, 25);
        }
        if(bonusTaken()){
            score += 10;
        }
    }
}
