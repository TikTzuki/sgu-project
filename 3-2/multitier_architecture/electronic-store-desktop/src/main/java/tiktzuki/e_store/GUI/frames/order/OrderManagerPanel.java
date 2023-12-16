package tiktzuki.e_store.GUI.frames.order;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import tiktzuki.e_store.BUS.OrderManagerController;
import tiktzuki.e_store.DTO.Customer;
import tiktzuki.e_store.DTO.Order;
import tiktzuki.e_store.DTO.OrderStatus;
import tiktzuki.e_store.GUI.MainFrame;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.customcomponents.ConfirmDialog;
import tiktzuki.e_store.GUI.customcomponents.MessageDialog;
import tiktzuki.e_store.GUI.customcomponents.TButton;
import tiktzuki.e_store.GUI.customcomponents.TComboBox;
import tiktzuki.e_store.GUI.customcomponents.TComboBoxItem;
import tiktzuki.e_store.GUI.customcomponents.TFormControl;
import tiktzuki.e_store.GUI.customcomponents.TTable;
import tiktzuki.e_store.services.Validators;

public class OrderManagerPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    OrderManagerController controller = new OrderManagerController();
    GroupLayout layout = new GroupLayout(this);
    // Search panel
    SearchOrderPanel pnlSearch = new SearchOrderPanel();

    //Control
    public static TFormControl<TComboBox<Customer>> cbxCustomer = new TFormControl<TComboBox<Customer>>(new TComboBox<TComboBoxItem<Customer>>(), "Khách hàng");
    public static TFormControl<JTextField> txtReciver = new TFormControl<JTextField>(new JTextField(), "Người nhận");
    public static TFormControl<JTextField> txtPhone = new TFormControl<JTextField>(new JTextField(), "Số điện thoại");
    public static TFormControl<JTextField> txtAddress = new TFormControl<JTextField>(new JTextField(), "Địa chỉ");
    public static TFormControl<TComboBox<OrderStatus>> cbxOrderStatus = new TFormControl<TComboBox<OrderStatus>>(new TComboBox<TComboBoxItem<OrderStatus>>(), "Trạng thái");
    public static TFormControl<JCheckBox> chbIsPaid = new TFormControl<JCheckBox>(new JCheckBox("Đã thanh toán", false), "Trạng thái thanh toán");
    static DefaultTableModel modelOrderDetail = new DefaultTableModel(new Object[]{"Tên", "Giá", "Số lượng", "Thành tiền"}, 0);
    TTable tblOrderDetail = new TTable(modelOrderDetail);
    JScrollPane orderDetailScroller = new JScrollPane(tblOrderDetail);
    TButton btnDeleteOrder = new TButton("Xóa");
    TButton btnModifyOrder = new TButton("Sửa");
    TButton btnPrintOrder = new TButton("In hóa đơn");
    JPanel pnlDetail = new JPanel();

    // Table
    JPanel pnlOrderTable = new JPanel();
    public static DefaultTableModel model = new DefaultTableModel(new Object[]{"#", "Khách hàng", "Địa chỉ", "Số điện thoại", "Ngày lập", "Đã thanh toán", "Tổng tiền"}, 0);
    TTable tblOrder = new TTable(model);
    JScrollPane scroller = new JScrollPane(tblOrder);

    public OrderManagerPanel() {
        initData();
        initComp();
    }

    private void initData() {
        cbxCustomer = new TFormControl<TComboBox<Customer>>(new TComboBox<TComboBoxItem<Customer>>(), "Khách hàng");
        controller.getAllCustomer().forEach(customer -> {
            cbxCustomer.getComponent().addItem(new TComboBoxItem<Customer>(customer, customer.getName()));
        });

        cbxOrderStatus = new TFormControl<TComboBox<OrderStatus>>(new TComboBox<TComboBoxItem<OrderStatus>>(), "Trạng thái");
        cbxOrderStatus.getComponent().removeAllItems();
        controller.getAllOrderStatus().forEach(status -> {
            cbxOrderStatus.getComponent().addItem(new TComboBoxItem<OrderStatus>(status, status.getName()));
        });
        loadNewOrderTable();
    }

    private void initComp() {
        //Detail
        cbxCustomer.getComponent().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer cus = cbxCustomer.getComponent().getSelectedObject();
                txtReciver.getComponent().setText(cus.getName());
                txtAddress.getComponent().setText(cus.getAddress());
                txtPhone.getComponent().setText(cus.getPhone());
            }
        });

        btnModifyOrder.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
