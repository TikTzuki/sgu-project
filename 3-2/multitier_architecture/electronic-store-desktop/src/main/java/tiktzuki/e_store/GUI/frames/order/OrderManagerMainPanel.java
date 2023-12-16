package tiktzuki.e_store.GUI.frames.order;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.protocol.x.MessageConstants;
import com.sun.nio.sctp.SctpStandardSocketOptions.InitMaxStreams;

import tiktzuki.e_store.BUS.OrderCreateController;
import tiktzuki.e_store.BUS.OrderManagerController;
import tiktzuki.e_store.DTO.Order;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.customcomponents.ConfirmDialog;
import tiktzuki.e_store.GUI.customcomponents.MessageDialog;
import tiktzuki.e_store.GUI.customcomponents.TButton;
import tiktzuki.e_store.GUI.customcomponents.TTable;

public class OrderManagerMainPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Logger LOGGER = Logger.getLogger(getClass().getSimpleName());
    static OrderManagerController controller = new OrderManagerController();

    //	static OrderManagerPanel pnlManager = new OrderManagerPanel();
//	static OrderModifyPanel pnlModify = new OrderModifyPanel();
    static JPanel mainContent = new JPanel();

    public OrderManagerMainPanel() {
        mainContent.removeAll();
        mainContent.add(new OrderManagerPanel());
        this.setBackground(Resources.C_INFO);
        this.setPreferredSize(Resources.MAIN_CONTENT);
        this.add(mainContent);
    }

    private static void initManagerPanel() {
        mainContent.removeAll();
        mainContent.add(new OrderManagerPanel());
        mainContent.revalidate();
        mainContent.repaint();
    }

    private static void initModifyPanel() {
        if (OrderManagerController.selectedOrder == null) {
            new MessageDialog("Không có đơn hàng được chọn");
            return;
        }
        mainContent.removeAll();
        mainContent.add(new OrderModifyPanel());
        mainContent.revalidate();
        mainContent.repaint();
    }

    public enum Action {
        SWITCH_MANAGER,
        SWITCH_MODIFY
    }

    public static void dispatch(Action action) {
        switch (action) {
            case SWITCH_MANAGER: {
                initManagerPanel();
                break;
            }
            case SWITCH_MODIFY: {
                initModifyPanel();
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + action);
        }
    }

}
