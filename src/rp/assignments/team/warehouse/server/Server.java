package rp.assignments.team.warehouse.server;

import rp.assignments.team.warehouse.server.controller.Controller;
import rp.assignments.team.warehouse.server.managementinterface.WMInterface;

public class Server {
    public static void main(String[] args) {
        // TODO Setup and start server from here
        Warehouse warehouse = new Warehouse();

        Controller controller = new Controller(warehouse);

        new WMInterface(controller);

//        (new JobAssigner(warehouse)).start();
    }
}
