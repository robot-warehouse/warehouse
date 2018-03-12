package rp.assignments.team.warehouse.server;

public class Server {
    public static void main(String[] args) {
        // TODO Setup and start server from here
        Warehouse warehouse = new Warehouse();

        Controller controller = new Controller(warehouse);

        new ManagementInterface(controller);
    }
}
