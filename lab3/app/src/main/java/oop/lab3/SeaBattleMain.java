package oop.lab3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import static android.os.SystemClock.sleep;

public class SeaBattleMain extends Activity implements View.OnTouchListener {
    private BattleView battleView;
    private static Game game = new Game();

    private boolean isPlayerStep = true;
    private boolean isFirstCreate = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        battleView = new BattleView(this, game);
        battleView.setOnTouchListener(this);
        setContentView(battleView);
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        int cellSize = battleView.getCellSize();
        int startBoardX = battleView.getStartEnemyFieldX();
        int endBoardX = cellSize * Field.SIZE + startBoardX;
        int endBoardY = battleView.getEndEnemyFieldY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (x >= startBoardX && y <= endBoardY &&
                        x <= endBoardX) {
                    int gridx = (x - startBoardX) / cellSize;
                    int gridy = y / cellSize;
                    return playerAttackEnemy(gridx, gridy);
                }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_MOVE:
        }
        return true;
    }


    private boolean playerAttackEnemy(int x, int y){
        if (isPlayerStep && !game.isPlayerWinner() && !game.isEnemyWinner()) {
            if(game.hitEnemy(x, y)) {
                battleView.postInvalidate();
                return true;
            }

            enemyAttackPlayer();
        }
        return false;
    }

    private void enemyAttackPlayer() {
        isPlayerStep = false;

        while (game.hitPlayer()) {
            battleView.postInvalidate();
        }
        isPlayerStep = true;
        battleView.postInvalidate();
    }
}
