package tiktzuki.e_store.GUI.frames.createorder;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import tiktzuki.e_store.BUS.OrderCreateController;
import tiktzuki.e_store.DTO.Customer;
import tiktzuki.e_store.DTO.Order;
import tiktzuki.e_store.DTO.OrderStatus;
import tiktzuki.e_store.GUI.MainFrame;
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

public class CreateOrderMainPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Logger LOGGER = Logger.getLogger(getClass().getSimpleName());
    OrderCreateController orderCreateController = new OrderCreateController();
    GroupLayout layout = new GroupLayout(this);
    // Order info
    public static TFormControl<TComboBox<Customer>> cbxCustomer = new TFormControl<TComboBox<Customer>>(new TComboBox<TComboBoxItem<Customer>>(), "Khách hàng");
    public static TFormControl<JTextField> txtReciver = new TFormControl<JTextField>(new JTextField(), "Người nhận");
    public static TFormControl<JTextField> txtPhone = new TFormControl<JTextField>(new JTextField(), "Số điện thoại");
    public static TFormControl<JTextField> txtAddress = new TFormControl<JTextField>(new JTextField(), "Địa chỉ");
    public static TFormControl<TComboBox<OrderStatus>> cbxOrderStatus = new TFormControl<TComboBox<OrderStatus>>(new TComboBox<TComboBoxItem<OrderStatus>>(), "Trạng thái");
    public static JCheckBox chbIsPaid = new JCheckBox("Đã thanh toán", false);
    TBorderPanel pnlOrderInfo = new TBorderPanel("Hóa đơn", new GridLayout(0, 1, 5, 5));
    TButton btnPrintOrder = new TButton("In hóa đơn");

    // Order Detail
    TButton btnRemoveOrder = new TButton(Resources.TRASH_BIN_ICON);
    TButton btnClearOrder = new TButton(Resources.EMPTY_ICON);
    static DefaultTableModel modelOrder = new DefaultTableModel(new Object[]{"Tên", "Giá", "Số lượng", "Thành tiền"}, 0);
    TTable tblOrderDetail = new TTable(modelOrder);
    JScrollPane orderScroller = new JScrollPane(tblOrderDetail);
    TBorderPanel pnlOrderDetail = new TBorderPanel("Chi tiết đơn hàng");

    // Combo
    TButton btnRemoveCombo = new TButton(Resources.TRASH_BIN_ICON);
    TButton btnClearCombo = new TButton(Resources.EMPTY_ICON);
    TButton btnApplyDiscount = new TButton("Áp dụng");
    TFormControl<JTextField> txtComboValue = new TFormControl<JTextField>(new JTextField(String.valueOf(OrderCreateController.totalComboDiscount)), "Tổng giá giảm");
    static DefaultTableModel modelCombo = new DefaultTableModel(new Object[]{"Mã", "Tên", "Giá giảm gợi ý"}, 0);
    TTable tblCombo = new TTable(modelCombo);
    JScrollPane comboScroller = new JScrollPane(tblCombo);
    TBorderPanel pnlComboDetail = new TBorderPanel("Combo");

    //Total
    JLabel lblProvisional = new JLabel("Tạm tính");
    static JLabel lblProvisionalValue = new TCurrencyLabel("1000");
    JLabel lblCurrentCombos = new JLabel("Giảm giá");
    static JLabel lblCurrentCombosValue = new TCurrencyLabel("0");
    JLabel lblAmountPrice = new JLabel("Tổng");
    static JLabel lblAmountValue = new TCurrencyLabel("0");
    TBorderPanel pnlSummary = new TBorderPanel("Tổng");

    TButton btnCancle = new TButton("Hủy");
    TButton btnConfirm = new TButton("Đặt");
    JPanel pnlControl = new JPanel();

    public CreateOrderMainPanel() {
        initData();
        initComp();
    }

    void initData() {
        // ORder info
        cbxOrderStatus = new TFormControl<TComboBox<OrderStatus>>(new TComboBox<TComboBoxItem<OrderStatus>>(), "Trạng thái");
        orderCreateController.getAllOrderStatus().forEach(status -> {
            cbxOrderStatus.getComponent().addItem(new TComboBoxItem<OrderStatus>(status, status.getName()));
        });
        cbxOrderStatus.getComponent().setSelectedIndex(1);
        ;


        cbxCustomer = new TFormControl<TComboBox<Customer>>(new TComboBox<TComboBoxItem<Customer>>(), "Khách hàng");
        orderCreateController.getAllCustomer().forEach(customer -> {
            cbxCustomer.getComponent().addItem(new TComboBoxItem<Customer>(customer, customer.getName()));
        });

        //Order detail
        modelOrder.setRowCount(0);
        OrderCreateController.orderPreview.getOrderDetails().forEach(item -> {
            modelOrder.addRow(new Object[]{item.getDevice().getName(), item.getPrice(), item.getQuantity(), item.getAmount()});
        });
        // Combo
        modelCombo.setRowCount(0);
        OrderCreateController.currentCombos.forEach(combo -> {
            modelCombo.addRow(new Object[]{combo.getComboId(), combo.getComboName(), combo.getDiscountValue()});
        });
        // Summary
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
                    orderCreateController.commitOrderForm();
                    orderCreateController.printOrder();
                }
            }

            ;
        });
        chbIsPaid.setBackground(Resources.C_LIGHT);
        pnlOrderInfo.add(cbxCustomer.getPnl());
        pnlOrderInfo.add(txtReciver.getPnl());
        pnlOrderInfo.add(txtPhone.getPnl());
        pnlOrderInfo.add(txtAddress.getPnl());
        pnlOrderInfo.add(cbxOrderStatus.getPnl());
        pnlOrderInfo.add(chbIsPaid);
        pnlOrderInfo.add(btnPrintOrder);

        //Order detail
        btnClearOrder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                orderCreateController.clearOrderItem();
            }
        });
        btnRemoveOrder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (tblOrderDetail.getSelectedRow() > -1) {
                    orderCreateController.removeOrderItem(tblOrderDetail.getValueAt(tblOrderDetail.getSelectedRow(), 0).toString());
                }
            }
        });
        JPanel pnlOrderControl = new JPanel();
        pnlOrderControl.add(btnClearOrder);
        pnlOrderControl.add(btnRemoveOrder);
        pnlOrderControl.setPreferredSize(new Dimension(580, 40));
        orderScroller.setPreferredSize(new Dimension(580, 400));

        pnlOrderDetail.add(pnlOrderControl);
        pnlOrderDetail.add(orderScroller);

        // Combo
        txtComboValue.getComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtComboValue.getComponent().setText(txtComboValue.getComponent().getText().replaceAll("(?!\\d).+", ""));
            }
        });
        btnApplyDiscount.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                orderCreateController.applyComboDiscount(Integer.valueOf(
                        txtComboValue.getComponent().getText().equals("") ? "0" : txtComboValue.getComponent().getText()));
            }
        });
        btnRemoveCombo.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (tblCombo.getSelectedRow() > -1) {
                    orderCreateController.removeCurrentCombo(tblCombo.getValueAt(tblCombo.getSelectedRow(), 1).toString());
                }
            }

            ;
        });
        btnClearCombo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                orderCreateController.clearCurrentCombos();

            }
        });
        JPanel pnlComboControl = new JPanel();
        pnlComboControl.add(btnClearCombo);
        pnlComboControl.add(btnRemoveCombo);
        pnlComboControl.add(txtComboValue.getPnl());
        pnlComboControl.add(btnApplyDiscount);
        pnlComboControl.setPreferredSize(new Dimension(580, 60));
        comboScroller.setPreferredSize(new Dimension(580, 200));

        pnlComboDetail.add(pnlComboControl);
        pnlComboDetail.add(comboScroller);

        // Summary
        pnlSummary.setLayout(new GridLayout(3, 2));
        pnlSummary.add(lblProvisional);
        pnlSummary.add(lblProvisionalValue);
        pnlSummary.add(lblCurrentCombos);
        pnlSummary.add(lblCurrentCombosValue);
        pnlSummary.add(lblAmountPrice);
        pnlSummary.add(lblAmountValue);
        pnlSummary.setPreferredSize(new Dimension(300, 60));

        // Control
        btnConfirm.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (validateForm())
                    orderCreateController.createOrder();
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
                                .addComponent(pnlComboDetail, 0, 600, 600)
                                .addComponent(pnlSummary, 0, 600, 600))
                )
                .addComponent(pnlControl));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(pnlOrderInfo)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(pnlOrderDetail)
                                .addComponent(pnlComboDetail)
                                .addComponent(pnlSummary)))
                .addComponent(pnlControl, 40, 40, 40)
        );

        this.setLayout(layout);
        this.setPreferredSize(Resources.MAIN_CONTENT);
    }

    private boolean validateForm() {
        String message = "";
        if (txtPhone.getComponent().getText().length() == 0) {
            message += "Khong duoc de trong so dien thoai. ";
        }
        if (OrderCreateController.orderPreview.getOrderDetails().size() == 0) {
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

                modelCombo.setRowCount(0);
                OrderCreateController.currentCombos.forEach(combo -> {
                    modelCombo.addRow(new Object[]{combo.getComboId(), combo.getComboName(), combo.getDiscountValue()});
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
}
