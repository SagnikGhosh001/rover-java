package com.tw.step.rover.roversystem;

import com.tw.step.rover.boundary.InfinitePlateau;
import com.tw.step.rover.commands.MoveCommand;
import com.tw.step.rover.commands.RoverCommands;
import com.tw.step.rover.position.Coordinate;
import com.tw.step.rover.position.Direction;
import com.tw.step.rover.position.Navigator;
import com.tw.step.rover.rover.Rover;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoverMissionTest {
    @Test
    void shouldExecuteCommandsForAddedRover() {
        RoverMission roverMission = new RoverMission();
        Rover rover = new Rover(new Coordinate(0, 0), Direction.N);
        RoverCommands commands = new RoverCommands();
        commands.add(new MoveCommand(Navigator.create(), new InfinitePlateau()));

        roverMission.addRover(rover);
        roverMission.addCommands(commands);
        roverMission.execute();

        assertEquals("0 1 N Live", roverMission.toString());
    }
}
