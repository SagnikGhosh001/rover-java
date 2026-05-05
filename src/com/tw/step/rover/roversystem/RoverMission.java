package com.tw.step.rover.roversystem;

import com.tw.step.rover.commands.RoverCommands;
import com.tw.step.rover.rover.Rover;

public class RoverMission {
    private Rover rover;
    private RoverCommands roverCommands;

    public void addRover(Rover rover) {
        this.rover = rover;
    }

    public void addCommands(RoverCommands roverCommands) {
        this.roverCommands = roverCommands;
    }

    public void execute() {
        this.roverCommands.execute(this.rover);
    }

    @Override
    public String toString() {
        return rover.toString();
    }
}
