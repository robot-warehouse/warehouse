package rp.assignments.team.warehouse.server.gui;

import java.util.LinkedList;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import rp.assignments.team.warehouse.server.job.Job;

public class JobTableModel extends AbstractTableModel implements TableModel {

    private List<Job> jobs = new LinkedList<>();

    private static Object getColumnValue(Job job, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return job.getId() + (job.isPredictedCancelled() ? " (C)" : "");
            case 1:
                return job.getPickCount();
            case 2:
                return job.getReward();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Job ID";
            case 1:
                return "Pick Count";
            case 2:
                return "Reward";
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return Integer.class;
            case 2:
                return Float.class;
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
        return this.jobs.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return getColumnValue(this.jobs.get(rowIndex), columnIndex);
    }

    public void addRow(Job job) {
        this.jobs.add(job);
        int index = this.jobs.indexOf(job);
        this.fireTableRowsInserted(index, index);
    }

    public Job getRow(int rowIndex) {
        return this.jobs.get(rowIndex);
    }

    public void removeRow(Job job) {
        int index = this.jobs.indexOf(job);
        this.jobs.remove(job);
        this.fireTableRowsDeleted(index, index);
    }

}
