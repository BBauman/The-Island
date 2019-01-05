package com.brianmbauman.theisland.logic.map;

public class Coordinates {
    public final int x;
    public final int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coordinates)) {return false;}
        if (((Coordinates) o).x != x) {return false;}
        if (((Coordinates) o).y != y) {return false;}
        return true;
    }

    @Override
    public int hashCode() {
        return (x << 16) + y;
    }
}
