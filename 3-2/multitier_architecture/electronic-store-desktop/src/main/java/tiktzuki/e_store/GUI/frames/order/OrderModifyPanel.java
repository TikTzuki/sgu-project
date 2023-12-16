package tiktzuki.e_store.GUI.frames.order;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import tiktzuki.e_store.BUS.OrderCreateController;
import tiktzuki.e_store.BUS.OrderManagerController;
import tiktzuki.e_store.DTO.Customer;
import tiktzuki.e_store.DTO.OrderStatus;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.customcomponents.MessageDialog;
import tiktzuki.e_store.GUI.customcomponents.TBorderPanel;
import tiktzuki.e_store.GUI.customcomponents.TButton;
import tiktzuki.e_store.GUI.customcomponents.TComboBox;
import tiktzuki.e_store.GUI.customcomponents.TComboBoxItem;
import tiktzuki.e_store.GUI.customcomponents.TCurrencyLabel;
import tiktzuki.e_store.GUI.customcomponents.TFormControl;
import tiktzuki.e_store.GUI.customcomponents.TTable;
import tiktzuki.e_store.services.DateFormat;
import tiktzuki.e_store.services.Validators;

public class OrderModifyPanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Logger LOGGER = Logger.getLogger(getClass().getSimpleName());
    GroupLayout layout = new GroupLayout(this);
    // Order info
    public static TFormControl<TComboBox<Customer>> cbxCustomer = new TFormControl<TComboBox<Customer>>(new TComboBox<TComboBoxItem<Customer>>(), "Khách hàng");
    public static TFormControl<JTextField> txtReciver = new TFormControl<JTextField>(new JTextField(), "Người nhận");
    public static TFormControl<JTextField> txtPhone = new TFormControl<JTextField>(new JTextField(), "Số điện thoại");
    public static TFormControl<JTextField> txtAddress = new TFormControl<JTextField>(new JTextField(), "Địa chỉ", 300);
    public static TFormControl<JTextField> txtScheduleDate = new TFormControl<JTextField>(new JTextField(), "Ngày hẹn giao", true);
    public static TFormControl<TComboBox<OrderStatus>> cbxOrderStatus = new TFormControl<TComboBox<OrderStatus>>(new TComboBox<TComboBoxItem<OrderStatus>>(), "Trạng thái");
    public static JCheckBox chbIsPaid = new JCheckBox("Đã thanh toán", false);
    TBorderPanel pnlOrderInfo = new TBorderPanel("Thông tin đơn hàng", new GridLayout(0, 1, 5, 5));
    TButton btnPrintOrder = new TButton("In hóa đơn");

    // Order Detail
    static DefaultTableModel modelOrder = new DefaultTableModel(new Object[]{"Tên", "Giá", "Số lượng", "Thành tiền"}, 0);
    TTable tblOrderDetail = new TTable(modelOrder);
    JScrollPane orderScroller = new JScrollPane(tblOrderDetail);
    TBorderPanel pnlOrderDetail = new TBorderPanel("Chi tiết đơn hàng");

    //Total
    JLabel lblProvisional = new JLabel("Tạm tính");
    static JLabel lblProvisionalValue = new TCurrencyLabel("1000");
    JLabel lblCurrentCombos = new JLabel("Giảm giá");
    static TFormControl<JTextField> txtCurrentCombosValue = new TFormControl<JTextField>(new JTextField("0"), "");
    JLabel lblAmountPrice = new JLabel("Tổng");
    static JLabel lblAmountValue = new TCurrencyLabel("0");
    TBorderPanel pnlSummary = new TBorderPanel("Tổng");

    TButton btnCancle = new TButton("Hủy");
    TButton btnConfirm = new TButton("Lưu");
    JPanel pnlControl = new JPanel();


    public OrderModifyPanel() {
        initData();
        initComp();
    }

    public void initData() {
        // ORder info
        cbxCustomer = new TFormControl<TComboBox<Customer>>(new TComboBox<TComboBoxItem<Customer>>(), "Khách hàng");
        OrderManagerMainPanel.controller.getAllCustomer().forEach(customer -> {
            if (customer.getId() == OrderManagerController.selectedOrder.getCustomerId()) {
                cbxCustomer.getComponent().setSelectedItem(new TComboBoxItem<Customer>(customer, customer.getName()));
            } else {
                cbxCustomer.getComponent().addItem(new TComboBoxItem<Customer>(customer, customer.getName()));
            }
        });

        txtReciver.getComponent().setText(OrderManagerController.selectedOrder.getReceiver());
        txtAddress.getComponent().setText(OrderManagerController.selectedOrder.getAddress());
        txtPhone.getComponent().setText(OrderManagerController.selectedOrder.getPhone());
        txtScheduleDate.getComponent().setText(OrderManagerController.selectedOrder.getReceiveDate());

        cbxOrderStatus = new TFormControl<TComboBox<OrderStatus>>(new TComboBox<TComboBoxItem<OrderStatus>>(), "Trạng thái");
        OrderManagerMainPanel.controller.getAllOrderStatus().forEach(status -> {
            if (status.getId() == OrderManagerController.selectedOrder.getStatusId()) {
                cbxOrderStatus.getComponent().setSelectedItem(new TComboBoxItem<OrderStatus>(status, status.getName()));
            } else {
                cbxOrderStatus.getComponent().addItem(new TComboBoxItem<OrderStatus>(status, status.getName()));
            }
        });

        //Order detail
        modelOrder.setRowCount(0);
        OrderManagerController.selectedOrder.getOrderDetails().forEach(item -> {
            modelOrder.addRow(new Object[]{item.getDevice().getName(), item.getPrice(), item.getQuantity(), item.getAmount()});
            System.out.println(item);
        });
        // Summary
        lblProvisionalValue.setText(String.valueOf(OrderManagerController.provisional));
        lblAmountValue.setText(String.valueOf(OrderManagerController.selectedOrder.getAmount()));
        txtCurrentCombosValue.getComponent().setText(String.valueOf(OrderManagerController.totalComboDiscount));
    }

    void initComp() {
        // ORder info
        cbxCustomer.getComponent().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer cus = cbxCustomer.getComponent().getSelectedObject();
                txtReciver.getComponent().setText(cus.getName());
                txtAddress.getComponent().setText(cus.getAddress());
                txtPhone.getComponent().setText(cus.getPhone());
            }
        });
        btnPrintOrder.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (validateForm()) {
//					OrderManagerMainPanel.controller.commitSelectedOrderForm();
                    OrderManagerMainPanel.controller.printOrder();
                }
            }

            ;
        });

        pnlOrderInfo.add(cbxCustomer.getPnl());
        pnlOrderInfo.add(txtReciver.getPnl());
        pnlOrderInfo.add(txtPhone.getPnl());
        pnlOrderInfo.add(txtAddress.getPnl());
        pnlOrderInfo.add(txtScheduleDate.getPnl());
        pnlOrderInfo.add(cbxOrderStatus.getPnl());
        pnlOrderInfo.add(chbIsPaid);
        pnlOrderInfo.add(btnPrintOrder);

        //Order detail
        JPanel pnlOrderControl = new JPanel();
        pnlOrderControl.setPreferredSize(new Dimension(580, 40));
        orderScroller.setPreferredSize(new Dimension(580, 400));

        pnlOrderDetail.add(pnlOrderControl);
        pnlOrderDetail.add(orderScroller);
        pnlOrderDetail.setBackground(Resources.C_LIGHT);

        // Summary
        txtCurrentCombosValue.getComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtCurrentCombosValue.getComponent().setText(txtCurrentCombosValue.getComponent().getText().replaceAll("(?!\\d).+", ""));
            }
        });

        pnlSummary.setLayout(new GridLayout(3, 2));
        pnlSummary.add(lblProvisional);
        pnlSummary.add(lblProvisionalValue);
        pnlSummary.add(lblCurrentCombos);
        pnlSummary.add(txtCurrentCombosValue.getComponent());
        pnlSummary.add(lblAmountPrice);
        pnlSummary.add(lblAmountValue);
        pnlSummary.setPreferredSize(new Dimension(300, 60));

        // Control
        btnCancle.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                OrderManagerMainPanel.dispatch(OrderManagerMainPanel.Action.SWITCH_MANAGER);
            }
        });
        btnConfirm.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (validateForm()) {
//					OrderManagerMainPanel.controller.modifyOrder();
                }
            }
        });
        pnlControl.add(btnCancle);
        pnlControl.add(btnConfirm);

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlOrderInfo)
                        .addGroup(layout.createParallelGroup()
                                .addComponent(pnlOrderDetail, 0, 600, 600)
                                .addComponent(pnlSummary, 0, 600, 600))
                )
                .addComponent(pnlControl));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(pnlOrderInfo)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(pnlOrderDetail)
                                .addComponent(pnlSummary)))
                .addComponent(pnlControl, 40, 40, 40)
        );

        this.setLayout(layout);
        this.setPreferredSize(Resources.MAIN_CONTENT);
    }

    private boolean validateForm() {
        String message = "";
        if (!Validators.checkDate(txtScheduleDate.getComponent().getText())) {
            message += "Ngay hen giao khong hop le. ";
        }
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
                modelOrder.setRowCount(0);
                OrderCreateController.orderPreview.getOrderDetails().forEach(item -> {
                    modelOrder.addRow(new Object[]{item.getDevice().getName(), item.getPrice(), item.getQuantity(), item.getAmount()});
                });

                lblProvisionalValue.setText(String.valueOf(OrderCreateController.provisional));
                txtCurrentCombosValue.getComponent().setText(String.valueOf(OrderCreateController.totalComboDiscount));
                lblAmountValue.setText(String.valueOf(OrderCreateController.orderPreview.getAmount()));
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + action);
        }
    }
}
