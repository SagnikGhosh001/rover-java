package com.tw.step.rover.roversystem;

import com.tw.step.rover.position.Coordinate;
import com.tw.step.rover.position.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RoverMissionScannerTest {
    @Test
    void shouldScanValuesFromInput() {
        RoverMissionsScanner scanner = RoverMissionsScanner.from("1 2 N");
        RoverMissionsScanner coordinateScanner = RoverMissionsScanner.from("2 0");
        RoverMissionsScanner directionScanner = RoverMissionsScanner.from("N");
        RoverMissionsScanner consumeScanner = RoverMissionsScanner.from("X");

        assertEquals("1", scanner.peek());
        assertEquals(1, scanner.scanNumber());
        assertEquals(new Coordinate(2, 0), coordinateScanner.scanCoordinate());
        assertEquals(Direction.N, directionScanner.scanDirection());
        assertEquals("X", consumeScanner.consume());
        assertNull(consumeScanner.consume());
    }
}
