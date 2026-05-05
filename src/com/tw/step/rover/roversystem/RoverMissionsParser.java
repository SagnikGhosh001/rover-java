package com.tw.step.rover.roversystem;

import com.tw.step.rover.boundary.Boundary;
import com.tw.step.rover.commands.CommandCreator;
import com.tw.step.rover.commands.RoverCommands;
import com.tw.step.rover.position.Coordinate;
import com.tw.step.rover.position.Direction;
import com.tw.step.rover.position.Navigator;
import com.tw.step.rover.rover.Rover;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class RoverMissionsParser {
    private final RoverMissionsScanner scanner;
    private final Navigator navigator;
    private final Boundary boundary;
    private final CommandCreator commandCreator;
    private final Map<String, RoverMission> missionMap = new LinkedHashMap<>();

    public RoverMissionsParser(RoverMissionsScanner scanner, Navigator navigator, Boundary boundary, CommandCreator commandCreator) {
        this.scanner = scanner;
        this.navigator = navigator;
        this.boundary = boundary;
        this.commandCreator = commandCreator;
    }

    private Rover parseRover(String id) {
        Coordinate coordinate = scanner.scanCoordinate();
        Direction heading = scanner.scanDirection();
        return new Rover(id, coordinate, heading);
    }

    public RoverMissions parse() {
        while (scanner.peek() != null) {
            String rawId = scanner.consume();
            parseLine(rawId);
        }

        RoverMissions missions = new RoverMissions();
        missions.addAll(missionMap.values());
        return missions;
    }


    private void parseLine(String rawId) {
        if (rawId.contains(":")) {
            parseCommandLine(rawId);
            return;
        }

        parseRoverLine(rawId);
    }

    private void parseCommandLine(String rawId) {
        String actualId = rawId.substring(0, rawId.length() - 1);
        missionMap.get(actualId).addCommands(parseRoverCommands());
    }

    private void parseRoverLine(String rawId) {
        RoverMission roverMission = new RoverMission();
        roverMission.addRover(parseRover(rawId));
        missionMap.put(rawId, roverMission);
    }

    private RoverCommands parseRoverCommands() {
        RoverCommands roverCommands = new RoverCommands();
        String instructions = scanner.consume();

        IntStream.range(0, instructions.length())
                .mapToObj(i -> commandCreator.create(instructions.charAt(i), navigator, boundary))
                .forEach(roverCommands::add);

        return roverCommands;
    }
}
