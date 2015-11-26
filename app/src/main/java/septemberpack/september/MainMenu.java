package septemberpack.september;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// Класс главного меню, содержит кнопку старта и ее слушателя

public class MainMenu extends Activity {

    Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btn_start = (Button) findViewById(R.id.btn_start); // Кнопка старт игры
        // Слушатель для кнопки старта
        btn_start.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent = new Intent(MainMenu.this, Game.class); // Интент для перехода с меню в игру
                startActivity(intent);
                GamePanel.speed = 10;
                GamePanel.score = 0;
            }
        });
    }
}
