package oop.lab3;

import java.util.Random;
import java.util.TreeMap;

public class Ship {

    private TreeMap<Point, Boolean> positions = new TreeMap<>();
    private Field field;

    public Ship(Point position, int size, Orientation orientation, Field field) {
        this.field = field;
        for (int i = 0; i < size; ++i) {
            if (orientation == Orientation.HORIZONTAL)
                positions.put(position.add(i, 0), false);
            else // orientation == Orientation.VERTICAL
                positions.put(position.add(0, i), false);
        }
    }

    public void init() {
        for (Point p : positions.keySet()) {
            field.getCell(p).setShip(this);
        }
    }

    public void tryHit(Point position) {
        positions.replace(position, true);
        if (!positions.containsValue(false))
            field.onShipDestroyed(this);
    }

    public TreeMap<Point, Boolean> getPositions() {
        return positions;
    }


}