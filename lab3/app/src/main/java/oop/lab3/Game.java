package oop.lab3;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.TreeSet;


public class Game {

    public Game() {
        init();
    }

    private final Field playerField = new Field();
    private final Field enemyField = new Field();
    private final ArrayList<Point> currentEnemyHit = new ArrayList<>();
    private static final Direction[] directions = { Direction.LEFT, Direction.TOP, Direction.RIGHT, Direction.BOTTOM };


    private Direction getOpposite(Direction direction) {
        switch (direction) {
            case LEFT: return Direction.RIGHT;
            case TOP: return Direction.BOTTOM;
            case RIGHT: return Direction.LEFT;
            case BOTTOM: return Direction.TOP;
        }
        return null;
    }

    public void init(){
        playerField.init();
        enemyField.init();
    }

    boolean hitEnemy(int x, int y) {
        return enemyField.tryHit(x, y);
    }

    boolean hitPlayer() {
        ArrayList<Point> availableHits = playerField.getAvailableHits();
        Point nextHit = null;
        boolean hitOnBegin = false;

        if (!currentEnemyHit.isEmpty()) {
            if (currentEnemyHit.size() >= 2) {
                Point first = currentEnemyHit.get(0);
                Point second = currentEnemyHit.get(1);
                int dx = second.x - first.x;
                int dy = second.y - first.y;
                nextHit = currentEnemyHit.get(currentEnemyHit.size() - 1).add(dx, dy);
                if (!availableHits.contains(nextHit)) {
                    nextHit = first.add(-dx, -dy);
                    hitOnBegin = true;
                }
            } else {
                for (Direction direction : directions) {
                    nextHit = currentEnemyHit.get(0).add(direction);
                    if (availableHits.contains(nextHit))
                        break;
                }
            }
        }

        if (nextHit == null) {
            Random rand = new Random();
            nextHit = availableHits.get(rand.nextInt(availableHits.size()));
        }

        int destroyedShipsBefore = playerField.getShipsDestroyed();
        if (playerField.tryHit(nextHit)) {
            int destroyedShipsAfter = playerField.getShipsDestroyed();

            if (destroyedShipsAfter > destroyedShipsBefore) {
                currentEnemyHit.clear();
            } else {
                if (hitOnBegin)
                    currentEnemyHit.add(0, nextHit);
                else
                    currentEnemyHit.add(nextHit);
            }
            return true;
        }
        return false;
    }

    public boolean isPlayerWinner() {
        return enemyField.areAllShipsDestroyed();
    }

    public boolean isEnemyWinner() {
        return playerField.areAllShipsDestroyed();
    }

    public Field getPlayerField() {
        return playerField;
    }

    public Field getEnemyField() {
        return enemyField;
    }
}
