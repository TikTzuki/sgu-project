package tiktzuki.e_store.BUS;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.TabStop.Alignment;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.cj.x.protobuf.MysqlxCrud.OrderOrBuilder;

import tiktzuki.e_store.DAL.CustomerRepository;
import tiktzuki.e_store.DAL.DeviceRepository;
import tiktzuki.e_store.DAL.OrderDetailRepository;
import tiktzuki.e_store.DAL.OrderRepository;
import tiktzuki.e_store.DAL.OrderStatusRepository;
import tiktzuki.e_store.DTO.Combo;
import tiktzuki.e_store.DTO.Customer;
import tiktzuki.e_store.DTO.Device;
import tiktzuki.e_store.DTO.Order;
import tiktzuki.e_store.DTO.OrderDetail;
import tiktzuki.e_store.DTO.OrderStatus;
import tiktzuki.e_store.GUI.MainFrame;
import tiktzuki.e_store.GUI.customcomponents.MessageDialog;
import tiktzuki.e_store.GUI.customcomponents.OrderPreviewDialog;
import tiktzuki.e_store.GUI.frames.createorder.CreateOrderMainPanel;
import tiktzuki.e_store.services.DateFormat;
import tiktzuki.e_store.services.Formatter;

public class OrderCreateController {
    private OrderRepository orderRepos = new OrderRepository();
    private OrderDetailRepository orderDetailRepos = new OrderDetailRepository();
    private DeviceRepository deviceReopos = new DeviceRepository();
    private CustomerRepository customerRepos = new CustomerRepository();
    private OrderStatusRepository orderStatusRepos = new OrderStatusRepository();

    public static Order orderPreview = new Order();

    static {
        orderPreview.setStaffId(MainFrame.CURRENT_USER != null ? MainFrame.CURRENT_USER.getId() : 1);
        orderPreview.setCreateDate(new DateFormat().format(new Date()));
    }

    public static List<Combo> currentCombos = new ArrayList<Combo>();
    public static Integer totalComboDiscount = 0;
    public static Integer provisional = 0;
    public static Integer shippingFee = 0;

    public void addOrderItem(Device device, int quantity) {
        OrderDetail newOrderDetail = new OrderDetail(null, device.getId(), quantity, device.getPrice() * quantity,
                device.getPrice(), device);

        OrderDetail oldOrderDetail = orderPreview.getOrderDetails().stream()
                .filter(item -> item.getDeviceId().equals(newOrderDetail.getDeviceId())).findAny().orElse(null);
        if (oldOrderDetail != null) {
            oldOrderDetail.setQuantity(oldOrderDetail.getQuantity() + quantity);
            oldOrderDetail.setAmount(oldOrderDetail.getAmount() + device.getPrice() * quantity);
            ;
        } else {
            orderPreview.getOrderDetails().add(newOrderDetail);
        }

        commitPrewviewOrder();
    }

    public void commitOrderForm() {
        Order order = OrderCreateController.orderPreview;
        order.setCustomer(CreateOrderMainPanel.cbxCustomer.getComponent().getSelectedObject());
        order.setCustomerId(order.getCustomer().getId());
        order.setReceiver(CreateOrderMainPanel.txtReciver.getComponent().getText());
        order.setPhone(CreateOrderMainPanel.txtPhone.getComponent().getText());
        order.setAddress(CreateOrderMainPanel.txtAddress.getComponent().getText());
        order.setStatus(CreateOrderMainPanel.cbxOrderStatus.getComponent().getSelectedObject());
        order.setStatusId(order.getStatus().getId());
        // TODO: check
        order.setPaymentStatus(CreateOrderMainPanel.chbIsPaid.isSelected() ? 1 : 2);
    }

    public void createOrder() {
        commitOrderForm();
        for (OrderDetail item : orderPreview.getOrderDetails()) {
            if (deviceReopos.findById(item.getDeviceId()).get().getQuantity() < item.getQuantity()) {
                new MessageDialog("Sản phẩm " + item.getDevice().getName() + " không đủ hàng");
                return;
            }
        }
        orderRepos.insert(orderPreview);
        Integer id = 0;
        for (Order order : orderRepos.findAll()) {
            id = (order.getId() > id ? order.getId() : id);
        }
        List<Device> devicesForUpdate = new ArrayList<Device>();
        for (OrderDetail item : orderPreview.getOrderDetails()) {
            item.setOrderId(id);
            Device deviceForUpdate = deviceReopos.findById(item.getDeviceId()).get();
            deviceForUpdate.setQuantity(deviceForUpdate.getQuantity() - item.getQuantity());
            devicesForUpdate.add(deviceForUpdate);
        }
        deviceReopos.updateAll(devicesForUpdate);
        orderDetailRepos.insertAll(orderPreview.getOrderDetails());
    }

