package com.tw.step.rover.roversystem;

import java.util.ArrayList;

public class RoverMissions extends ArrayList<RoverMission> {
    public void executeAll() {
        this.forEach(mission -> {
            while (mission.hasMoreCommand()) {
                mission.executeNext();
                handleCollision(mission);
            }
        });

        this.forEach(System.out::println);
    }

    private void handleCollision(RoverMission other) {
        this.forEach((mission) -> {
            if (!other.equals(mission)) mission.checkCollision(other);
        });
    }
}
