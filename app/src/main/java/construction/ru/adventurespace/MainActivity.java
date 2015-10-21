package construction.ru.adventurespace;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
    Button buttonLeft,buttonRight;
    ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonLeft=(Button) findViewById(R.id.BLeft);
        buttonRight=(Button) findViewById(R.id.BRight);
        im=(ImageView) findViewById(R.id.fregat);
        View.OnClickListener OnClick=new View.OnClickListener() {
            float X;
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.BLeft:
                        im.setTranslationX(X-=5);
                        break;
                    case R.id.BRight:
                        im.setTranslationX(X+=5);
                        break;
                }
            }
        };
        buttonLeft.setOnClickListener(OnClick);
        buttonRight.setOnClickListener(OnClick);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
