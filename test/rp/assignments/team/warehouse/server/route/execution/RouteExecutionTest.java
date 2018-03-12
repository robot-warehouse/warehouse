package rp.assignments.team.warehouse.server.route.execution;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import rp.assignments.team.warehouse.server.Facing;
import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.route.execution.Instruction;
import rp.assignments.team.warehouse.server.route.execution.RouteExecution;

public class RouteExecutionTest {

    private final Facing defaultFacingDirection = Facing.East;

    @Test
    public void simpleHorizontalTest() {
        // arrange
        ArrayList<Location> testLocations = new ArrayList<Location>() {
            {
                add(new Location(0, 0));
                add(new Location(1, 0));
                add(new Location(2, 0));
            }
        };

        // act
        ArrayList<Instruction> output = RouteExecution.convertCoordinatesToInstructions(defaultFacingDirection,
                testLocations);

        // assert
        ArrayList<Instruction> expected = new ArrayList<Instruction>() {
            {
                add(Instruction.FORWARDS);
                add(Instruction.FORWARDS);
                add(Instruction.STOP);
            }
        };

        assertEquals(expected, output);
    }

    @Test
    public void simpleVerticalTest() {
        // arrange
        ArrayList<Location> testLocations = new ArrayList<Location>() {
            {
                add(new Location(0, 0));
                add(new Location(0, 1));
                add(new Location(0, 2));
                add(new Location(0, 3));
                add(new Location(0, 4));
                add(new Location(0, 5));
            }
        };

        // act
        ArrayList<Instruction> output = RouteExecution.convertCoordinatesToInstructions(defaultFacingDirection,
                testLocations);

        // assert
        ArrayList<Instruction> expected = new ArrayList<Instruction>() {
            {
                add(Instruction.LEFT);
                add(Instruction.FORWARDS);
                add(Instruction.FORWARDS);
                add(Instruction.FORWARDS);
                add(Instruction.FORWARDS);
                add(Instruction.STOP);
            }
        };

        assertEquals(expected, output);
    }

    @Test
    public void longerPathTest() {
        // arrange
        ArrayList<Location> testLocations = new ArrayList<Location>() {
            {
                add(new Location(1, 0));
                add(new Location(2, 0));
                add(new Location(3, 0));
                add(new Location(4, 0));
                add(new Location(5, 0));
                add(new Location(6, 0));
                add(new Location(7, 0));
                add(new Location(8, 0));
                add(new Location(9, 0));
                add(new Location(10, 0));
                add(new Location(11, 0));
                add(new Location(11, 1));
                add(new Location(11, 2));
                add(new Location(11, 3));
                add(new Location(11, 4));
            }
        };

        // act
        ArrayList<Instruction> output = RouteExecution.convertCoordinatesToInstructions(defaultFacingDirection,
                testLocations);

        // assert
        ArrayList<Instruction> expected = new ArrayList<Instruction>() {
            {
                add(Instruction.FORWARDS);
                add(Instruction.FORWARDS);
                add(Instruction.FORWARDS);
                add(Instruction.FORWARDS);
                add(Instruction.FORWARDS);
                add(Instruction.FORWARDS);
                add(Instruction.FORWARDS);
                add(Instruction.FORWARDS);
                add(Instruction.FORWARDS);
                add(Instruction.FORWARDS);
                add(Instruction.LEFT);
                add(Instruction.FORWARDS);
                add(Instruction.FORWARDS);
                add(Instruction.FORWARDS);
                add(Instruction.STOP);
            }
        };
        assertEquals(expected, output);
    }

    // TODO add facing tests
}