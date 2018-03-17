package rp.assignments.team.warehouse.server.route.execution;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import rp.assignments.team.warehouse.server.route.execution.RouteExecution;
import rp.assignments.team.warehouse.server.route.planning.State;
import rp.assignments.team.warehouse.shared.Facing;
import rp.assignments.team.warehouse.shared.Instruction;

public class RouteExecutionTest {

    private final Facing defaultFacingDirection = Facing.EAST;

    @Test
    public void simpleHorizontalTest() {
        // arrange
        List<State> testRoute = new ArrayList<State>() {
            {
                add(new State(0, 0));
                add(new State(1, 0));
                add(new State(2, 0));
            }
        };

        // act
        List<Instruction> output = RouteExecution.convertCoordinatesToInstructions(defaultFacingDirection,
                testRoute);

        // assert
        List<Instruction> expected = new ArrayList<Instruction>() {
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
        List<State> testRoute = new ArrayList<State>() {
            {
                add(new State(0, 0));
                add(new State(0, 1));
                add(new State(0, 2));
                add(new State(0, 3));
                add(new State(0, 4));
                add(new State(0, 5));
            }
        };

        // act
        List<Instruction> output = RouteExecution.convertCoordinatesToInstructions(defaultFacingDirection,
                testRoute);

        // assert
        List<Instruction> expected = new ArrayList<Instruction>() {
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
        ArrayList<State> testRoute = new ArrayList<State>() {
            {
                add(new State(1, 0));
                add(new State(2, 0));
                add(new State(3, 0));
                add(new State(4, 0));
                add(new State(5, 0));
                add(new State(6, 0));
                add(new State(7, 0));
                add(new State(8, 0));
                add(new State(9, 0));
                add(new State(10, 0));
                add(new State(11, 0));
                add(new State(11, 1));
                add(new State(11, 2));
                add(new State(11, 3));
                add(new State(11, 4));
            }
        };

        // act
        List<Instruction> output = RouteExecution.convertCoordinatesToInstructions(defaultFacingDirection,
                testRoute);

        // assert
        List<Instruction> expected = new ArrayList<Instruction>() {
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