//				OrderManagerMainPanel.dispatch(OrderManagerMainPanel.Action.SWITCH_MODIFY);
                if (validateForm()) {
                    commitSelectedOrderForm();
                    OrderManagerMainPanel.controller.modifyOrder(OrderManagerController.selectedOrder);
                }
            }
        });

        btnDeleteOrder.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (new ConfirmDialog("Xoá đơn hàng?").confirm() && OrderManagerController.selectedOrder != null) {
                    OrderManagerMainPanel.controller.deleteOrder(OrderManagerController.selectedOrder.getId());
                }
            }
        });

        btnPrintOrder.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (validateForm()) {
                    commitSelectedOrderForm();
                    OrderManagerMainPanel.controller.printOrder();
                    new MessageDialog("In hoa don thanh cong!");
                }
            }

            ;
        });

        GroupLayout layout1 = new GroupLayout(pnlDetail);
        layout1.setAutoCreateContainerGaps(true);
        layout1.setAutoCreateGaps(true);
        layout1.setHorizontalGroup(layout1.createSequentialGroup()
                .addGroup(layout1.createParallelGroup()
                        .addGroup(layout1.createSequentialGroup()
                                .addComponent(cbxCustomer.getPnl())
                                .addComponent(txtReciver.getPnl())
                                .addComponent(btnModifyOrder, 100, 100, 100))
                        .addGroup(layout1.createSequentialGroup()
                                .addComponent(txtPhone.getPnl())
                                .addComponent(txtAddress.getPnl())
                                .addComponent(btnDeleteOrder, 100, 100, 100)
                        )
                        .addGroup(layout1.createSequentialGroup()
                                .addComponent(cbxOrderStatus.getPnl())
                                .addComponent(chbIsPaid.getPnl())
                                .addComponent(btnPrintOrder, 100, 100, 100))
                )
                .addComponent(orderDetailScroller));
        layout1.setVerticalGroup(layout1.createParallelGroup()
                .addGroup(layout1.createSequentialGroup()
                        .addComponent(cbxCustomer.getPnl())
                        .addComponent(txtPhone.getPnl())
                        .addComponent(cbxOrderStatus.getPnl()))
                .addGroup(layout1.createSequentialGroup()
                        .addComponent(txtReciver.getPnl())
                        .addComponent(txtAddress.getPnl())
                        .addComponent(chbIsPaid.getPnl()))
                .addGroup(layout1.createSequentialGroup()
                        .addComponent(btnModifyOrder)
                        .addComponent(btnDeleteOrder)
                        .addComponent(btnPrintOrder))
                .addComponent(orderDetailScroller)
        );

        pnlDetail.setLayout(layout1);
        pnlDetail.setBackground(Resources.C_LIGHT);
        // Table
        tblOrder.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setSelectedOrder();
            }
        });
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(pnlDetail)
                .addComponent(pnlSearch)
                .addComponent(scroller));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(pnlDetail)
                .addComponent(pnlSearch, 120, 120, 120)
                .addComponent(scroller));
        this.setLayout(layout);
        this.setPreferredSize(Resources.MAIN_CONTENT);
    }

    private static void loadNewOrderTable() {
        model.setRowCount(0);
        OrderManagerController.orders.forEach(order -> {
            model.addRow(new Object[]{
                    order.getId(),
                    order.getReceiver(),
                    order.getAddress(),
                    order.getPhone(),
                    order.getCreateDate(),
                    order.getPaymentStatus().equals(1) ? "Đã thanh toán" : "Chưa thanh toán",
                    order.getAmount()});
        });
    }

    private void setSelectedOrder() {
        if (tblOrder.getSelectedRow() == -1) {
            return;
        }
        Integer id = Integer.valueOf(tblOrder.getValueAt(tblOrder.getSelectedRow(), 0).toString());
        OrderManagerController.selectedOrder = OrderManagerController.orders.stream().filter(item -> item.getId().equals(id)).findAny().get();
        Order order = OrderManagerController.selectedOrder;

        for (int i = 0; i < cbxCustomer.getComponent().getItemCount(); i++) {
            if (cbxCustomer.getComponent().getItemAt(i).getObject().getId().equals(
                    order.getCustomer().getId())) {
                cbxCustomer.getComponent().setSelectedIndex(i);
            }
        }
        txtReciver.getComponent().setText(order.getReceiver());
        txtPhone.getComponent().setText(order.getPhone());
        txtAddress.getComponent().setText(order.getAddress());
        for (int i = 0; i < cbxOrderStatus.getComponent().getItemCount(); i++) {
            if (cbxOrderStatus.getComponent().getItemAt(i).getObject().getId().equals(
                    order.getStatus().getId())) {
                cbxOrderStatus.getComponent().setSelectedIndex(i);
            }
        }
        chbIsPaid.getComponent().setSelected(order.getPaymentStatus().equals(1) ? true : false);
        modelOrderDetail.setRowCount(0);
        order.getOrderDetails().forEach(item -> {
            modelOrderDetail.addRow(new Object[]{item.getDevice().getName(), item.getPrice(), item.getQuantity(), item.getAmount()});
        });

    }

    public void commitSelectedOrderForm() {
        Order order = OrderManagerController.selectedOrder;
        order.setStaffId(MainFrame.CURRENT_USER.getId());
        order.setCustomer(cbxCustomer.getComponent().getSelectedObject());
        order.setCustomerId(order.getCustomer().getId());
        order.setReceiver(txtReciver.getComponent().getText());
        order.setPhone(txtPhone.getComponent().getText());
        order.setAddress(txtAddress.getComponent().getText());
        order.setStatus(cbxOrderStatus.getComponent().getSelectedObject());
        order.setStatusId(order.getStatus().getId());
        order.setPaymentStatus(chbIsPaid.getComponent().isSelected() ? 1 : 2);
    }

    private boolean validateForm() {
        String message = "";
        if (txtPhone.getComponent().getText().length() == 0) {
            message += "Khong duoc de trong so dien thoai. ";
        }
        if (OrderManagerController.selectedOrder.getOrderDetails().size() == 0) {
            message += "Hoa don phai co san pham. ";
        }
        if (message.length() > 0) {
            new MessageDialog(message);
        }
        return message.length() == 0 ? true : false;
    }

    public enum Action {
        UPDATE
    }

    public static void dispatch(Action action) {
        switch (action) {
            case UPDATE: {
                loadNewOrderTable();
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + action);
        }
    }

}
