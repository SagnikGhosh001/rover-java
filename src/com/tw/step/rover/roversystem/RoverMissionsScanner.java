package com.tw.step.rover.roversystem;

import com.tw.step.rover.position.Coordinate;
import com.tw.step.rover.position.Direction;

public class RoverMissionsScanner {
    private final String[] tokens;
    private int currentIndex;

    private RoverMissionsScanner(String[] tokens) {
        this.tokens = tokens;
        this.currentIndex = 0;
    }

    public String peek() {
        if (this.isDone()) return null;
        return this.tokens[this.currentIndex];
    }

    private boolean isDone() {
        return this.currentIndex >= this.tokens.length;
    }

    public String consume() {
        String token = this.peek();
        if (this.currentIndex < this.tokens.length) {
            this.currentIndex++;
        }
        return token;
    }

    public static RoverMissionsScanner from(String input) {
        String[] tokens = input.split("[\n\t ]+");
        return new RoverMissionsScanner(tokens);
    }

    public int scanNumber() {
        return Integer.parseInt(consume());
    }

    public Coordinate scanCoordinate() {
        int x = this.scanNumber();
        int y = this.scanNumber();
        return new Coordinate(x, y);
    }

    public Direction scanDirection() {
        return Direction.valueOf(this.consume());
    }
}
