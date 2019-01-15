package com.brianmbauman.theisland.logic.map;

import com.brianmbauman.theisland.logic.map.Location;
import com.google.common.collect.ImmutableBiMap;

import java.util.HashSet;
import java.util.Set;

public class Map {

    private ImmutableBiMap<Coordinates, Location> map;

    public Coordinates coordinates(Location location) {
        return map.inverse().get(location);
    }

    public Set<Location> neighbors(Location location, boolean includeDiagonals) {
        Coordinates source = coordinates(location);
        int x = source.x;
        int y = source.y;
        Set<Location> neighbors = new HashSet<>();

        // Clockwise from top
        neighbors.add(map.get(new Coordinates(x, y-1)));
        neighbors.add(map.get(new Coordinates(x+1, y)));
        neighbors.add(map.get(new Coordinates(x, y+1)));
        neighbors.add(map.get(new Coordinates(x-1, y)));

        if (includeDiagonals) {
            // Clockwise from upper left
            neighbors.add(map.get(new Coordinates(x-1, y-1)));
            neighbors.add(map.get(new Coordinates(x+1, y-1)));
            neighbors.add(map.get(new Coordinates(x+1, y+1)));
            neighbors.add(map.get(new Coordinates(x-1, y+1)));
        }

        return neighbors;
    }


}
