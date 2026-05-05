package com.tw.step.rover;

import com.tw.step.rover.boundary.Boundary;
import com.tw.step.rover.boundary.Plateau;
import com.tw.step.rover.commands.CommandCreator;
import com.tw.step.rover.position.Coordinate;
import com.tw.step.rover.position.Navigator;
import com.tw.step.rover.roversystem.RoverSystem;
import com.tw.step.rover.roversystem.RoverSystemParser;
import com.tw.step.rover.roversystem.RoverSystemScanner;

public class App {
    static void main() {
        String text = """
                5 5
                1 5 N
                LFFLFF
                """;

        RoverSystemScanner scanner = RoverSystemScanner.from(text);
        Navigator navigator = Navigator.create();
        Coordinate bottomLeft = new Coordinate(0, 0);
        Coordinate topRight = scanner.scanCoordinate();
        Boundary boundary = new Plateau(bottomLeft, topRight);
        CommandCreator commandCreator = new CommandCreator();
        RoverSystemParser roverSystemParser = new RoverSystemParser(scanner, navigator, boundary, commandCreator);
        RoverSystem system = roverSystemParser.parse();
        system.execute();
        System.out.println(system);
    }
}
