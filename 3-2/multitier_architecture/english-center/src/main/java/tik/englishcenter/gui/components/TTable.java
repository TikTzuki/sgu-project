package tik.englishcenter.gui.components;

import tik.englishcenter.gui.Resources;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class TTable extends JTable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TTable(DefaultTableModel dataModel) {
        super(dataModel);
        this.getTableHeader().setBackground(Resources.C_SECONDARY);
        this.getTableHeader().setForeground(Resources.C_LIGHT);
        this.setBackground(Resources.C_LIGHT);
        this.setForeground(Resources.C_DARK);
        this.setSelectionBackground(Resources.C_SECONDARY);
    }

    public TTable() {
        super();
        this.getTableHeader().setBackground(Resources.C_SECONDARY);
        this.getTableHeader().setForeground(Resources.C_LIGHT);
        this.setBackground(Resources.C_LIGHT);
        this.setForeground(Resources.C_DARK);
        this.setSelectionBackground(Resources.C_SECONDARY);
    }

    @Override
    public DefaultTableModel getModel() {
        return (DefaultTableModel) super.getModel();
    }
}
