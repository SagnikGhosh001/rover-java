package com.tw.step.rover.roversystem;

import com.tw.step.rover.boundary.Boundary;
import com.tw.step.rover.commands.CommandCreator;
import com.tw.step.rover.commands.RoverCommand;
import com.tw.step.rover.commands.RoverCommands;
import com.tw.step.rover.position.Coordinate;
import com.tw.step.rover.position.Direction;
import com.tw.step.rover.position.Navigator;
import com.tw.step.rover.rover.Rover;

import java.util.*;

public class RoverMissionsParser {
    private final RoverMissionsScanner scanner;
    private final Navigator navigator;
    private final Boundary boundary;
    private final CommandCreator commandCreator;
    private final Map<String, Rover> roverMap = new LinkedHashMap<>();
    private final Map<String, RoverCommands> roverCommandsMap = new HashMap<>();

    public RoverMissionsParser(RoverMissionsScanner scanner, Navigator navigator, Boundary boundary, CommandCreator commandCreator) {
        this.scanner = scanner;
        this.navigator = navigator;
        this.boundary = boundary;
        this.commandCreator = commandCreator;
    }

    private Rover parseRover() {
        Coordinate coordinate = scanner.scanCoordinate();
        Direction heading = scanner.scanDirection();
        return new Rover(coordinate, heading);
    }

    public List<RoverMission> parse() {
        setUpRoversAndCommandsMap();
        return createRoverMissions();
    }

    private List<RoverMission> createRoverMissions() {
        List<RoverMission> roverMissions = new ArrayList<>();

        roverMap.forEach((roverId, rover) -> {
            RoverMission roverMission = new RoverMission();
            roverMission.addRover(rover);
            roverMission.addCommands(roverCommandsMap.get(roverId));
            roverMissions.add(roverMission);
        });

        return roverMissions;
    }

    private void setUpRoversAndCommandsMap() {
        while (scanner.peek() != null) {
            String rawId = scanner.consume();
            if (rawId.contains(":")) {
                setRoverCommandsMap(rawId, roverCommandsMap);
                continue;
            }

            setRoverMap(roverMap, rawId);
        }
    }

    private void setRoverMap(Map<String, Rover> roverMap, String rawId) {
        Rover rover = parseRover();
        roverMap.put(rawId, rover);
    }

    private void setRoverCommandsMap(String rawId, Map<String, RoverCommands> roverCommandsMap) {
        String actualId = rawId.substring(0, rawId.length() - 1);
        RoverCommands roverCommands = parseRoverCommands();
        roverCommandsMap.put(actualId, roverCommands);
    }

    private RoverCommands parseRoverCommands() {
        RoverCommands roverCommands = new RoverCommands();
        String instructions = scanner.consume();
        for (int i = 0; i < instructions.length(); i++) {
            RoverCommand roverCommand = commandCreator.create(instructions.charAt(i), navigator, boundary);
            roverCommands.add(roverCommand);
        }

        return roverCommands;
    }
}
