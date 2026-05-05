package com.tw.step.rover;

import com.tw.step.rover.roversystem.RoverSystem;

public class App {
    static void main() {
        String text = """
                5 5
                R1 1 2 N
                R1: FFRFF
                """;

        RoverSystem roverSystem = new RoverSystem();
        roverSystem.execute(text);

    }
}
