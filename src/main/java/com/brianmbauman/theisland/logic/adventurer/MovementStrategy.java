package com.brianmbauman.theisland.logic.adventurer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MovementStrategy {

    private final boolean usesDiagonals;
    private final boolean usesFlight;
    private final boolean usesSunk;

    @JsonCreator
    public MovementStrategy (
            @JsonProperty("usesDiagonals") boolean usesDiagonals,
            @JsonProperty("usesFlight") boolean usesFlight,
            @JsonProperty("usesSunk") boolean usesSunk
    ) {
        this.usesDiagonals = usesDiagonals;
        this.usesFlight = usesFlight;
        this.usesSunk = usesSunk;
    }

    public boolean usesDiagonals() {
        return usesDiagonals;
    }

    public boolean usesFlight() {
        return usesFlight;
    }

    public boolean usesSunk() {
        return usesSunk;
    }
}
