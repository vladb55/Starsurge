package septemberpack.september;


import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Game extends Activity {

    View pauseBtn;
    View pauseMenu;
    RelativeLayout Rel_main_game;
    GamePanel gamePanel;

    View.OnClickListener ContinueClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pauseMenu.setVisibility(View.GONE);
            pauseBtn.setVisibility(View.VISIBLE);
            gamePanel.Pause_game = false;
        }
    };

    View.OnClickListener GoMainClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Game.this.finish();
        }
    };

    View.OnClickListener PauseClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pauseBtn.setVisibility(View.GONE);
            pauseMenu.setVisibility(View.VISIBLE);
            gamePanel.Pause_game = true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        Rel_main_game = (RelativeLayout) findViewById(R.id.main_game_rl);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        final int heightS = dm.heightPixels;
        final int widthS = dm.widthPixels;

        gamePanel = new GamePanel(getApplicationContext(), this, heightS);
        Rel_main_game.addView(gamePanel);



        LayoutInflater myInflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
        pauseBtn = myInflater.inflate(R.layout.pause, null, false);
        pauseBtn.setX(0); // width-250 (width - btn.size)
        pauseBtn.setY(0);

        Rel_main_game.addView(pauseBtn);

        pauseBtn.getLayoutParams().height=250;
        pauseBtn.getLayoutParams().width=250;

        pauseMenu = myInflater.inflate(R.layout.pause_menu, null, false);
        Rel_main_game.addView(pauseMenu);
        pauseMenu.setVisibility(View.GONE);

        Button btnContinue = (Button) findViewById(R.id.btnContinue);
        Button btnGoMain = (Button) findViewById(R.id.btnGoMain);

        btnContinue.setOnClickListener(ContinueClick);
        btnGoMain.setOnClickListener(GoMainClick);
        pauseBtn.setOnClickListener(PauseClick);
    }
}
