package com.brianmbauman.theisland.logic.map;

import com.brianmbauman.theisland.logic.adventurer.MovementStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MapTest {

    private Map map;

    @BeforeEach
    void setUp() {
        map = new Map("Default", Location.load());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void coordinates() {
        Assertions.assertAll(() ->
                    map.locations().forEach(location -> Assertions.assertSame(map.coordinates(location).getClass(), Coordinate.class))
        );
    }

    @Test
    void neighbors() {

        MovementStrategy diagonalMovement = new MovementStrategy(true, false, false);

        Assertions.assertAll(() ->
            map.locations().forEach(location ->
            {
                Assertions.assertTrue(map.neighbors(location, MovementStrategy.DEFAULT).size() >= 2);
                Assertions.assertTrue(map.neighbors(location, diagonalMovement).size() >= 3 );
            })
        );
    }
}