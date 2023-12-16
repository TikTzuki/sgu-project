package tiktzuki.e_store.GUI.frames.shippingnote;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import tiktzuki.e_store.BUS.ShippingNoteController;
import tiktzuki.e_store.DTO.Order;
import tiktzuki.e_store.DTO.ShippingNote;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.customcomponents.FindOrderDialog;
import tiktzuki.e_store.GUI.customcomponents.MessageDialog;
import tiktzuki.e_store.GUI.customcomponents.TBorderPanel;
import tiktzuki.e_store.GUI.customcomponents.TButton;
import tiktzuki.e_store.GUI.customcomponents.TCurrencyLabel;
import tiktzuki.e_store.GUI.customcomponents.TFormControl;
import tiktzuki.e_store.GUI.customcomponents.TTable;
import tiktzuki.e_store.services.DateFormat;
import tiktzuki.e_store.services.Formatter;
import tiktzuki.e_store.services.Validators;

import javax.swing.JPanel;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

import tiktzuki.e_store.BUS.ShippingNoteController;
import tiktzuki.e_store.DTO.Order;
import tiktzuki.e_store.GUI.customcomponents.TButton;

public class ShippingNoteCreatePanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    ShippingNoteController controller = new ShippingNoteController();
    TFormControl<JTextField> txtReceiver = new TFormControl<JTextField>(new JTextField(), "Người nhận");
    TFormControl<JTextField> txtAddress = new TFormControl<JTextField>(new JTextField(), "Địa chỉ giao", 300);
    TFormControl<JTextField> txtPhone = new TFormControl<JTextField>(new JTextField(), "Số điện thoại người nhận");
    TFormControl<JTextField> txtScheduleDate = new TFormControl<JTextField>(new JTextField(), "Ngày hẹn giao", true);

    TBorderPanel pnlOrderPreview = new TBorderPanel("Hoá đơn");
    static TFormControl<JTextField> txtCustomer = new TFormControl<JTextField>(new JTextField(), "Người đặt");
    static TFormControl<JTextField> txtCreateDate = new TFormControl<JTextField>(new JTextField(), "Ngày đặt");
    static TFormControl<JTextField> txtAmount = new TFormControl<JTextField>(new JTextField(), "Tổng");
    static TFormControl<JTextField> txtOrderStatus = new TFormControl<JTextField>(new JTextField(), "Trạng thái hóa đơn");
    static DefaultTableModel orderDetailModel = new DefaultTableModel(new Object[]{"Tên", "Giá", "Số lượng", "Thành tiền"}, 0);
    TTable tblOrderDetail = new TTable(orderDetailModel);
    JScrollPane scrollerOrderDetail = new JScrollPane(tblOrderDetail);

    JPanel pnlControl = new JPanel();
    TButton btnCancel = new TButton("Hủy");
    TButton btnSubmit = new TButton("Tạo");

    public ShippingNoteCreatePanel() {
        initData();
        initComp();
    }

    private void initData() {
        txtReceiver.getComponent().setText(ShippingNoteController.selectedOrder.getReceiver());
        txtAddress.getComponent().setText(ShippingNoteController.selectedOrder.getAddress());
        txtPhone.getComponent().setText(ShippingNoteController.selectedOrder.getPhone());

        Order order = ShippingNoteController.selectedOrder;
        txtCustomer.getComponent().setText(order.getCustomer().getName());
        txtCreateDate.getComponent().setText(order.getCreateDate());
        txtOrderStatus.getComponent().setText(order.getStatus().getName());
        txtAmount.getComponent().setText(Formatter.currencyFormat(order.getAmount()));
        orderDetailModel.setRowCount(0);
        order.getOrderDetails().forEach(orderItem -> {
            orderDetailModel.addRow(new Object[]{orderItem.getDevice().getName(), orderItem.getPrice(), orderItem.getQuantity(), orderItem.getAmount()});
        });
    }

    private void initComp() {
        // OrderPreview
        txtCustomer.getComponent().setEditable(false);
        txtCreateDate.getComponent().setEditable(false);
        txtAmount.getComponent().setEditable(false);
        txtOrderStatus.getComponent().setEditable(false);
        GroupLayout orderPreviewLayout = new GroupLayout(pnlOrderPreview);
        orderPreviewLayout.setAutoCreateContainerGaps(true);
        orderPreviewLayout.setAutoCreateGaps(true);
        orderPreviewLayout.setHorizontalGroup(orderPreviewLayout.createSequentialGroup()
                .addGroup(orderPreviewLayout.createParallelGroup()
                        .addComponent(txtCustomer.getPnl())
                        .addComponent(txtAmount.getPnl())
                        .addComponent(txtOrderStatus.getPnl())
                        .addComponent(txtCreateDate.getPnl())
                )
                .addComponent(scrollerOrderDetail));
        orderPreviewLayout.setVerticalGroup(orderPreviewLayout.createParallelGroup()
                .addGroup(orderPreviewLayout.createSequentialGroup()
                        .addComponent(txtCustomer.getPnl())
                        .addComponent(txtAmount.getPnl())
                        .addComponent(txtOrderStatus.getPnl())
                        .addComponent(txtCreateDate.getPnl())
                )
                .addComponent(scrollerOrderDetail));
        pnlOrderPreview.setLayout(orderPreviewLayout);
        //Control
        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ShippingNoteMainPanel.dispatch(ShippingNoteMainPanel.Action.SWITCH_TO_MAIN_PANEL);
            }
        });
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (validateForm()) {
                    controller.createShippingNote(new ShippingNote(
                            null,
                            txtReceiver.getComponent().getText(),
                            txtAddress.getComponent().getText(),
                            txtPhone.getComponent().getText(),
                            txtScheduleDate.getComponent().getText(),
                            ShippingNoteController.selectedOrder.getId()
                    ));
                    ShippingNoteMainPanel.dispatch(ShippingNoteMainPanel.Action.SWITCH_TO_MAIN_PANEL);
                }
            }
        });

        pnlControl.add(btnCancel);
        pnlControl.add(btnSubmit);


        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(txtReceiver.getPnl())
                                .addComponent(txtAddress.getPnl())
                                .addComponent(txtPhone.getPnl())
                                .addComponent(txtScheduleDate.getPnl())
                        )
                        .addComponent(pnlOrderPreview))
                .addComponent(pnlControl));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(txtReceiver.getPnl())
                                .addComponent(txtAddress.getPnl())
                                .addComponent(txtPhone.getPnl())
                                .addComponent(txtScheduleDate.getPnl())
                        )
                        .addComponent(pnlOrderPreview))
                .addComponent(pnlControl));
        this.setLayout(layout);
