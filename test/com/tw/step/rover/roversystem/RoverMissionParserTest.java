package com.tw.step.rover.roversystem;

import com.tw.step.rover.boundary.Boundary;
import com.tw.step.rover.boundary.InfinitePlateau;
import com.tw.step.rover.boundary.Plateau;
import com.tw.step.rover.commands.CommandCreator;
import com.tw.step.rover.position.Coordinate;
import com.tw.step.rover.position.Navigator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoverMissionParserTest {
    @Test
    void shouldParseAndExecuteRoverSystem() {
        RoverMissionsScanner scanner = RoverMissionsScanner.from("""
                R1 1 2 N
                R2 3 3 E
                R1: FFRFF
                R2: FFFF
                """);
        RoverMissionsParser parser = new RoverMissionsParser(scanner, Navigator.create(), new InfinitePlateau(), new CommandCreator());

        List<RoverMission> roverMission = parser.parse();
        roverMission.forEach(RoverMission::execute);

        assertEquals("[3 4 E Live, 7 3 E Live]", roverMission.toString());
    }

    @Test
    void shouldParseAndExecuteRoverSystemForFinitePlateau() {
        RoverMissionsScanner scanner = RoverMissionsScanner.from("""
                5 5
                R1 1 2 N
                R2 3 3 E
                R1: FFRFF
                R2: FFFF
                """);
        Coordinate bottomLeft = new Coordinate(0, 0);
        Coordinate topRight = scanner.scanCoordinate();
        Boundary boundary = new Plateau(bottomLeft, topRight);
        RoverMissionsParser parser = new RoverMissionsParser(scanner, Navigator.create(), boundary, new CommandCreator());

        List<RoverMission> roverMission = parser.parse();
        roverMission.forEach(RoverMission::execute);

        assertEquals("[3 4 E Live, 5 3 E Dead]", roverMission.toString());
    }
}
