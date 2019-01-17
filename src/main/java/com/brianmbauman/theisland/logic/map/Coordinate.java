package com.brianmbauman.theisland.logic.map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinate {
    public final int x;
    public final int y;

    @JsonCreator
    public Coordinate(
            @JsonProperty("x") int x,
            @JsonProperty("y") int y
        ) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coordinate)) {return false;}
        if (((Coordinate) o).x != x) {return false;}
        if (((Coordinate) o).y != y) {return false;}
        return true;
    }

    @Override
    public int hashCode() {
        return (x << 16) + y;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }
}
