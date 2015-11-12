package septemberpack.september;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.test.InstrumentationTestCase;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Created by USER on 12.11.2015.
 */
public class Tests extends InstrumentationTestCase {

    Background back;
    Bitmap bmp;
    Player player;
    Canvas canvas;
    Game game;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void SaveTest(){
        testingBackground();
        testPlayer();
        testGame();
        init();
    }

    public void testGame() {
        game=new Game();
        game.moveLeft();
        game.moveRight();
        game.findViewById(R.id.btn_start);
    }


    private void init() {
        canvas=new Canvas();
        canvas.drawColor(Color.BLACK);
    }

    public void testPlayer() {
        player.update();
        player.getShip();
        player.draw(canvas);
        player.x=80;
        player.y=90;
    }


    public void testingBackground() {
        back=new Background();
        back.update();
        back.draw(canvas);
    }

}
