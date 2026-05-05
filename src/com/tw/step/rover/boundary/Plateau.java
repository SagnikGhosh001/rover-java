package com.tw.step.rover.boundary;

import com.tw.step.rover.position.Coordinate;

public class Plateau implements Boundary {
    private final Coordinate bottomLeft;
    private final Coordinate topRight;

    public Plateau(Coordinate bottomLeft, Coordinate topRight) {
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
    }

    @Override
    public boolean isWithin(Coordinate coord) {
        return coord.isWithin(bottomLeft, topRight);
    }
}
