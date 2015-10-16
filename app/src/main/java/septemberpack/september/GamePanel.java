package septemberpack.september;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Vlady on 10.10.2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public static final int WIDTH = 2000; // Ширина фона
    public static final int HEIGHT = 2800; // Высота фона

    public MainThread thread; // Поток для отрисовки игрового процесса
    public boolean Pause_game; // Флаг для проверки не стоит ли пауза
    private Background background; // Объект для обращения к фону

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
            }
    }

    // Вызов метода update в классе Background, который обновляет координаты фона
    void Update(){
        background.update();
    }

    // Начало выполнения отрисовки. Инициализируем поток, загружаем фон, стартуем поток отрисовки
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.back_game));
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Метод вызывающийся при выходе из игры в главное меню, конец выполнения отрисовки
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch(InterruptedException ex){}
        }
    }
}
