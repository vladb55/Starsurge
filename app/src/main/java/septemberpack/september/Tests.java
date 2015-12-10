package septemberpack.september;

/**
 * Created by USER on 10.12.2015.
 */
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.test.InstrumentationTestCase;
import android.view.SurfaceHolder;

import junit.framework.TestSuite;

public class Tests extends InstrumentationTestCase {

        Background back;
        Bitmap bmp;
        Player player;
        Canvas canvas;
        Game game;
        Asteroid asteroid;
        GamePanel gamepanel;
        Context context;
        MainThread mainthread;
        SurfaceHolder sh;
        @Override
        protected void setUp() throws Exception {
            super.setUp();
        }

        @Override
        protected void tearDown() throws Exception {
            super.tearDown();
        }

        public void SaveTest(){
            testAsteroid();
            testPlayer();
            testBackground();
            testGame();
            testGamePanel();
            testThread();
            init();
        }

    public void testThread() {
        mainthread=new MainThread(sh,gamepanel);
        mainthread.setRunning(true);
        mainthread.run();
    }


    public void testGamePanel() {
        gamepanel=new GamePanel(context);
        gamepanel.Draw(canvas);
        gamepanel.Update();
        gamepanel.collision();
    }

    public void testGame() {
        game=new Game();
        game.startFrameAnimation();
        game.onUserLeaveHint();
        game.moveLeft();
        game.moveRight();
    }

    public void testBackground() {
        back=new Background(bmp);
        back.draw(canvas);
        back.x=0;
        back.y=10;
        back.dy=6;
        back.update();
    }

    public void testPlayer() {
        player=new Player(bmp);
        player.draw(canvas);
        player.getShip();
        player.getX();
        player.getY();
        player.update();
    }

    public void testAsteroid() {
        asteroid=new Asteroid(bmp);
        asteroid.draw(canvas);
        asteroid.getAsteroid1();
        asteroid.getAsteroid2();
        asteroid.getAsteroid3();
        asteroid.update();
    }

    private void init() {
            canvas=new Canvas();
            canvas.drawColor(Color.BLACK);
        }
}
