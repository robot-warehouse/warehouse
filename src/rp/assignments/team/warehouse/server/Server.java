package rp.assignments.team.warehouse.server;

import rp.assignments.team.warehouse.server.controller.Controller;
import rp.assignments.team.warehouse.server.managementinterface.WMInterface;

public class Server extends Thread {

    private Warehouse warehouse;
    private boolean running;

    public static void main(String[] args) {
        // TODO Setup and start server from here
        Warehouse warehouse = new Warehouse();

        Controller controller = new Controller(warehouse);

        new WMInterface(controller);

        Thread server = new Server(warehouse);
        server.start();
    }

    public Server(Warehouse warehouse) {
        assert warehouse != null;

        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        while (this.warehouse.isRunning()) {
            // TODO

            Thread.yield();
        }
    }
}
