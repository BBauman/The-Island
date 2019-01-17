package com.brianmbauman.theisland.logic.map;

import com.brianmbauman.theisland.logic.adventurer.MovementStrategy;
import com.brianmbauman.theisland.logic.exception.BadConfigurationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableBiMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Map {

    private ImmutableBiMap<Coordinate, Location> map;

    private static LinkedHashSet<Coordinate> loadCoordinates(String coordinatesFile) {
        try (InputStream locationsConfig = Location.class.getResourceAsStream(coordinatesFile)){
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<Set<Coordinate>> responseType = new TypeReference<>() {};

            // TODO: Single global, preconfigured Mapper?
            mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

            MappingIterator<Coordinate> mappingIterator = mapper.readerFor(Coordinate.class).readValues(locationsConfig);

            return new LinkedHashSet<>(mappingIterator.readAll());
        } catch (IOException e) {
            throw new BadConfigurationException(e);
        }
    }

    public Map(String mapName, List<Location> locations) {
        this(loadCoordinates(mapName), locations);
    }

    public Map(Set<Coordinate> coordinates, List<Location> locations) {

        Collections.shuffle(locations);
        ImmutableBiMap.Builder builder = ImmutableBiMap.builder();

        Iterator locationIterator = locations.iterator();
        for (Coordinate coordinate : coordinates) {
            builder.put(coordinate, locationIterator.next());
        }

        map = builder.build();

    }

    public Coordinate coordinates(Location location) {
        return map.inverse().get(location);
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
        map.forEach((coordinate, location) -> {
            builder.append(coordinate + " : " + location);
            builder.append(System.getProperty("line.separator"));
        });

        return builder.toString();
    }
}
