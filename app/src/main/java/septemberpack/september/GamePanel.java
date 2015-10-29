package septemberpack.september;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Vlady on 10.10.2015.
 */

// Данный класс содержит методы отрисовки графики в игре
// Особенность класса SurfaceView, от которого наследован наш класс, заключается в том, что он предоставляет
// отдельную область для рисования, действия с которой должны быть вынесены в отдельный
// поток приложения (класс MainThread)
// Также, GamePanel реализует интерфейс SurfaceHolder.Callback
// Этот интерфейс предлагает реализовать три метода: surfaceCreated(), surfaceChanged() и surfaceDestroyed()
// вызываемые соответственно при создании области для рисования, ее изменении и разрушении.

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public static final int WIDTH = 1000; // Ширина фона
    public static final int HEIGHT = 1400; // Высота фона

    public MainThread thread; // Поток для отрисовки игрового процесса
    public boolean Pause_game; // Флаг для проверки не стоит ли пауза
    private Background background; // Объект для обращения к фону
    private Asteroid asteroid; // Объект для обращения к астероиду
    public static int speed = 10; // Скорость движение


    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    // Метод для рисования фона
    void Draw(Canvas canvas){

        final float scaleFactorX = getWidth()/(WIDTH*1.f); // Подстраивание фона под размер экрана по ширине
        final float scaleFactorY = getHeight()/(HEIGHT*1.f); // Подстраивание фона под размер экрана по высоте

        if(!Pause_game) // Если не стоит пауза, рисуем
            if(canvas != null) {
                canvas.scale(scaleFactorX, scaleFactorY);
                background.draw(canvas);
                asteroid.draw(canvas);
            }
    }

    // Вызов метода update в классе Background, который обновляет координаты фона
    void Update(){
        background.update();
        asteroid.update();
    }

    // Начало выполнения отрисовки. Инициализируем поток, загружаем фон, стартуем поток отрисовки
    // Воспроизводим музыку
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        Game.fonSong.start();
        Game.fonSong.setLooping(true);
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background_game));
        asteroid = new Asteroid(BitmapFactory.decodeResource(getResources(), R.drawable.asteroid80px));
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Метод вызывающийся при выходе из игры в главное меню, конец выполнения отрисовки
    // Прерываем музыку
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
}
