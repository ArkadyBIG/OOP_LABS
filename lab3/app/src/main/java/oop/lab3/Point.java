package oop.lab3;

import androidx.annotation.Nullable;

public class Point implements Comparable {

    public int x = 0;
    public int y = 0;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point add(int x, int y) {
        return new Point(this.x + x, this.y + y);
    }

    public Point add(Point p) {
        return add(p.x, p.y);
    }

    public Point add(Direction direction) {
        int dx = 0;
        int dy = 0;
        switch (direction) {
            case LEFT: dx = -1; break;
            case TOP: dy = 1; break;
            case RIGHT: dx = 1; break;
            case BOTTOM: dy = -1; break;
        }
        return add(dx, dy);
    }

    @Override
    public int compareTo(Object o) {
        if (o == null)
            return 1;

        if (o == this)
            return 0;

        if (o instanceof Point) {
            Point p = (Point) o;
            if (x != p.x)
                return (x < p.x ? -1 : 1);
            if (y != p.y)
                return (y < p.y ? -1 : 1);
            return 0;
        }

        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (o instanceof Point) {
            Point p = (Point) o;
            return x == p.x && y == p.y;
        }
        return false;
    }
}
