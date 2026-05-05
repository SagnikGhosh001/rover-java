package com.tw.step.rover.rover;

import com.tw.step.rover.boundary.Boundary;
import com.tw.step.rover.position.Coordinate;
import com.tw.step.rover.position.Direction;
import com.tw.step.rover.position.Navigator;

import java.util.Objects;

public class Rover {
    private final String id;
    private RoverState roverState;
    private Coordinate coordinate;
    private Direction heading;

    public Rover(String id, Coordinate coordinate, Direction heading) {
        this.id = id;
        this.coordinate = coordinate;
        this.heading = heading;
        this.roverState = new LiveRoverState(this);
    }

    public Rover(Coordinate coordinate, Direction heading) {
        this("", coordinate, heading);
    }

    public void turnLeft(Navigator navigator, Boundary boundary) {
        this.roverState = this.roverState.turnLeft(navigator, boundary);
    }

    public void turnRight(Navigator navigator, Boundary boundary) {
        this.roverState = this.roverState.turnRight(navigator, boundary);
    }

    public void move(Navigator navigator, Boundary boundary) {
        this.roverState = this.roverState.move(navigator, boundary);
    }

    void turnLeftInternal(Navigator navigator) {
        this.heading = navigator.leftOf(this.heading);
    }

    void turnRightInternal(Navigator navigator) {
        this.heading = navigator.rightOf(this.heading);
    }

    Coordinate getNextCoordinateInternal(Navigator navigator) {
        return navigator.nextCoordinate(this.coordinate, this.heading);
    }

    void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    boolean isWithin(Boundary boundary) {
        return boundary.isWithin(this.coordinate);
    }

    @Override
    public String toString() {
        String state = roverState.equals(new LiveRoverState(this)) ? "Live" : "Dead";
        return coordinate.toString() + " " + heading.toString() + " " + state;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rover rover = (Rover) o;
        return Objects.equals(id, rover.id) && Objects.equals(roverState, rover.roverState) && Objects.equals(coordinate, rover.coordinate) && heading == rover.heading;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roverState, coordinate, heading);
    }
}
