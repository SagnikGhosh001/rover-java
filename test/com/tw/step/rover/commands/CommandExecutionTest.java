package com.tw.step.rover.commands;

import com.tw.step.rover.boundary.InfinitePlateau;
import com.tw.step.rover.position.Coordinate;
import com.tw.step.rover.position.Direction;
import com.tw.step.rover.position.Navigator;
import com.tw.step.rover.rover.Rover;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandExecutionTest {
    @Test
    void shouldExecuteSingleCommands() {
        Navigator navigator = Navigator.create();
        InfinitePlateau boundary = new InfinitePlateau();
        Rover rover = new Rover(new Coordinate(0, 0), Direction.N);

        new TurnLeftCommand(navigator, boundary).execute(rover);
        assertEquals("0 0 W Live", rover.toString());

        new TurnRightCommand(navigator, boundary).execute(rover);
        assertEquals("0 0 N Live", rover.toString());

        new MoveCommand(navigator, boundary).execute(rover);
        assertEquals("0 1 N Live", rover.toString());
    }
}
