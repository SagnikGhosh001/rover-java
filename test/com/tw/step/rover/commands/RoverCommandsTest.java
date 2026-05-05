package com.tw.step.rover.commands;

import com.tw.step.rover.boundary.InfinitePlateau;
import com.tw.step.rover.position.Coordinate;
import com.tw.step.rover.position.Direction;
import com.tw.step.rover.position.Navigator;
import com.tw.step.rover.rover.Rover;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoverCommandsTest {
    @Test
    void shouldExecuteCommandsInSequence() {
        Navigator navigator = Navigator.create();
        InfinitePlateau boundary = new InfinitePlateau();
        RoverCommands commands = new RoverCommands();
        Rover rover = new Rover(new Coordinate(0, 0), Direction.N);

        commands.add(new TurnRightCommand(navigator, boundary));
        commands.add(new MoveCommand(navigator, boundary));
        commands.add(new MoveCommand(navigator, boundary));

        commands.execute(rover);

        assertEquals("2 0 E Live", rover.toString());
    }
}
