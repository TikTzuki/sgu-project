package tiktzuki.e_store.GUI.customcomponents;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import tiktzuki.e_store.GUI.Resources;

public class TTable extends JTable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TTable(TableModel dataModel) {
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
}
