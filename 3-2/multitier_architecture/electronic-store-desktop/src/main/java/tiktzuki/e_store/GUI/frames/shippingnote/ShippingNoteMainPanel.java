package tiktzuki.e_store.GUI.frames.shippingnote;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import tiktzuki.e_store.BUS.ShippingNoteController;

public class ShippingNoteMainPanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    ShippingNoteController controller = new ShippingNoteController();

    static ShippingNoteManagerPanel pnlManager = new ShippingNoteManagerPanel();
    static ShippingNoteCreatePanel pnlCreate;

    static JPanel pnlBackground = new JPanel();

    public ShippingNoteMainPanel() {
        pnlBackground.add(pnlManager);
        this.add(pnlBackground);
    }

    private static void switchToCreatePanel() {
        pnlCreate = new ShippingNoteCreatePanel();
        pnlBackground.removeAll();
        pnlBackground.add(pnlCreate);
        pnlBackground.revalidate();
        pnlBackground.repaint();
    }

    private static void switchToManagerPanel() {
        pnlManager = new ShippingNoteManagerPanel();
        pnlBackground.removeAll();
        pnlBackground.add(pnlManager);
        pnlBackground.revalidate();
        pnlBackground.repaint();
    }

    public enum Action {
        UPDATE,
        SWITCH_TO_CREATE_PANEL,
        SWITCH_TO_MAIN_PANEL
    }

    public static void dispatch(Action action) {
        switch (action) {
            case UPDATE: {

                break;
            }
            case SWITCH_TO_CREATE_PANEL: {
                switchToCreatePanel();
                break;
            }
            case SWITCH_TO_MAIN_PANEL: {
                switchToManagerPanel();
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + action);
        }
    }
}
