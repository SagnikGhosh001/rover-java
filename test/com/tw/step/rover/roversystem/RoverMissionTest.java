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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void shouldReturnTrueWhenCommandsRemain() {
        RoverMission mission = missionWithMoveCommands(2);

        assertTrue(mission.hasMoreCommand());
    }

    @Test
    void shouldReturnFalseWhenAllCommandsExecuted() {
        RoverMission mission = missionWithMoveCommands(1);
        mission.executeNext();

        assertFalse(mission.hasMoreCommand());
    }

    @Test
    void shouldExecuteOneCommandAtATime() {
        RoverMission mission = missionWithMoveCommands(2);

        mission.executeNext();

        assertEquals("0 1 N Live", mission.toString());
        assertTrue(mission.hasMoreCommand());

        mission.executeNext();

        assertEquals("0 2 N Live", mission.toString());
        assertFalse(mission.hasMoreCommand());
    }

    @Test
    void shouldMarkBothMissionsDeadOnCollision() {
        RoverMission first = missionAt(new Coordinate(1, 1));
        RoverMission second = missionAt(new Coordinate(1, 1));

        first.checkCollision(second);

        assertEquals("1 1 N Dead", first.toString());
        assertEquals("1 1 N Dead", second.toString());
    }

    @Test
    void shouldNotMarkDeadWhenMissionsAtDifferentPositions() {
        RoverMission first = missionAt(new Coordinate(0, 0));
        RoverMission second = missionAt(new Coordinate(1, 1));

        first.checkCollision(second);

        assertEquals("0 0 N Live", first.toString());
        assertEquals("1 1 N Live", second.toString());
    }

    private RoverMission missionWithMoveCommands(int count) {
        RoverMission mission = new RoverMission();
        mission.addRover(new Rover(new Coordinate(0, 0), Direction.N));
        RoverCommands commands = new RoverCommands();
        for (int i = 0; i < count; i++)
            commands.add(new MoveCommand(Navigator.create(), new InfinitePlateau()));
        mission.addCommands(commands);
        return mission;
    }

    private RoverMission missionAt(Coordinate coordinate) {
        RoverMission mission = new RoverMission();
        mission.addRover(new Rover(coordinate, Direction.N));
        mission.addCommands(new RoverCommands());
        return mission;
    }
}
