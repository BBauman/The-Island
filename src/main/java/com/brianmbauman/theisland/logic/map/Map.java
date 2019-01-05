package com.brianmbauman.theisland.logic.map;

import com.brianmbauman.theisland.logic.map.Location;

import java.util.HashSet;
import java.util.Set;

public class Map {



    private java.util.Map<Coordinates, Location> grid;

    public Coordinates coordinates(Location location) {
        // TODO
        return null;
    }

    public Set<Location> neighbors(Location location, boolean includeDiagonals) {
        Coordinates source = coordinates(location);
        int x = source.x;
        int y = source.y;
        Set<Location> neighbors = new HashSet<>();

        // Clockwise from top
        neighbors.add(grid.get(new Coordinates(x, y-1)));
        neighbors.add(grid.get(new Coordinates(x+1, y)));
        neighbors.add(grid.get(new Coordinates(x, y+1)));
        neighbors.add(grid.get(new Coordinates(x-1, y)));

        if (includeDiagonals) {
            // Clockwise from upper left
            neighbors.add(grid.get(new Coordinates(x-1, y-1)));
            neighbors.add(grid.get(new Coordinates(x+1, y-1)));
            neighbors.add(grid.get(new Coordinates(x+1, y+1)));
            neighbors.add(grid.get(new Coordinates(x-1, y+1)));
        }

        return neighbors;
    }


}
