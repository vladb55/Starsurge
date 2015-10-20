package septemberpack.september;


import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Game extends Activity {

    View pauseBtn; // View кнопки пауза
    View pauseMenu; // View меню паузы
    View leftBtn; // View кнопки влево
    View rightBtn; // View кнопки вправо
    View ship; // View корабля
    float X, MAX_WIDTH; // Стартовая координата корабля, максимальная ширина окна
    RelativeLayout Rel_main_game; // Основной лэйаут, на котором происходит игра
    GamePanel gamePanel; // Объект класса GamePanel
    MediaPlayer fonSong; // Объект для запуска музыки в игре

    // Обработчик нажатия на кнопку продолжить
    View.OnClickListener ContinueClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pauseMenu.setVisibility(View.GONE);
            pauseBtn.setVisibility(View.VISIBLE);
            gamePanel.Pause_game = false;
        }
    };

    // Обработчик нажатия на кнопку пауза
    View.OnClickListener GoMainClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fonSong.reset();
            gamePanel.thread.setRunning(false);
            Game.this.finish();
        }
    };

    // Обработчик нажатия на кнопку пауза
    View.OnClickListener PauseClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pauseBtn.setVisibility(View.GONE);
            pauseMenu.setVisibility(View.VISIBLE);
            gamePanel.Pause_game = true;
        }
    };

    // Обработчик нажатия на кнопку влево
    View.OnClickListener LeftClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            moveLeft();
        }
    };

    // Обработчик нажатия на кнопку вправо
    View.OnClickListener RightClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            moveRight();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        // Основной лэйаут, на который добавляются все игровые компоненты
        Rel_main_game = (RelativeLayout) findViewById(R.id.main_game_rl);

        // Получаем размеры окна
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        X = dm.widthPixels/2; // Точка старта игрока по x
        MAX_WIDTH = dm.widthPixels - 100; // Максимальная ширина окна

        gamePanel = new GamePanel(this);
        Rel_main_game.addView(gamePanel);

        // Инициализируем инфлейтер для преобразования View в Layout
        LayoutInflater myInflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);

        // Кнопка паузы
        pauseBtn = myInflater.inflate(R.layout.pause, null, false);
        pauseBtn.setX(0);
        pauseBtn.setY(0);
        Rel_main_game.addView(pauseBtn);
        pauseBtn.getLayoutParams().height=250;
        pauseBtn.getLayoutParams().width=250;

        // Меню паузы
        pauseMenu = myInflater.inflate(R.layout.pause_menu, null, false);
        Rel_main_game.addView(pauseMenu);
        pauseMenu.setVisibility(View.GONE);

        Button btnContinue = (Button) findViewById(R.id.btnContinue);
        Button btnGoMain = (Button) findViewById(R.id.btnGoMain);

        // Добавляем слушателей для меню паузы и кнопки паузы
        btnContinue.setOnClickListener(ContinueClick);
        btnGoMain.setOnClickListener(GoMainClick);
        pauseBtn.setOnClickListener(PauseClick);

        // Кнопка влево
        leftBtn = myInflater.inflate(R.layout.turn_left, null, false);
        leftBtn.setX(0);
        leftBtn.setY(dm.heightPixels - 100);
        Rel_main_game.addView(leftBtn);
        leftBtn.getLayoutParams().height=100;
        leftBtn.getLayoutParams().width=100;

        // Кнопка вправо
        rightBtn = myInflater.inflate(R.layout.turn_right, null, false);
        rightBtn.setX(dm.widthPixels-100);
        rightBtn.setY(dm.heightPixels-100);
        Rel_main_game.addView(rightBtn);
        rightBtn.getLayoutParams().height=100;
        rightBtn.getLayoutParams().width=100;

        // Добавляем слушателей
        leftBtn.setOnClickListener(LeftClick);
        rightBtn.setOnClickListener(RightClick);

        // Корабль
        ship = myInflater.inflate(R.layout.ship, null, false);
        ship.setX(X);
        ship.setY(dm.heightPixels-300);
        Rel_main_game.addView(ship);
        ship.getLayoutParams().height=80;
        ship.getLayoutParams().width=80;

        // Музыка в игре
        fonSong = MediaPlayer.create(Game.this, R.raw.fonsong);
        fonSong.start();
        fonSong.setLooping(true);
    }

    // Метод движения влево
    public void moveLeft(){
        if(X <= 0) X = 0;
        else
        ship.setX(X -= 40);
    }

    // Метод движения вправо
    public void moveRight(){
        if(X >= (MAX_WIDTH)) X = MAX_WIDTH - 20;
        else
        ship.setX(X += 40);
    }
}
