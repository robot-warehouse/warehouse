package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;

public class Data {

    public static final ArrayList obstacles = new ArrayList<State>() {
        {
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
        }
    };
}
