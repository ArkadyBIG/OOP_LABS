package oop.lab3;

public class Cell {

    private final Point position;
    private final Field field;
    private Ship ship;
    private boolean wasHit = false;

    public Cell(int x, int y, Field field) {
        this.position = new Point(x, y);
        this.field = field;
    }

    public Cell(Point position, Field field) {
        this.position = position;
        this.field = field;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    public Point getPosition() {
        return position;
    }

    public boolean hasShip() {
        return ship != null;
    }

    public boolean tryHit() {
        if (wasHit)
            return true;

        wasHit = true;
        if (hasShip()) {
            ship.tryHit(position);
            return true;
        }
        return false;
    }

    public boolean wasHit() {
        return wasHit;
    }

    public CellState getState() {
        if (hasShip()) {
            if (wasHit)
                return CellState.HIT;
            return CellState.SHIP;
        }
        if (wasHit)
            return CellState.MISSED;
        return CellState.EMPTY;
    }
}
