package com.brianmbauman.theisland.logic.map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MapTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void load() {
        Map map = new Map("/maps/default.json5", Location.load());
        System.out.println(map);
        Assertions.assertEquals(24, map.map.size(), "Fully loaded map does not contain 24 locations");
    }
}