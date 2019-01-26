package com.brianmbauman.theisland.logic.map;

import com.brianmbauman.theisland.logic.adventurer.MovementStrategy;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class Map {

    private ImmutableBiMap<Coordinate, Location> map;

    public Map(String mapName, List<Location> locations) {
        this(MapLayout.LAYOUTS.get(mapName), locations);
    }

    public Map(MapLayout mapLayout, List<Location> locations) {

        Collections.shuffle(locations);
        ImmutableBiMap.Builder builder = ImmutableBiMap.builder();

        Iterator locationIterator = locations.iterator();
        for (Coordinate coordinate : mapLayout.getCoordinates()) {
            builder.put(coordinate, locationIterator.next());
        }

        map = builder.build();

    }

    public Coordinate coordinates(Location location) {
        return map.inverse().get(location);
    }

    public ImmutableSet<Location> findPath(Location start, Location end, MovementStrategy movementStrategy) {
        // TODO
        return null;
    }

    public ImmutableSet<Location> locations() {
        return map.values();
    }

    public Set<Location> neighbors(Location location, MovementStrategy movementStrategy) {
        Coordinate source = coordinates(location);
        int x = source.x;
        int y = source.y;
        Set<Location> neighbors = new HashSet<>();

        // Clockwise from top
        neighbors.add(map.get(new Coordinate(x, y-1)));
        neighbors.add(map.get(new Coordinate(x+1, y)));
        neighbors.add(map.get(new Coordinate(x, y+1)));
        neighbors.add(map.get(new Coordinate(x-1, y)));

        if (movementStrategy.usesDiagonals()) {
            // Clockwise from upper left
            neighbors.add(map.get(new Coordinate(x-1, y-1)));
            neighbors.add(map.get(new Coordinate(x+1, y-1)));
            neighbors.add(map.get(new Coordinate(x+1, y+1)));
            neighbors.add(map.get(new Coordinate(x-1, y+1)));
        }

        // Remove nulls
        neighbors.remove(null);

        // Remove if sunken
        if (!movementStrategy.usesSunk()) {
            for (Location neighbor : neighbors) {
                if (neighbor.getFloodState().equals(Location.FloodState.SUNK)){
                    neighbors.remove(neighbor);
                }
            }
        }

        return neighbors;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        map.forEach((coordinate, location) ->
                builder.append(coordinate)
                    .append(" : ")
                    .append(System.getProperty("line.separator")));

        return builder.toString();
    }
}
