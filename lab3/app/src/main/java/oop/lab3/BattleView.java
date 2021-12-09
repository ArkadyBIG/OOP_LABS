package oop.lab3;

import android.content.Context;
import android.graphics.*;
import android.view.SurfaceView;


public class BattleView extends SurfaceView {
    private Bitmap bitmap;
    private Paint paint;
    private Game game;
    private static int cellSize ;
    private int xStart = 0, yStart = 0;
    private int endEnemyFieldY;
    private int startEnemyFieldX;
    private Canvas canv = null;

    public BattleView(Context context, Game game) {
        super(context);
        paint = new Paint();
        setWillNotDraw(false);
        bitmap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.battle);
        this.game = game;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.save();
        drawBoard(canvas);
        canvas.restore();
        canvas.save();
        drawShips(canvas);
        canvas.restore();
        checkWinner(canvas);
    }

    private boolean checkWinner(Canvas canvas) {
        if(game.isEnemyWinner() || game.isPlayerWinner()) {
            paint.setColor(Color.MAGENTA);
            paint.setTextSize(72);
            if(game.isPlayerWinner())
                canvas.drawText("You won!",getWidth()/2,getHeight()/2,paint);
            else
                canvas.drawText("Game over!",getWidth()/2,getHeight()/2, paint );
            return true;
        }
        return false;
    }

    private void drawShips(Canvas canvas) {
        Paint rp = new Paint();
        for(Cell cell : game.getPlayerField().getCells())
        {
            Point pos = cell.getPosition();
            switch (cell.getState()) {
                case SHIP: rp.setARGB(255, 0, 150, 100); break;
                case MISSED: rp.setARGB(255, 0, 0, 100); break;
                case HIT: rp.setARGB(150, 250, 0, 0); break;
                default: continue;
            }
            canvas.drawRect( pos.x * cellSize,pos.y * cellSize,
                    (pos.x + 1) * cellSize, (pos.y + 1) * cellSize, rp);
        }

        canvas.translate(startEnemyFieldX,0);

        for(Cell cell : game.getEnemyField().getCells())
        {
            Point pos = cell.getPosition();
            switch (cell.getState()) {
                case MISSED: rp.setARGB(150, 0, 0, 100); break;
                case HIT: rp.setARGB(150, 250, 0, 0); break;
                default: continue;
            }
            canvas.drawRect( pos.x * cellSize,pos.y * cellSize,
                    (pos.x + 1) * cellSize, (pos.y + 1) * cellSize, rp);
        }
    }

    private void drawBoard(Canvas canvas) {
        cellSize = getWidth()/(2 * Field.SIZE) - 8;
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        int n = Field.SIZE * cellSize;
        for(int i = 0; i <= n; i += cellSize)
            canvas.drawLine(i, yStart, i, n, paint );
        for(int i = 0; i <= n; i += cellSize)
            canvas.drawLine(xStart, i, n, i, paint );

        startEnemyFieldX = n + cellSize;
        endEnemyFieldY = cellSize * Field.SIZE;
        canvas.translate(startEnemyFieldX,0);
        paint.setColor(Color.BLACK);

        for(int i = 0; i <= n; i += cellSize)
            canvas.drawLine(i, yStart, i, n, paint );

        for(int i = 0; i <= n; i += cellSize)
            canvas.drawLine(xStart, i, n, i, paint );
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int textPadding = 10;
        int shapeWidth = 100;
        int contentWidth = shapeWidth;
        int minw = contentWidth + getPaddingLeft() + getPaddingRight();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 0);
        int shapeHeight = 100;
        int minh = shapeHeight + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);
        setMeasuredDimension(w, h);
    }

    public int getEndEnemyFieldY() {
        return endEnemyFieldY;
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getStartEnemyFieldX() {
        return startEnemyFieldX;
    }
}