//		this.setPreferredSize(Resources.MAIN_CONTENT);
    }

    private boolean validateForm() {
        String receiver = txtReceiver.getComponent().getText();
        String address = txtAddress.getComponent().getText();
        String phone = txtPhone.getComponent().getText();
        String scheduleDate = txtScheduleDate.getComponent().getText();

        String message = "";
        if (receiver.equals("")) {
            message += "Khong dc de trong nguoi nhan. ";
        }
        if (address.equals("")) {
            message += "Khong dc de trong dia chi. ";
        }
        if (phone.equals("") || !Validators.checkPhone_Number(phone)) {
            message += "So dien thoai sai. ";
        }
        if (!Validators.checkDate(scheduleDate)) {
            message += "Sai dinh dang dd/MM/yyyy. ";
        }
        if (Validators.isBefore(scheduleDate, new DateFormat().format(new Date()))) {
            message += "Ngay hen phai sau ngay hien tai. ";
        }
        if (!message.equals("")) {
            new MessageDialog(message);
            return false;
        }
        return true;
    }
//	public enum Action{
//		SET_ORDER,
//	}
//	public static void dispatch(Action action) {
//		switch (action) {
//		case SET_ORDER:{
//			Order order = ShippingNoteController.selectedOrder;
//			txtCustomer.getComponent().setText( order.getCustomer().getName() );
//			txtCreateDate.getComponent().setText(order.getCreateDate());
//			txtOrderStatus.getComponent().setText( order.getStatus().getName());
//			txtAmount.getComponent().setText(Formatter.currencyFormat(order.getAmount()));
//			orderDetailModel.setRowCount(0);
//			order.getOrderDetails().forEach(orderItem ->{
//				orderDetailModel.addRow(new Object[] {orderItem.getDevice().getName(), orderItem.getPrice(), orderItem.getQuantity(), orderItem.getAmount()});
//			});
//			break;
//		}
//		default:
//			throw new IllegalArgumentException("Unexpected value: " + action);
//		}
//	}
}
