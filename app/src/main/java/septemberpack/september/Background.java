package septemberpack.september;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Vlady on 11.10.2015.
 */
public class Background {

    Bitmap backBmp;
    int x, y;
    int ScreenWidth;
    int CountBackground;
    GamePanel root_gamePanel;

    public Background(Bitmap bmp, int ScreenWidth, GamePanel gamePanel){
        this.backBmp = bmp;
        this.ScreenWidth = ScreenWidth;
        this.x = 0;
        this.y = 0;
        CountBackground = ScreenWidth/backBmp.getWidth()+1;
        root_gamePanel = gamePanel;
    }

    public void draw(Canvas canvas){
        for(int i = 0; i < CountBackground+1; i++){
            if(canvas!=null)
                canvas.drawBitmap(backBmp, backBmp.getWidth()*i++, y, null);
        }
        if(Math.abs(x)>backBmp.getWidth()){
            x = x + backBmp.getWidth();
        }
    }

    public void update(float dt){
        x = (int) (x - root_gamePanel.ShipSpeed*dt);
    }

}
