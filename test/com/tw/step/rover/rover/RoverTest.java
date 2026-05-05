package com.tw.step.rover.rover;

import com.tw.step.rover.boundary.InfinitePlateau;
import com.tw.step.rover.position.Coordinate;
import com.tw.step.rover.position.Direction;
import com.tw.step.rover.position.Navigator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoverTest {
    @Test
    void shouldTurnAndMove() {
        Rover rover = new Rover(new Coordinate(0, 0), Direction.N);
        Navigator navigator = Navigator.create();
        InfinitePlateau boundary = new InfinitePlateau();

        rover.turnRight(navigator, boundary);
        rover.move(navigator, boundary);

        assertEquals("1 0 E Live", rover.toString());
    }
}
