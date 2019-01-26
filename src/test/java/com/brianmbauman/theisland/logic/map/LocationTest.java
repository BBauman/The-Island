package com.brianmbauman.theisland.logic.map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class LocationTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void load() {
        List list = Location.load();
        System.out.println(Arrays.toString(list.toArray()));
        Assertions.assertEquals(24, list.size(), "Incorrect number of locations loaded from configuration");
    }
}