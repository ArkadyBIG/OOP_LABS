package oop.lab3;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

public class Field {

    private final ArrayList<Cell> cells = new ArrayList<>();
    private int shipsDestroyed = 0;

    public static final int SIZE = 10;
    private static final int[] SHIPS = { 1, 1, 1, 1, 2, 2, 2, 3, 3, 4 };

    public Field() {
        for (int i = 0; i < SIZE * SIZE; ++i)
            cells.add(new Cell(i % SIZE, i / SIZE, this));
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public Cell getCell(Point position) {
        return getCell(position.x, position.y);
    }

    public Cell getCell(int x, int y) {
        if (!isValid(x, y))
            return null;
        return cells.get(x + y * SIZE);
    }

    public ArrayList<Point> getAvailableHits() {
        ArrayList<Point> result = new ArrayList<>();

        for (Cell cell : cells) {
            switch (cell.getState()) {
                case EMPTY:
                case SHIP:
                    result.add(cell.getPosition());
            }
        }

        return result;
    }

    public boolean isValid(Point position) {
        return isValid(position.x, position.y);
    }

    public boolean isValid(int x, int y) {
        return x >= 0 && x < SIZE &&
               y >= 0 && y < SIZE;
    }

    public boolean tryHit(Point position) {
        return tryHit(position.x, position.y);
    }

    public boolean tryHit(int x, int y) {
        Cell cell = getCell(x, y);

        if (cell == null)
            return true;

        return cell.tryHit();
    }

    public void onShipDestroyed(Ship destroyed) {
        ++shipsDestroyed;
        for (Point pos : destroyed.getPositions().keySet()) {
            for (int dx = -1; dx <= 1; ++dx)
            for (int dy = -1; dy <= 1; ++dy) {
                Cell cell = getCell(pos.add(dx, dy));
                if (cell != null)
                    cell.tryHit();
            }
        }
    }

    public boolean areAllShipsDestroyed() {
        return shipsDestroyed >= SHIPS.length;
    }

    public int getShipsDestroyed() {
        return shipsDestroyed;
    }

    public void init() {

        TreeSet<Point> availablePoints = new TreeSet<>();
        for (Cell cell : cells)
            availablePoints.add(cell.getPosition());

        for (int shipSize : SHIPS)
            generateShip(shipSize, availablePoints);
    }

    public void generateShip(int shipSize, TreeSet<Point> availablePoints) {
        ArrayList<Pair<Point, Orientation>> checkedPoints = new ArrayList<>();

        for (Point pos : availablePoints) {
            boolean canPlaceHorizontal = true;
            boolean canPlaceVertical = true;
            for (int i = 1; i < shipSize; ++i) {
                if (!availablePoints.contains(pos.add(i, 0)))
                    canPlaceHorizontal = false;
                if (!availablePoints.contains(pos.add(0, i)))
                    canPlaceVertical = false;
            }
            if (canPlaceHorizontal)
                checkedPoints.add(new Pair<>(pos, Orientation.HORIZONTAL));
            if (canPlaceVertical)
                checkedPoints.add(new Pair<>(pos, Orientation.VERTICAL));
        }

        if (checkedPoints.isEmpty())
            return;

        Random rand = new Random();
        Pair<Point, Orientation> chosen = checkedPoints.get(rand.nextInt(checkedPoints.size()));
        Ship generatedShip = new Ship(chosen.first, shipSize, chosen.second, this);

        for (Point pos : generatedShip.getPositions().keySet()) {
            for (int dx = -1; dx <= 1; ++dx)
            for (int dy = -1; dy <= 1; ++dy)
                availablePoints.remove(pos.add(dx, dy));
        }

        generatedShip.init();
    }
}
