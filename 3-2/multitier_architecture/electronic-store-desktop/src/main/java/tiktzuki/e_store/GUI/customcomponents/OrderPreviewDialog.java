package tiktzuki.e_store.GUI.customcomponents;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import tiktzuki.e_store.BUS.OrderCreateController;
import tiktzuki.e_store.DTO.OrderDetail;
import tiktzuki.e_store.GUI.MainFrame;
import tiktzuki.e_store.GUI.Resources;

public class OrderPreviewDialog {
    OrderCreateController orderCreateController = new OrderCreateController();
    public static JDialog dialog = new JDialog();
    JPanel pnlBackground = new JPanel();

    JPanel pnlControl = new JPanel();
    static JLabel lblSelectedItem = new JLabel();
    TButton btnRemove = new TButton(Resources.TRASH_BIN_ICON);
    TButton btnClear = new TButton(Resources.EMPTY_ICON);

    static DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Tên", "Giá", "Số lượng", "Thành tiền"},
            0);
    TTable table = new TTable(model);
    JScrollPane scroller = new JScrollPane(table);

    JPanel pnlSummary = new JPanel();
    JLabel lblProvisional = new JLabel("Tạm tính");
    static JLabel lblProvisionalValue = new TCurrencyLabel("0");
    JLabel lblCurrentCombos = new JLabel("Giảm giá");
    static JLabel lblCurrentCombosValue = new TCurrencyLabel("0");
    JLabel lblAmountPrice = new JLabel("Tổng");
    static JLabel lblAmountValue = new TCurrencyLabel("0");

    JPanel pnlNavigate = new JPanel();
    TButton btnConfirm = new TButton("Tạo đơn");
    TButton btnClose = new TButton("Đóng");

    public static List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();

    public OrderPreviewDialog() {
        orderDetails = new ArrayList<OrderDetail>();
        dialog.setTitle("Báo giá");
        dialog.setModal(true);
        //Control
        btnClear.setPreferredSize(Resources.ICON_BUTTON_SIZE);
        btnClear.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                orderCreateController.clearOrderItem();
            }
        });
        btnRemove.setPreferredSize(Resources.ICON_BUTTON_SIZE);
        btnRemove.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (table.getSelectedRow() > -1) {
                    orderCreateController.removeOrderItem(table.getValueAt(table.getSelectedRow(), 0).toString());
                }
            }
        });
        lblSelectedItem.setPreferredSize(new Dimension(300, 40));
        pnlControl.add(btnClear);
        pnlControl.add(btnRemove);
        pnlControl.add(lblSelectedItem);
        // Table
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                String deviceName = table.getValueAt(row, 0).toString();
                lblSelectedItem.setText(deviceName);
            }
        });
        scroller.setPreferredSize(new Dimension(400, 350));

        // Summary
        pnlSummary.setLayout(new GridLayout(3, 2));
        pnlSummary.add(lblProvisional);
        pnlSummary.add(lblProvisionalValue);
        pnlSummary.add(lblCurrentCombos);
        pnlSummary.add(lblCurrentCombosValue);
        pnlSummary.add(lblAmountPrice);
        pnlSummary.add(lblAmountValue);
        pnlSummary.setBackground(Resources.C_LIGHT);
        pnlSummary.setPreferredSize(new Dimension(300, 60));

        //Control
        btnClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dialog.setVisible(false);
            }
        });
        btnConfirm.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dialog.setVisible(false);
                MainFrame.dispatch(MainFrame.Action.CREATE_ORDER);
            }
        });
        pnlNavigate.add(btnClose);
        pnlNavigate.add(btnConfirm);

        pnlBackground.setPreferredSize(new Dimension(460, 520));
        pnlBackground.add(pnlControl);
        pnlBackground.add(scroller);
        pnlBackground.add(pnlSummary);
        pnlBackground.add(pnlNavigate);
        dialog.add(pnlBackground);

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(false);
    }

    public enum Action {
        DISPLAY,
        HIDE,
        CONFIRM,
        UPDATE
    }

    public static void dispatch(Action action) {
        switch (action) {
            case DISPLAY: {
                dialog.setVisible(dialog.isVisible() ? false : true);
                break;
            }
            case HIDE: {
                dialog.setVisible(false);
            }
            case CONFIRM: {

                break;
            }
            case UPDATE: {
                lblSelectedItem.setText("");
                model.setRowCount(0);
                OrderCreateController.orderPreview.getOrderDetails().forEach(item -> {
                    model.addRow(new Object[]{item.getDevice().getName(), item.getPrice(), item.getQuantity(), item.getAmount()});
                });
                lblProvisionalValue.setText(String.valueOf(OrderCreateController.provisional));
                lblCurrentCombosValue.setText(String.valueOf(OrderCreateController.totalComboDiscount));
                lblAmountValue.setText(String.valueOf(OrderCreateController.orderPreview.getAmount()));
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + action);
        }
    }

    public static void main(String[] args) {
        new OrderPreviewDialog();
    }
}
