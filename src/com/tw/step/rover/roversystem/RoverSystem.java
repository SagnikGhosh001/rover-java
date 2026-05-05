package com.tw.step.rover.roversystem;

import com.tw.step.rover.boundary.Boundary;
import com.tw.step.rover.boundary.Plateau;
import com.tw.step.rover.commands.CommandCreator;
import com.tw.step.rover.position.Coordinate;
import com.tw.step.rover.position.Navigator;

import java.util.List;

public class RoverSystem {
    public void execute(String text) {
        RoverMissionsScanner scanner = RoverMissionsScanner.from(text);
        Navigator navigator = Navigator.create();
        Boundary boundary = getBoundary(scanner);
        CommandCreator commandCreator = new CommandCreator();
        RoverMissionsParser roverMissionsParser = new RoverMissionsParser(scanner, navigator, boundary, commandCreator);
        List<RoverMission> missions = roverMissionsParser.parse();

        missions.forEach((mission) -> {
            mission.execute();
            System.out.println(mission);
        });
    }


    private static Boundary getBoundary(RoverMissionsScanner scanner) {
        Coordinate bottomLeft = new Coordinate(0, 0);
        Coordinate topRight = scanner.scanCoordinate();
        return new Plateau(bottomLeft, topRight);

    }
}
