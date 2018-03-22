package rp.assignments.team.warehouse.server.gui;

import java.util.LinkedList;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import rp.assignments.team.warehouse.server.Robot;

public class RobotTableModel extends AbstractTableModel implements TableModel {

    private List<Robot> robots = new LinkedList<>();

    private static Object getColumnValue(Robot robot, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return robot.getName();
            case 1:
                return robot.getCurrentLocation();
            case 2:
                return robot.getCurrentFacingDirection().toString();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Robot Name";
            case 1:
                return "Current Location";
            case 2:
                return "Current Facing";
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

    @Override
    public void addTableModelListener(TableModelListener l) {}

    @Override
    public void removeTableModelListener(TableModelListener l) {}

    @Override
    public int getRowCount() {
        return this.robots.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return getColumnValue(this.robots.get(rowIndex), columnIndex);
    }

    public void addRow(Robot robot) {
        this.robots.add(robot);
        int index = this.robots.indexOf(robot);
        this.fireTableRowsInserted(index, index);
    }

    public Robot getRow(int rowIndex) {
        return this.robots.get(rowIndex);
    }

    public void removeRow(Robot robot) {
        int index = this.robots.indexOf(robot);
        this.robots.remove(robot);
        this.fireTableRowsDeleted(index, index);
    }

}
