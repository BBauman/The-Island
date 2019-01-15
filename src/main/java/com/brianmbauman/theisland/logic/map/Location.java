package com.brianmbauman.theisland.logic.map;

import com.brianmbauman.theisland.logic.Element;
import com.brianmbauman.theisland.logic.exception.BadConfigurationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class Location {
    public enum FloodState {
        DRY,
        FLOODED,
        SUNK
    }

    public static List<Location> load() {

        try (InputStream locationsConfig = Location.class.getResourceAsStream("/locations.json5")){
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<LinkedList<Location>> responseType = new TypeReference<>() {};

            // TODO: Single global, preconfigured Mapper?
            mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

            MappingIterator<Location> mappingIterator = mapper.readerFor(Location.class).readValues(locationsConfig);
            return mappingIterator.readAll();
        } catch (IOException e) {
            throw new BadConfigurationException(e);
        }


    }

    private String name;
    private Element element;
    private FloodState floodState;

    @JsonCreator
    private Location (
            @JsonProperty("name") String name,
            @JsonProperty("element") String element
    ) {
        this.name       = name != null ? name : "Unknown";
        this.element    = element != null ? Element.fromName(element) : Element.NONE;
    }

    @Override
    public String toString(){
        return name + " (" + element.name() + ")";
    }

}
