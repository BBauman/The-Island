package com.brianmbauman.theisland.logic;

public enum Element {
    NONE,
    AIR,
    EARTH,
    FIRE,
    WATER;

    public static Element fromName(String name) {
        for (Element element : Element.values()) {
            if (element.name().equalsIgnoreCase(name)) {
                return element;
            }
        }
        throw new IllegalArgumentException("No element exists for value " + name);
    }
}
