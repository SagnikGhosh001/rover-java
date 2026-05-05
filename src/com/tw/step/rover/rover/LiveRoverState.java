package com.tw.step.rover.rover;

import com.tw.step.rover.boundary.Boundary;
import com.tw.step.rover.position.Coordinate;
import com.tw.step.rover.position.Navigator;

import java.util.Objects;

public class LiveRoverState implements RoverState {
    private final Rover rover;

    public LiveRoverState(Rover rover) {
        this.rover = rover;
    }

    @Override
    public RoverState turnLeft(Navigator navigator, Boundary boundary) {
        this.rover.turnLeftInternal(navigator);
        return this;
    }

    @Override
    public RoverState turnRight(Navigator navigator, Boundary boundary) {
        this.rover.turnRightInternal(navigator);
        return this;
    }

    @Override
    public RoverState move(Navigator navigator, Boundary boundary) {
        Coordinate nextCoordinate = rover.getNextCoordinateInternal(navigator);

        if (!boundary.isWithin(nextCoordinate)) {
            return new DeadRoverState(rover);
        }

        rover.setCoordinate(nextCoordinate);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LiveRoverState that = (LiveRoverState) o;
        return Objects.equals(rover, that.rover);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rover);
    }
}
