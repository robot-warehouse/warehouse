package rp.assignments.team.warehouse.server;

import rp.assignments.team.warehouse.server.gui.ManagementInterface;

public class Server {
    public static void main(String[] args) {
        // TODO Setup and start server from here
        Warehouse warehouse = new Warehouse();

        Controller controller = new Controller(warehouse);

        ManagementInterface managementInterface = new ManagementInterface(controller);

        controller.setManagementInterface(managementInterface);

        warehouse.setController(controller);
    }
}
