package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;
import java.util.List;

import rp.assignments.team.warehouse.server.Location;

public class Data {

    private final static List<Location> obstacles = new ArrayList<Location>() {{
        add(new State(1, 1));
        add(new State(1, 2));
        add(new State(1, 3));
        add(new State(1, 4));
        add(new State(1, 5));
        add(new State(4, 1));
        add(new State(4, 2));
        add(new State(4, 3));
        add(new State(4, 4));
        add(new State(4, 5));
        add(new State(7, 1));
        add(new State(7, 2));
        add(new State(7, 3));
        add(new State(7, 4));
        add(new State(7, 5));
        add(new State(10, 1));
        add(new State(10, 2));
        add(new State(10, 3));
        add(new State(10, 4));
        add(new State(10, 5));
    }};

    private final static List<Location> singleRow = new ArrayList<Location>() {{
    	add(new State(0,0));
    	add(new State(0,1));
    	add(new State(0,2));
    	add(new State(0,3));
    	add(new State(0,4));
    	add(new State(0,5));
    	add(new State(0,6));
    	add(new State(1,0));
    	add(new State(11,0));
    	add(new State(11,1));
    	add(new State(11,2));
    	add(new State(11,3));
    	add(new State(11,4));
    	add(new State(11,5));
    	add(new State(11,6));
    	add(new State(10,0));
    }};

    public static List<Location> getRow(){
    	return singleRow;
    }

    public static List<Location> getObstacles() {
        return new ArrayList<Location>(obstacles);
    }
}
