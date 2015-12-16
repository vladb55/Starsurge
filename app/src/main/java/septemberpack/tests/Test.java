package septemberpack.tests;

import septemberpack.september.*;
import android.test.InstrumentationTestCase;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.test.InstrumentationTestCase;
import android.view.SurfaceHolder;
/**
 * Created by USER on 16.12.2015.
 */
public class Test extends InstrumentationTestCase {

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
    Animation anim;
    Explosion exp;
    Bitmap[] bitmap;

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
        testBackground();
        testPlayer();
        testGame();
        testGamePanel();
        testThread();
        testAnimation();
        testExplosion();
    }

    public void testExplosion() {
        exp=new Explosion(bmp,0,0,12,12,11);
        exp.getHeight();
        exp.draw(canvas);
        exp.update();
    }

    public void testAnimation() {
        anim=new Animation();
        anim.getFrame();
        anim.getImage();
        anim.playedOnce();
        anim.setFrame(12);
        anim.setDelay(1000);
        anim.setFrames(bitmap);
        anim.update();
    }

    public void testThread() {
        mainthread=new MainThread(sh,gamepanel);
        mainthread.setRunning(true);
        mainthread.run();
    }

    public void testGamePanel() {
        gamepanel=new GamePanel(context);
        gamepanel.playMusic();
        gamepanel.getX();
        gamepanel.getY();
        gamepanel.collision();
        gamepanel.gameFailed=true;
        gamepanel.pauseGame=false;
    }

    public void testGame() {
        game=new Game();
        game.moveRight();
        game.moveLeft();
    }

    public void testPlayer() {
        player=new Player(bmp);
        player.draw(canvas);
        player.getShip();
        player.getY();
        player.getX();
        player.update();
    }

    public void testBackground() {
        back=new Background(bmp);
        back.draw(canvas);
        back.update();
    }

    public void testAsteroid() {
        asteroid=new Asteroid(bmp);
        asteroid.getAsteroid1();
        asteroid.getAsteroid2();
        asteroid.getAsteroid3();
        asteroid.draw(canvas);
        asteroid.update();
    }
}
