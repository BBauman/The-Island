package com.brianmbauman.theisland.logic.map;

import com.brianmbauman.theisland.logic.exception.BadConfigurationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class MapLayout implements Comparable<MapLayout> {

    static final java.util.Map<String, MapLayout> LAYOUTS = loadLayouts("/maps");
    private final String name;
    private final Set<Coordinate> coordinates;

    @JsonCreator
    private MapLayout(
            @JsonProperty("name") String name,
            @JsonProperty("coordinates") Set<Coordinate> coordinates
    ) {
        this.name = name;
        this.coordinates = coordinates;
    }

    private static java.util.Map<String, MapLayout> loadLayouts(String resourceFolder) {

        java.util.Map<String, MapLayout> layouts = new HashMap<>();

        try {
            Set<Path> mapPaths = Files.walk(Paths.get((MapLayout.class.getResource(resourceFolder).toURI())))
                    .filter(path -> path.toString().endsWith(".json5"))
                    .map(Path::toAbsolutePath)
                    .collect(Collectors.toSet());

            for (Path path : mapPaths) {
                try {
                    MapLayout layout  = loadLayoutFile(path);
                    layouts.put(layout.name, layout);
                } catch (BadConfigurationException e) {
                    // TODO: Log warning if single map is missing, but carry on.
                }
            }

        } catch (IOException | URISyntaxException e) {
            throw new BadConfigurationException("Cannot read map files", e);
        }

        if (layouts.isEmpty()) {
            throw new BadConfigurationException("No valid maps were found.");
        }

        return Collections.unmodifiableMap(layouts);
    }

    private static MapLayout loadLayoutFile(Path path) {
        try (InputStream locationsConfig = Files.newInputStream(path)) {
            ObjectMapper mapper = new ObjectMapper();

            // TODO: Single global, preconfigured Mapper?
            mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

            return mapper.readValue(locationsConfig, MapLayout.class);
        } catch (IOException e) {
            throw new BadConfigurationException(e);
        }
    }

    public String getName() {
        return name;
    }

    public Set<Coordinate> getCoordinates() {
        return coordinates;
    }

    @Override
    public int compareTo(MapLayout o) {
        return this.name.compareTo(o.name);
    }
}
