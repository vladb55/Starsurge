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

/* Класс игры, в котором содержатся основные компоненты игры
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
    public static ImageView imgAnim;
    private final static int DURATION = 50;
    private AnimationDrawable mAnimationDrawable = null;

    // Обработчик нажатия на кнопку продолжить
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

    // Обработчик нажатия на кнопку главное меню
    View.OnClickListener GoMainClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goToMainMenu();
        }
    };

    // Обработчик нажатия на кнопку пауза
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

        // Основной лэйаут, на который добавляются все игровые компоненты
        Rel_main_game = (RelativeLayout) findViewById(R.id.main_game_rl);

        // Получаем размеры окна
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        gamePanel = new GamePanel(this);
        Rel_main_game.addView(gamePanel);

        // Инициализируем инфлейтер для преобразования View в Layout
        LayoutInflater myInflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);

        // Кнопка паузы
        pauseBtn = myInflater.inflate(R.layout.pause, null, false);
        pauseBtn.setX(0);
        pauseBtn.setY(-50);
        Rel_main_game.addView(pauseBtn);
        pauseBtn.getLayoutParams().height = 200;
        pauseBtn.getLayoutParams().width = 200;
        pauseBtn.setOnClickListener(PauseClick);

        // Меню паузы
        pauseMenu = myInflater.inflate(R.layout.pause_menu, null, false);
        Rel_main_game.addView(pauseMenu);
        pauseMenu.setVisibility(View.GONE);

        // Кнопки меню паузы: продолжить и выйти в меню
        btnContinue = (Button) findViewById(R.id.btnContinue);
        btnGoMain = (Button) findViewById(R.id.btnGoMain);

        // Добавляем слушателей для меню паузы и кнопки паузы
        btnContinue.setOnClickListener(ContinueClick);
        btnGoMain.setOnClickListener(GoMainClick);

        // Кнопка влево
        leftBtn = myInflater.inflate(R.layout.turn_left, null, false);
        leftBtn.setX(0);
        leftBtn.setY(dm.heightPixels - 150);
        Rel_main_game.addView(leftBtn);
        leftBtn.getLayoutParams().height = 150;
        leftBtn.getLayoutParams().width = 150;
        leftBtn.setOnClickListener(LeftClick);

        // Кнопка вправо
        rightBtn = myInflater.inflate(R.layout.turn_right, null, false);
        rightBtn.setX(dm.widthPixels - 150);
        rightBtn.setY(dm.heightPixels - 150);
        Rel_main_game.addView(rightBtn);
        rightBtn.getLayoutParams().height = 150;
        rightBtn.getLayoutParams().width = 150;
        rightBtn.setOnClickListener(RightClick);

        // Инициализируем объект для музыки
        fonSong = MediaPlayer.create(Game.this, R.raw.fonsong);

        // Анимация взрыва
        imgAnim = new ImageView(getApplicationContext());
        Rel_main_game.addView(imgAnim);
    }

    // Метод движения влево
    public void moveLeft(){
        Player.left = true;
    }

    // Метод движения вправо
    public void moveRight(){
        Player.right = true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void startFrameAnimation() {
        BitmapDrawable fire0 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire0);
        BitmapDrawable fire1 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire1);
        BitmapDrawable fire2 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire2);
        BitmapDrawable fire3 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire3);
        BitmapDrawable fire4 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire4);
        BitmapDrawable fire5 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire5);
        BitmapDrawable fire6 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire6);
        BitmapDrawable fire7 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire7);
        BitmapDrawable fire8 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire8);
        BitmapDrawable fire9 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire9);
        BitmapDrawable fire10 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire10);
        BitmapDrawable fire11 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire11);
        BitmapDrawable fire12 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire12);
        BitmapDrawable fire13 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire13);
        BitmapDrawable fire14 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire14);
        BitmapDrawable fire15 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire15);
        BitmapDrawable fire16 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire16);
        BitmapDrawable fire17 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire17);
        BitmapDrawable fire18 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire18);
        BitmapDrawable fire19 = (BitmapDrawable) getResources().getDrawable(R.drawable.fire19);

        mAnimationDrawable = new AnimationDrawable();

        mAnimationDrawable.addFrame(fire0, DURATION);
        mAnimationDrawable.addFrame(fire1, DURATION);
        mAnimationDrawable.addFrame(fire2, DURATION);
        mAnimationDrawable.addFrame(fire3, DURATION);
        mAnimationDrawable.addFrame(fire4, DURATION);
        mAnimationDrawable.addFrame(fire5, DURATION);
        mAnimationDrawable.addFrame(fire6, DURATION);
        mAnimationDrawable.addFrame(fire7, DURATION);
        mAnimationDrawable.addFrame(fire8, DURATION);
        mAnimationDrawable.addFrame(fire9, DURATION);
        mAnimationDrawable.addFrame(fire10, DURATION);
        mAnimationDrawable.addFrame(fire11, DURATION);
        mAnimationDrawable.addFrame(fire12, DURATION);
        mAnimationDrawable.addFrame(fire13, DURATION);
        mAnimationDrawable.addFrame(fire14, DURATION);
        mAnimationDrawable.addFrame(fire15, DURATION);
        mAnimationDrawable.addFrame(fire16, DURATION);
        mAnimationDrawable.addFrame(fire17, DURATION);
        mAnimationDrawable.addFrame(fire18, DURATION);
        mAnimationDrawable.addFrame(fire19, DURATION);

        imgAnim.setBackground(mAnimationDrawable);
        mAnimationDrawable.setOneShot(true);

        imgAnim.setX(Player.x - 160);
        imgAnim.setY(Player.y - 100);

        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.setVisible(true, true);
            mAnimationDrawable.start();
        }
    }

    private void stopFrameAnimation() {
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
            mAnimationDrawable.setVisible(false, false);
        }
    }

    private void goToMainMenu(){
        gamePanel.thread.setRunning(false); // Стопаем поток
        Game.this.finish(); // Стопаем активити
    }
}
