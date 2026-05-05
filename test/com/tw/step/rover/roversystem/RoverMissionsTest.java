package com.tw.step.rover.roversystem;

import com.tw.step.rover.boundary.InfinitePlateau;
import com.tw.step.rover.boundary.Plateau;
import com.tw.step.rover.commands.MoveCommand;
import com.tw.step.rover.commands.RoverCommands;
import com.tw.step.rover.position.Coordinate;
import com.tw.step.rover.position.Direction;
import com.tw.step.rover.position.Navigator;
import com.tw.step.rover.rover.Rover;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoverMissionsTest {
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    void redirectStdout() {
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void restoreStdout() {
        System.setOut(originalOut);
    }

    @Test
    void shouldExecuteAllSingleMissionAndPrintResult() {
        RoverMission mission = new RoverMission();
        mission.addRover(new Rover(new Coordinate(0, 0), Direction.N));
        RoverCommands commands = new RoverCommands();
        commands.add(new MoveCommand(Navigator.create(), new InfinitePlateau()));
        mission.addCommands(commands);

        RoverMissions missions = new RoverMissions();
        missions.add(mission);
        missions.executeAll();

        assertEquals("0 1 N Live" + System.lineSeparator(), output.toString());
    }

    @Test
    void shouldExecuteAllAllMissionsInOrder() {
        Navigator navigator = Navigator.create();
        InfinitePlateau plateau = new InfinitePlateau();

        RoverMission first = new RoverMission();
        first.addRover(new Rover(new Coordinate(1, 2), Direction.N));
        RoverCommands firstCommands = new RoverCommands();
        firstCommands.add(new MoveCommand(navigator, plateau));
        first.addCommands(firstCommands);

        RoverMission second = new RoverMission();
        second.addRover(new Rover(new Coordinate(3, 3), Direction.E));
        RoverCommands secondCommands = new RoverCommands();
        secondCommands.add(new MoveCommand(navigator, plateau));
        second.addCommands(secondCommands);

        RoverMissions missions = new RoverMissions();
        missions.add(first);
        missions.add(second);
        missions.executeAll();

        assertEquals(
                "1 3 N Live" + System.lineSeparator() +
                        "4 3 E Live" + System.lineSeparator(),
                output.toString()
        );
    }

    @Test
    void shouldPrintDeadWhenRoverExceedsBoundary() {
        Plateau plateau = new Plateau(new Coordinate(0, 0), new Coordinate(1, 1));
        Navigator navigator = Navigator.create();

        RoverMission mission = new RoverMission();
        mission.addRover(new Rover(new Coordinate(1, 1), Direction.N));
        RoverCommands commands = new RoverCommands();
        commands.add(new MoveCommand(navigator, plateau));
        mission.addCommands(commands);

        RoverMissions missions = new RoverMissions();
        missions.add(mission);
        missions.executeAll();

        assertEquals("1 1 N Dead" + System.lineSeparator(), output.toString());
    }

    @Test
    void shouldProduceNoOutputForEmptyMissions() {
        new RoverMissions().executeAll();

        assertEquals("", output.toString());
    }
}
