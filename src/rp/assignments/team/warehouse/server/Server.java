package rp.assignments.team.warehouse.server;

import rp.assignments.team.warehouse.server.gui.ManagementInterface;

public class Server {

    /**
     * The start point of the application.
     *
     * @param args Arguments passed to the program.
     */
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();

        Controller controller = new Controller(warehouse);

        ManagementInterface managementInterface = new ManagementInterface(controller);

        controller.setManagementInterface(managementInterface);

        warehouse.setController(controller);
    }
}
