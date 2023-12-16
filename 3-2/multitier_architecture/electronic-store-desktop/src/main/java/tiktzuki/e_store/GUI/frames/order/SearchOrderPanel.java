package tiktzuki.e_store.GUI.frames.order;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tiktzuki.e_store.BUS.OrderManagerController;
import tiktzuki.e_store.DTO.Customer;
import tiktzuki.e_store.DTO.OrderStatus;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.customcomponents.TButton;
import tiktzuki.e_store.GUI.customcomponents.TComboBox;
import tiktzuki.e_store.GUI.customcomponents.TComboBoxItem;
import tiktzuki.e_store.GUI.customcomponents.TFormControl;

public class SearchOrderPanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    GroupLayout layout = new GroupLayout(this);
    OrderManagerController controller = new OrderManagerController();

    TFormControl<TComboBox<Customer>> cbxCustomer = new TFormControl<TComboBox<Customer>>(new TComboBox<TComboBoxItem<Customer>>(), "Khách hàng");
    TFormControl<JTextField> txtCreateDateMin = new TFormControl<JTextField>(new JTextField(), "Ngày tạo (min)", true);
    TFormControl<JTextField> txtCreateDateMax = new TFormControl<JTextField>(new JTextField(), "Ngày tạo (max)", true);
    TFormControl<JTextField> txtAddress = new TFormControl<JTextField>(new JTextField(), "Địa chỉ");
    TFormControl<TComboBox<OrderStatus>> cbxOrderStatus = new TFormControl<TComboBox<OrderStatus>>(new TComboBox<TComboBoxItem<OrderStatus>>(), "Trạng thái");
    JCheckBox chbPaymentStatus = new JCheckBox("Đã thanh toán");
    TFormControl<JTextField> txtPhone = new TFormControl<JTextField>(new JTextField(), "Số điện thoại");

    TButton btnSearch = new TButton("Tìm");

    public SearchOrderPanel() {
        initData();
        initComp();
    }

    private void initData() {
        cbxCustomer.getComponent().addItem(new TComboBoxItem<Customer>(new Customer(-1, null, null, null, null, null, null, null), ""));
        controller.getAllCustomer().forEach(cus -> {
            cbxCustomer.getComponent().addItem(new TComboBoxItem<Customer>(cus, cus.getName()));
        });

        cbxOrderStatus.getComponent().addItem(new TComboBoxItem<OrderStatus>(new OrderStatus(-1, null), ""));
        controller.getAllOrderStatus().forEach(status -> {
            cbxOrderStatus.getComponent().addItem(new TComboBoxItem<OrderStatus>(status, status.getName()));
        });
    }

    private void initComp() {
        btnSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.search(
                        cbxCustomer.getComponent().getSelectedObject().getId(),
                        txtCreateDateMin.getComponent().getText(),
                        txtCreateDateMax.getComponent().getText(),
                        txtAddress.getComponent().getText(),
                        cbxOrderStatus.getComponent().getSelectedObject().getId(),
                        //TODO check
                        chbPaymentStatus.isSelected() ? 1 : 2,
                        txtPhone.getComponent().getText()
                );
            }
        });
        this.add(cbxCustomer.getPnl());
        this.add(txtCreateDateMin.getPnl());
        this.add(txtCreateDateMax.getPnl());
        this.add(txtAddress.getPnl());
        this.add(cbxOrderStatus.getPnl());
        this.add(txtPhone.getPnl());
        this.add(chbPaymentStatus);
        this.add(btnSearch);
        this.setBackground(Resources.C_WHITE);
    }

}
