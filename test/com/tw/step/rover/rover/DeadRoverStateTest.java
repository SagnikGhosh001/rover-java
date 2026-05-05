package com.tw.step.rover.rover;

import com.tw.step.rover.boundary.InfinitePlateau;
import com.tw.step.rover.position.Coordinate;
import com.tw.step.rover.position.Direction;
import com.tw.step.rover.position.Navigator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class DeadRoverStateTest {
    @Test
    void shouldIgnoreAllCommands() {
        Rover rover = new Rover(new Coordinate(1, 1), Direction.N);
        DeadRoverState state = new DeadRoverState(rover);
        Navigator navigator = Navigator.create();
        InfinitePlateau boundary = new InfinitePlateau();

        assertSame(state, state.turnLeft(navigator, boundary));
        assertSame(state, state.turnRight(navigator, boundary));
        assertSame(state, state.move(navigator, boundary));
        assertEquals("1 1 N Live", rover.toString());
    }
}
