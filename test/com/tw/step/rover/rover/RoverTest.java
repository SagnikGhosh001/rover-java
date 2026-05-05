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

    @Test
    void shouldMarkBothRoversDeadWhenAtSamePosition() {
        Rover first = new Rover(new Coordinate(1, 1), Direction.N);
        Rover second = new Rover(new Coordinate(1, 1), Direction.E);

        first.checkCollision(second);

        assertEquals("1 1 N Dead", first.toString());
        assertEquals("1 1 E Dead", second.toString());
    }

    @Test
    void shouldMarkBothRoversDeadWhenAtSamePositionWhenCallingFromSecondRover() {
        Rover first = new Rover(new Coordinate(1, 1), Direction.N);
        Rover second = new Rover(new Coordinate(1, 1), Direction.E);

        second.checkCollision(first);

        assertEquals("1 1 N Dead", first.toString());
        assertEquals("1 1 E Dead", second.toString());
    }

    @Test
    void shouldNotMarkDeadWhenAtDifferentPositions() {
        Rover first = new Rover(new Coordinate(1, 1), Direction.N);
        Rover second = new Rover(new Coordinate(2, 1), Direction.N);

        first.checkCollision(second);

        assertEquals("1 1 N Live", first.toString());
        assertEquals("2 1 N Live", second.toString());
    }
}
