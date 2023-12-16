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
import tiktzuki.e_store.GUI.customcomponents.ConfirmDialog;
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

public class ShippingNoteManagerPanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    static ShippingNoteController controller = new ShippingNoteController();
    GroupLayout layout = new GroupLayout(this);
    JPanel pnlDetail = new JPanel();
    TFormControl<JTextField> txtReceiver = new TFormControl<JTextField>(new JTextField(), "Người nhận");
    TFormControl<JTextField> txtAddress = new TFormControl<JTextField>(new JTextField(), "Địa chỉ giao");
    TFormControl<JTextField> txtPhone = new TFormControl<JTextField>(new JTextField(), "Số điện thoại người nhận");
    TFormControl<JTextField> txtScheduleDate = new TFormControl<JTextField>(new JTextField(), "Ngày hẹn giao", true);

    TButton btnCreate = new TButton("Tạo");
    TButton btnModify = new TButton("Sửa");
    TButton btnDelete = new TButton("Xóa");
    TButton btnPrint = new TButton("In");

    TBorderPanel pnlOrderPreview = new TBorderPanel("Hoá đơn");
    static TFormControl<JTextField> txtCustomer = new TFormControl<JTextField>(new JTextField(), "Người đặt");
    static TFormControl<JTextField> txtCreateDate = new TFormControl<JTextField>(new JTextField(), "Ngày đặt");
    static TFormControl<JTextField> txtAmount = new TFormControl<JTextField>(new JTextField(), "Tổng");
    static TFormControl<JTextField> txtOrderStatus = new TFormControl<JTextField>(new JTextField(), "Trạng thái hóa đơn");
    static DefaultTableModel orderDetailModel = new DefaultTableModel(new Object[]{"Tên", "Giá", "Số lượng", "Thành tiền"}, 0);
    TTable tblOrderDetail = new TTable(orderDetailModel);
    JScrollPane scrollerOrderDetail = new JScrollPane(tblOrderDetail);

    JPanel pnlSearch = new JPanel();

    static DefaultTableModel model = new DefaultTableModel(new Object[]{"#", "Người nhận", "Địa chỉ nhận", "Số điện thoại", "Ngày hẹn giao", "Người đặt", "Ngày đặt"}, 0);
    static TTable tblShippingNote = new TTable(model);
    JScrollPane scroller = new JScrollPane(tblShippingNote);

    public ShippingNoteManagerPanel() {
        initData();
        initComp();
    }

    private void initData() {
        loadTableShippingNote();
    }

    private void initComp() {
        // Shipping detail
        btnCreate.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                FindOrderDialog.dispatch(FindOrderDialog.Action.SWITCH_STATE);
            }
        });

        btnModify.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (validateForm() && new ConfirmDialog("Xac nhan sua?").confirm()) {
                    commitShippingNote();
                    controller.modifyShippingNote(ShippingNoteController.currentShippingNote);
                }
            }
        });
        btnDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (new ConfirmDialog("Xac nhan xoa").confirm() && ShippingNoteController.currentShippingNote != null) {
                    controller.deleteOrder(ShippingNoteController.currentShippingNote.getId());
                }
            }
        });
        btnPrint.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (ShippingNoteController.currentShippingNote != null && new ConfirmDialog("In?").confirm())
                    controller.printShippingNote(ShippingNoteController.currentShippingNote);
            }
        });
        GroupLayout detailPanelLayout = new GroupLayout(pnlDetail);
        detailPanelLayout.setAutoCreateContainerGaps(true);
        detailPanelLayout.setAutoCreateGaps(true);

        detailPanelLayout.setHorizontalGroup(detailPanelLayout.createSequentialGroup()
                .addGroup(detailPanelLayout.createParallelGroup()
                        .addComponent(txtReceiver.getPnl())
                        .addComponent(txtAddress.getPnl())
                        .addComponent(txtPhone.getPnl())
                        .addComponent(txtScheduleDate.getPnl())
                )
                .addGroup(detailPanelLayout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(btnCreate, 80, 80, 80)
                        .addComponent(btnModify, 80, 80, 80)
                        .addComponent(btnDelete, 80, 80, 80)
                        .addComponent(btnPrint, 80, 80, 80))
                .addComponent(pnlOrderPreview));
        detailPanelLayout.setVerticalGroup(detailPanelLayout.createParallelGroup(Alignment.CENTER)
                .addGroup(detailPanelLayout.createSequentialGroup()
                        .addComponent(txtReceiver.getPnl())
                        .addComponent(txtAddress.getPnl())
                        .addComponent(txtPhone.getPnl())
                        .addComponent(txtScheduleDate.getPnl())
                )
                .addGroup(detailPanelLayout.createSequentialGroup()
                        .addComponent(btnCreate)
                        .addComponent(btnModify)
                        .addComponent(btnDelete)
                        .addComponent(btnPrint))
                .addComponent(pnlOrderPreview));
        pnlDetail.setLayout(detailPanelLayout);
        pnlDetail.setBackground(Resources.C_LIGHT);
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
        //SEARCH
        pnlSearch.setBackground(Resources.C_LIGHT);
        // Table
        tblShippingNote.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setSelectedShippingNote();
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
                .addComponent(pnlSearch)
                .addComponent(scroller));
        this.setLayout(layout);
        this.setBackground(Resources.C_LIGHT);
        this.setPreferredSize(Resources.MAIN_CONTENT);
    }

    private void setSelectedShippingNote() {
        if (tblShippingNote.getSelectedRow() == -1) {
            return;
        }
        Integer id = Integer.valueOf(tblShippingNote.getValueAt(tblShippingNote.getSelectedRow(), 0).toString());
        ShippingNoteController.currentShippingNote = ShippingNoteController.shippingNotes.stream().filter(item -> item.getId().equals(id)).findAny().get();
        ShippingNote note = ShippingNoteController.currentShippingNote;
        txtReceiver.getComponent().setText(note.getReceiver());
        txtAddress.getComponent().setText(note.getAddress());
        txtPhone.getComponent().setText(note.getPhoneNumber());
        txtScheduleDate.getComponent().setText(note.getScheduleDate());
        txtCustomer.getComponent().setText(note.getOrder().getCustomer().getName());
        txtAmount.getComponent().setText(Formatter.currencyFormat(note.getOrder().getAmount()));
        txtOrderStatus.getComponent().setText(note.getOrder().getStatus().getName());
        txtCreateDate.getComponent().setText(note.getOrder().getCreateDate());

        orderDetailModel.setRowCount(0);
        note.getOrder().getOrderDetails().forEach(item -> {
            orderDetailModel.addRow(new Object[]{item.getDevice().getName(), item.getPrice(), item.getPrice(), item.getAmount()});
        });
    }

    private static void loadTableShippingNote() {
        model.setRowCount(0);
        controller.getAllShippingNote();
        ShippingNoteController.shippingNotes.forEach(note -> {
            model.addRow(new Object[]{
                    note.getId(),
                    note.getReceiver(),
                    note.getAddress(),
                    note.getPhoneNumber(),
                    note.getScheduleDate(),
                    note.getOrder().getCustomer().getName(),
                    note.getOrder().getCreateDate()});
        });
        tblShippingNote.revalidate();
        tblShippingNote.repaint();
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

    private void commitShippingNote() {
        ShippingNoteController.currentShippingNote.setAddress(txtAddress.getComponent().getText());
        ShippingNoteController.currentShippingNote.setPhoneNumber(txtPhone.getComponent().getText());
        ShippingNoteController.currentShippingNote.setReceiver(txtReceiver.getComponent().getText());
        ShippingNoteController.currentShippingNote.setScheduleDate(txtScheduleDate.getComponent().getText());
    }

    public enum Action {
        UPDATE,
    }

    public static void dispatch(Action action) {
        switch (action) {
            case UPDATE: {
                loadTableShippingNote();
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + action);
        }
    }
}