    private void commitPrewviewOrder() {
        // Update selected combo
        currentCombos = CatalogController.combos.stream().filter(combo -> {
            return combo.stream()
                    .allMatch(deviceType -> orderPreview.getOrderDetails().stream()
                            .anyMatch(item -> item.getDevice().getDeviceTypeId().equals(deviceType.getId())))
                    && (combo.size() > 0);
        }).collect(Collectors.toList());

        // Update discount value
        totalComboDiscount = 0;
        currentCombos.forEach(combo -> totalComboDiscount += combo.getDiscountValue());

        // Update provisional
        provisional = 0;
        orderPreview.getOrderDetails().forEach(item -> provisional += item.getAmount());

        // Update amount
        orderPreview.setAmount(0);
        orderPreview.setAmount(provisional - (totalComboDiscount + shippingFee));
        OrderPreviewDialog.dispatch(OrderPreviewDialog.Action.UPDATE);
        CreateOrderMainPanel.dispatch(CreateOrderMainPanel.Action.UPDATE);
    }

    public void clearOrderItem() {
        orderPreview.setOrderDetails(new ArrayList<OrderDetail>());
        commitPrewviewOrder();
    }

    public void removeOrderItem(String comboName) {
        orderPreview.getOrderDetails().removeIf(i -> i.getDevice().getName().equals(comboName));
        commitPrewviewOrder();
    }

    public void clearCurrentCombos() {
        currentCombos = new ArrayList<Combo>();
        commitPrewviewOrder();
    }

    public void removeCurrentCombo(String comboName) {
        currentCombos.removeIf(combo -> combo.getComboName().equals(comboName));
        commitPrewviewOrder();
    }

    public void applyComboDiscount(Integer newValue) {
        if (newValue > orderPreview.getAmount()) {
            new MessageDialog("Giá giảm không được cao hơn giá bán");
            return;
        }
        totalComboDiscount = newValue;
        // Update amount
        orderPreview.setAmount(0);
        orderPreview.setAmount(provisional - (totalComboDiscount + shippingFee));
        OrderPreviewDialog.dispatch(OrderPreviewDialog.Action.UPDATE);
        CreateOrderMainPanel.dispatch(CreateOrderMainPanel.Action.UPDATE);
    }

    public List<OrderStatus> getAllOrderStatus() {
        return orderStatusRepos.findAll();
    }

    public List<Customer> getAllCustomer() {
        return customerRepos.findAll();
    }

    public void printOrder() {
        System.out.println(orderPreview.getCreateDate());
        String fileName = "Hoa_Don_" + System.currentTimeMillis();
        if (orderPreview.getCustomer() != null) {
            fileName = "Hoa_Don_" + orderPreview.getCustomer().getName() + "_" + System.currentTimeMillis();
        }

        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Paragraph title = new Paragraph("Phieu giao hang", font);

            // Creating a list
            com.itextpdf.text.List list = new com.itextpdf.text.List();

            // Add elements to the list
            list.add(
                    "Khach hang: " + orderPreview.getCustomer() != null ? orderPreview.getCustomer().getName() : "---");
            list.add("Ngay dat: " + orderPreview.getCreateDate());
            list.add("\n");

            // Table
            PdfPTable table = new PdfPTable(4);
            Stream.of("San pham", "Don gia", "So luong", "Thanh tien").forEach(columnTitle -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.WHITE);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(columnTitle));
                table.addCell(header);
            });
            orderPreview.getOrderDetails().forEach(item -> {
                table.addCell(item.getDevice().getName());
                table.addCell(String.valueOf(item.getPrice()));
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(String.valueOf(item.getAmount()));
            });
            // Summary
            com.itextpdf.text.List listSumary = new com.itextpdf.text.List();
            listSumary.add("Tien hang: " + Formatter.currencyFormat(OrderCreateController.provisional) + " VND \n");
            listSumary.add("Giam gia: " + Formatter.currencyFormat(OrderCreateController.totalComboDiscount) + " VND \n");
            listSumary.add("Tong: " + Formatter.currencyFormat(orderPreview.getAmount()) + " VND \n");

            document.add(title);
            document.add(list);
            document.add(table);
            document.add(listSumary);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
//		OrderController controller = new OrderController();
//		OrderController controller2 = new OrderController();
//		Device d1 = controller.deviceReopos.findById(13);
//		Device d = controller.deviceReopos.findById(4);
//		controller.addOrderItem(d1, 1);
//		controller.addOrderItem(d, 1);
//		//		controller.orderPreview.getOrderDetails().forEach(System.out::println);
//		controller.currentCombos.forEach(System.out::println);
    }
}
