package septemberpack.september;


import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Класс игры, в котором содержатся основные компоненты игры
 * Данный класс содержит лэйаут, на котором собраны все отображаемые компоненты, такие как
 * кнопка паузы, меню паузы, кнопки управления кораблем, добавление объекта класса GamePanel
 * для отображения графики
 */
public class Game extends Activity {

    private View pauseBtn; // View кнопки пауза
    private View pauseMenu; // View меню паузы
    private View leftBtn; // View кнопки влево
    private View rightBtn; // View кнопки вправо
    private RelativeLayout Rel_main_game; // Основной лэйаут, на котором происходит игра
    private GamePanel gamePanel; // Объект класса GamePanel
    public static MediaPlayer fonSong; // Объект для запуска музыки в игре
    private Button btnContinue;
    private Button btnGoMain;

    /**
     * Обработчик нажатия на кнопку продолжить
     */
    View.OnClickListener ContinueClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!gamePanel.gameFailed) {
                pauseMenu.setVisibility(View.GONE); // Прячем меню паузы
                pauseBtn.setVisibility(View.VISIBLE); // Показываем кнопку паузы
                leftBtn.setVisibility(View.VISIBLE); // Показываем кнопку влево
                rightBtn.setVisibility(View.VISIBLE); // Показываем кнопку вправо
                gamePanel.pauseGame = false; // Флаг паузы на ноль - игра продолжается
            }
            else btnContinue.setEnabled(false);
        }
    };

    /**
     * Обработчик нажатия на кнопку главное меню
     */
    View.OnClickListener GoMainClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goToMainMenu();
        }
    };

    /**
     * Обработчик нажатия на кнопку пауза
     */
    View.OnClickListener PauseClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pauseBtn.setVisibility(View.GONE); // Прячем кнопку паузы
            leftBtn.setVisibility(View.GONE); // Прячем кнопку влево
            rightBtn.setVisibility(View.GONE); // Прячем кнопку вправо
            pauseMenu.setVisibility(View.VISIBLE); // Показывает меню паузы
            gamePanel.pauseGame = true; // Ставим игру на стоп
        }
    };

    /**
     * Обработчик нажатия на кнопку влево
     */
    View.OnClickListener LeftClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            moveLeft();
        }
    };

    /**
     * Обработчик нажатия на кнопку вправо
     */
    View.OnClickListener RightClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            moveRight();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(!gamePanel.gameFailed) {
                if (!gamePanel.pauseGame) {
                    gamePanel.pauseGame = true;
                    pauseBtn.setVisibility(View.GONE);
                    leftBtn.setVisibility(View.GONE);
                    rightBtn.setVisibility(View.GONE);
                    pauseMenu.setVisibility(View.VISIBLE);
                } else {
                    gamePanel.pauseGame = false;
                    pauseBtn.setVisibility(View.VISIBLE);
                    leftBtn.setVisibility(View.VISIBLE);
                    rightBtn.setVisibility(View.VISIBLE);
                    pauseMenu.setVisibility(View.GONE);
                }
            }
        }

        return true;
    }

    @Override
    protected void onUserLeaveHint(){
        goToMainMenu();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        /**
         * Основной лэйаут, на который добавляются все игровые компоненты
         */
        Rel_main_game = (RelativeLayout) findViewById(R.id.main_game_rl);

        /**
         * Получаем размеры окна
         */
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        gamePanel = new GamePanel(this);
        Rel_main_game.addView(gamePanel);

        /**
         * Инициализируем инфлейтер для преобразования View в Layout
         */
        LayoutInflater myInflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);

        /**
         * Кнопка паузы
         */
        pauseBtn = myInflater.inflate(R.layout.pause, null, false);
        pauseBtn.setX(0);
        pauseBtn.setY(-50);
        Rel_main_game.addView(pauseBtn);
        pauseBtn.getLayoutParams().height = 200;
        pauseBtn.getLayoutParams().width = 200;
        pauseBtn.setOnClickListener(PauseClick);

        /**
         * Меню паузы
         */
        pauseMenu = myInflater.inflate(R.layout.pause_menu, null, false);
        Rel_main_game.addView(pauseMenu);
        pauseMenu.setVisibility(View.GONE);

        /**
         * Кнопки меню паузы: продолжить и выйти в меню
         */
        btnContinue = (Button) findViewById(R.id.btnContinue);
        btnGoMain = (Button) findViewById(R.id.btnGoMain);

        /**
         * Добавляем слушателей для меню паузы и кнопки паузы
         */
        btnContinue.setOnClickListener(ContinueClick);
        btnGoMain.setOnClickListener(GoMainClick);

        /**
         * Кнопка влево
         */
        leftBtn = myInflater.inflate(R.layout.turn_left, null, false);
        leftBtn.setX(0);
        leftBtn.setY(dm.heightPixels - 150);
        Rel_main_game.addView(leftBtn);
        leftBtn.getLayoutParams().height = 150;
        leftBtn.getLayoutParams().width = 150;
        leftBtn.setOnClickListener(LeftClick);

        /**
         * Кнопка вправо
         */
        rightBtn = myInflater.inflate(R.layout.turn_right, null, false);
        rightBtn.setX(dm.widthPixels - 150);
        rightBtn.setY(dm.heightPixels - 150);
        Rel_main_game.addView(rightBtn);
        rightBtn.getLayoutParams().height = 150;
        rightBtn.getLayoutParams().width = 150;
        rightBtn.setOnClickListener(RightClick);

        /**
         * Инициализируем объект для музыки
         */
        fonSong = MediaPlayer.create(Game.this, R.raw.fonsong);
    }

    /**
     * Метод движения влево
     */
    public void moveLeft(){
        Player.left = true;
    }

    /**
     * Метод движения вправо
     */
    public void moveRight(){
        Player.right = true;
    }

    /**
     * Метод останавливающий поток и совершает переход в главное меню игры
     */
    private void goToMainMenu(){
        gamePanel.thread.setRunning(false); // Стопаем поток
        Game.this.finish(); // Стопаем активити
    }
}
