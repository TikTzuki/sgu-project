package tiktzuki.e_store.BUS;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.Data;
import tiktzuki.e_store.DAL.*;
import tiktzuki.e_store.DTO.Order;
import tiktzuki.e_store.DTO.OrderDetail;
import tiktzuki.e_store.DTO.ShippingNote;
import tiktzuki.e_store.GUI.customcomponents.FindOrderDialog;
import tiktzuki.e_store.GUI.frames.shippingnote.ShippingNoteCreatePanel;
import tiktzuki.e_store.GUI.frames.shippingnote.ShippingNoteMainPanel;
import tiktzuki.e_store.GUI.frames.shippingnote.ShippingNoteManagerPanel;
import tiktzuki.e_store.services.Formatter;

@Data
public class ShippingNoteController {
    ShippingNoteRepository shippingNoteRepos = new ShippingNoteRepository();
    OrderRepository orderRepos = new OrderRepository();
    OrderDetailRepository orderDetailRepos = new OrderDetailRepository();
    private OrderStatusRepository statusRepos = new OrderStatusRepository();
    private DeviceRepository deviceRepos = new DeviceRepository();
    private CustomerRepository customerRepos = new CustomerRepository();
    public static Order selectedOrder;
    public static List<ShippingNote> shippingNotes;
    public static ShippingNote currentShippingNote;

    public void createShippingNote(ShippingNote shippingNote) {
        shippingNoteRepos.insert(shippingNote);
    }

    public void modifyShippingNote(ShippingNote shippingNote) {
        shippingNoteRepos.update(shippingNote);
        ShippingNoteManagerPanel.dispatch(ShippingNoteManagerPanel.Action.UPDATE);
    }

    public List<ShippingNote> getAllShippingNote() {
        List<ShippingNote> notes = shippingNoteRepos.findAll();
        notes.forEach(note -> {
            Order order = orderRepos.findById(note.getOrderId()).get();
            order.setCustomer(customerRepos.findById(order.getCustomerId()).get());
            order.setStatus(statusRepos.findById(order.getStatusId()).get());
            List<OrderDetail> orderItems = orderDetailRepos.findAllByOrderId(order.getId());
            orderItems.forEach(item -> {
                item.setDevice(deviceRepos.findById(item.getDeviceId()).get());
            });
            order.setOrderDetails(orderItems);
            note.setOrder(order);
        });
        this.shippingNotes = notes;
        return notes;
    }

    public List<Order> getAllOrder() {
        List<Order> orders = orderRepos.findAll();
        orders.forEach(order -> {
            order.setOrderDetails(orderDetailRepos.findAllByOrderId(order.getId()));
            order.getOrderDetails().forEach(item -> {
                item.setDevice(deviceRepos.findById(item.getDeviceId()).get());
            });
            order.setCustomer(customerRepos.findById(order.getCustomerId()).get());
            order.setStatus(statusRepos.findById(order.getStatusId()).get());
        });
        return orders;
    }

    public void setSelectedOrder(int id) {
        selectedOrder = this.getAllOrder().stream().filter(order -> order.getId() == id).findAny().get();
        FindOrderDialog.dispatch(FindOrderDialog.Action.SWITCH_STATE);
        ShippingNoteMainPanel.dispatch(ShippingNoteMainPanel.Action.SWITCH_TO_CREATE_PANEL);
    }

    public void deleteOrder(int id) {
        shippingNoteRepos.deleteById(id);
        ShippingNoteManagerPanel.dispatch(ShippingNoteManagerPanel.Action.UPDATE);
    }

    public void printShippingNote(ShippingNote shippingNote) {
        String fileName = "Phieu_Giao_Hang_" + System.currentTimeMillis();
        if (shippingNote.getOrder().getCustomer() != null) {
            fileName = "Phieu_Giao_Hang_" + shippingNote.getOrder().getCustomer().getName() + "_" + System.currentTimeMillis();
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
                    "Khach hang: " + shippingNote.getOrder().getCustomer() != null ? shippingNote.getOrder().getCustomer().getName() : "---");
            list.add("Ngay dat: " + shippingNote.getOrder().getCreateDate());
            list.add("-------------------------------------");
            list.add("Nguoi nhan: " + shippingNote.getReceiver());
            list.add("So dien thoai: " + shippingNote.getPhoneNumber());
            list.add("Dia chi: " + shippingNote.getAddress());
            list.add("Ngay giao: " + shippingNote.getScheduleDate());
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
            shippingNote.getOrder().getOrderDetails().forEach(item -> {
                table.addCell(item.getDevice().getName());
                table.addCell(String.valueOf(item.getPrice()));
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(String.valueOf(item.getAmount()));
            });
            // Summary
            com.itextpdf.text.List listSumary = new com.itextpdf.text.List();
            listSumary.add("Tong: " + Formatter.currencyFormat(shippingNote.getOrder().getAmount()) + " VND \n");

            document.add(title);
            document.add(list);
            document.add(table);
            document.add(listSumary);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
