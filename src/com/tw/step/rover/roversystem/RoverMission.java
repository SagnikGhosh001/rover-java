package com.tw.step.rover.roversystem;

import com.tw.step.rover.commands.RoverCommands;
import com.tw.step.rover.rover.Rover;

public class RoverMission {
    private Rover rover;
    private RoverCommands roverCommands;
    private int commandIndex = 0;

    public void addRover(Rover rover) {
        this.rover = rover;
    }

    public void addCommands(RoverCommands roverCommands) {
        this.roverCommands = roverCommands;
    }

    public void execute() {
        this.roverCommands.execute(this.rover);
    }

    public boolean hasMoreCommand() {
        return commandIndex < roverCommands.size();
    }

    public void executeNext() {
        this.roverCommands.get(commandIndex).execute(this.rover);
        commandIndex++;
    }

    @Override
    public String toString() {
        return rover.toString();
    }

    public void checkCollision(RoverMission other) {
        rover.checkCollision(other.rover);
    }
}
