package com.tw.step.rover.roversystem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoverSystemTest {
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    void redirectStdout() {
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void restoreStdout() {
        System.setOut(originalOut);
    }

    @Test
    void shouldPrintFinalPositionForSingleRover() {
        new RoverSystem().execute("""
                5 5
                R1 1 2 N
                R1: FFRFF
                """);

        assertEquals("3 4 E Live" + System.lineSeparator(), output.toString());
    }

    @Test
    void shouldPrintFinalPositionsForMultipleRovers() {
        new RoverSystem().execute("""
                10 10
                R1 1 2 N
                R2 3 3 E
                R1: FFRFF
                R2: FFFF
                """);

        assertEquals(
                "3 4 E Live" + System.lineSeparator() +
                        "7 3 E Live" + System.lineSeparator(),
                output.toString()
        );
    }

    @Test
    void shouldMarkRoverDeadWhenItExceedsPlateau() {
        new RoverSystem().execute("""
                5 5
                R1 3 3 E
                R1: FFFF
                """);

        assertEquals("5 3 E Dead" + System.lineSeparator(), output.toString());
    }

    @Test
    void shouldHandleMixedLiveAndDeadRovers() {
        new RoverSystem().execute("""
                5 5
                R1 1 2 N
                R2 3 3 E
                R1: FFRFF
                R2: FFFF
                """);

        assertEquals(
                "3 4 E Live" + System.lineSeparator() +
                        "5 3 E Dead" + System.lineSeparator(),
                output.toString()
        );
    }

    @Test
    void shouldMarkBothRoversDeadOnCollision() {
        new RoverSystem().execute("""
                10 10
                R1 0 1 E
                R2 1 1 N
                R1: F
                R2: F
                """);

        assertEquals(
                "1 1 E Dead" + System.lineSeparator() +
                        "1 1 N Dead" + System.lineSeparator(),
                output.toString()
        );
    }

    @Test
    void shouldMarkBothRoversDeadOnCollisionMultiCommandForOneRover() {
        new RoverSystem().execute("""
                10 10
                R1 0 1 N
                R2 1 1 N
                R1: LRRF
                R2: F
                """);

        assertEquals(
                "1 1 E Dead" + System.lineSeparator() +
                        "1 1 N Dead" + System.lineSeparator(),
                output.toString()
        );
    }

    @Test
    void shouldMarkBothRoversDeadOnCollisionMultiCommandForOneRoverForSecondRover() {
        new RoverSystem().execute("""
                10 10
                R1 0 1 N
                R2 1 2 N
                R1: LRRF
                R2: FRFRFFRF
                """);

        assertEquals(
                "1 1 E Dead" + System.lineSeparator() +
                        "1 1 W Dead" + System.lineSeparator(),
                output.toString()
        );
    }
}
