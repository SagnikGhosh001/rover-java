package com.tw.step.rover.roversystem;

import java.util.ArrayList;

public class RoverMissions extends ArrayList<RoverMission> {
    public void executeAll() {
        this.forEach((mission) -> {
            mission.execute();
            System.out.println(mission);
        });
    }
}
