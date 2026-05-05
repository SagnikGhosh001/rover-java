package com.tw.step.rover.position;

import java.util.HashMap;
import java.util.Map;

public class Navigator {

    private static Navigator NAVIGATOR = null;
    private final HashMap<Direction, Direction> lefts;
    private final HashMap<Direction, Direction> rights;
    private final HashMap<Direction, Coordinate> offsets;

    private Navigator() {
        offsets = new HashMap<>();
        offsets.put(Direction.N, new Coordinate(0,1));
        offsets.put(Direction.E, new Coordinate(1,0));
        offsets.put(Direction.S, new Coordinate(0,-1));
        offsets.put(Direction.W, new Coordinate(-1,0));

        lefts = new HashMap<Direction, Direction>();
        lefts.put(Direction.N, Direction.W);
        lefts.put(Direction.S, Direction.E);
        lefts.put(Direction.W, Direction.S);
        lefts.put(Direction.E, Direction.N);

        rights = new HashMap<Direction, Direction>();
        for (Map.Entry<Direction, Direction> entry : lefts.entrySet()) {
            rights.put(entry.getValue(), entry.getKey());
        }
    }

    public Coordinate nextCoordinate(Coordinate coordinate, Direction heading) {
        return coordinate.add(offsets.get(heading));
    }

    public Direction leftOf(Direction direction) {
        return lefts.get(direction);
    }

    public Direction rightOf(Direction direction) {
        return rights.get(direction);
    }
    
    public static Navigator create() {
        if(NAVIGATOR != null) return NAVIGATOR;
        NAVIGATOR = new Navigator();
        return NAVIGATOR;
    }
}
