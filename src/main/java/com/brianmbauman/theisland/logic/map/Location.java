package com.brianmbauman.theisland.logic.map;

import com.brianmbauman.theisland.logic.Element;

public class Location {
    public enum FloodState {
        DRY,
        FLOODED,
        SUNK
    }

    private String name;
    private Element element;
    private FloodState floodState;

    private Map map;



}
