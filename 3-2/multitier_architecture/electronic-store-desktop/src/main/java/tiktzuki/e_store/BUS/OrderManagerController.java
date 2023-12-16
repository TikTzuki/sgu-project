package tiktzuki.e_store.BUS;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import tiktzuki.e_store.DAL.CustomerRepository;
import tiktzuki.e_store.DAL.DeviceRepository;
import tiktzuki.e_store.DAL.OrderDetailRepository;
import tiktzuki.e_store.DAL.OrderRepository;
import tiktzuki.e_store.DAL.OrderStatusRepository;
import tiktzuki.e_store.DTO.Combo;
import tiktzuki.e_store.DTO.Customer;
import tiktzuki.e_store.DTO.Order;
import tiktzuki.e_store.DTO.OrderDetail;
import tiktzuki.e_store.DTO.OrderStatus;
import tiktzuki.e_store.GUI.customcomponents.FindOrderDialog;
import tiktzuki.e_store.GUI.customcomponents.MessageDialog;
import tiktzuki.e_store.GUI.frames.order.OrderManagerMainPanel;
import tiktzuki.e_store.GUI.frames.order.OrderManagerPanel;
import tiktzuki.e_store.GUI.frames.order.OrderModifyPanel;
import tiktzuki.e_store.services.DateFormat;
import tiktzuki.e_store.services.Formatter;
import tiktzuki.e_store.services.Validators;

public class OrderManagerController {
    private OrderRepository orderRepos = new OrderRepository();
    private OrderDetailRepository orderDetailRepos = new OrderDetailRepository();
    private CustomerRepository customerRepos = new CustomerRepository();
    private OrderStatusRepository statusRepos = new OrderStatusRepository();
    private DeviceRepository deviceRepos = new DeviceRepository();

    public static List<Order> orders = new ArrayList<Order>();
    public static Order selectedOrder = new Order();
    public static List<Combo> currentCombos = new ArrayList<Combo>();
    public static Integer totalComboDiscount = 0;
    public static Integer provisional = 0;
    public static Integer shippingFee = 0;

    public OrderManagerController() {

    }

    public void setSelectedOrder(int id) {
        selectedOrder = this.getAllOrder().stream().filter(order -> order.getId() == id).findAny().get();
    }

    public List<Order> getAllOrder() {
        orders = orderRepos.findAll();
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

    public List<Customer> getAllCustomer() {
        return customerRepos.findAll();
    }

    public List<OrderStatus> getAllOrderStatus() {
        return statusRepos.findAll();
    }

    public List<Order> search(
            int customerId,
            String createDateMin,
            String createDateMax,
            String address,
            int statusId,
            int paymentStatus,
            String phoneNumber) {
        orders = this.getAllOrder();
        orders.removeIf(order -> {
            return (customerId > 0 ? !(order.getCustomerId() == customerId) : false) ||
                    (!createDateMin.equals("") ? !Validators.isBefore(createDateMin, order.getCreateDate()) : false) ||
                    (!createDateMax.equals("") ? !Validators.isBefore(order.getCreateDate(), createDateMax) : false) ||
                    (!address.equals("") ? !order.getAddress().toLowerCase().contains(address.toLowerCase()) : false) ||
                    (statusId > 0 ? !(order.getStatusId() == statusId) : false) ||
                    (paymentStatus > 0 ? !(order.getPaymentStatus() == paymentStatus) : false) ||
                    (!phoneNumber.equals("") ? !(order.getPhone().contains(phoneNumber)) : false);
        });
        OrderManagerPanel.dispatch(OrderManagerPanel.Action.UPDATE);
        FindOrderDialog.dispatch(FindOrderDialog.Action.UPDATE);
        return orders;
    }

    public void deleteOrder(int orderId) {
        if (selectedOrder.getId() != orderId) {
            return;
        }
        selectedOrder.setStatusId(3);
        orderRepos.update(selectedOrder);
        selectedOrder = new Order();
        OrderManagerMainPanel.dispatch(OrderManagerMainPanel.Action.SWITCH_MANAGER);
    }

    public void removeOrderItem(String comboName) {
        selectedOrder.getOrderDetails().removeIf(i -> i.getDevice().getName().equals(comboName));
//		commitSelectedOrderForm();
    }

    public void clearOrderItem() {
        selectedOrder.setOrderDetails(new ArrayList<OrderDetail>());
//		commitSelectedOrderForm();
    }

    public void applyComboDiscount(Integer newValue) {
        if (newValue > selectedOrder.getAmount()) {
            new MessageDialog("Giá giảm không được thấp hơn giá bán");
            return;
        }
        totalComboDiscount = newValue;
        // Update amount
        selectedOrder.setAmount(0);
        selectedOrder.setAmount(provisional - (totalComboDiscount + shippingFee));
        OrderModifyPanel.dispatch(OrderModifyPanel.Action.UPDATE);
    }

//	public void commitSelectedOrderForm() {
//		Order order = OrderManagerController.selectedOrder;
//		order.setCustomer(OrderModifyPanel.cbxCustomer.getComponent().getSelectedObject());
//		order.setCustomerId(order.getCustomer().getId());
//		order.setReceiver(OrderModifyPanel.txtReciver.getComponent().getText());
//		order.setPhone(OrderModifyPanel.txtPhone.getComponent().getText());
//		order.setAddress(OrderModifyPanel.txtAddress.getComponent().getText());
//		order.setReceiveDate(OrderModifyPanel.txtScheduleDate.getComponent().getText());
//		order.setStatus(OrderModifyPanel.cbxOrderStatus.getComponent().getSelectedObject());
//		order.setStatusId(order.getStatus().getId());
//		// TODO: check
//		order.setPaymentStatus(OrderModifyPanel.chbIsPaid.isSelected() ? 1 : 2 );
//		OrderModifyPanel.dispatch(OrderModifyPanel.Action.UPDATE);
//	}

    public void modifyOrder(Order order) {
//		commitSelectedOrderForm();
        orderRepos.update(order);
        selectedOrder = new Order();
        OrderManagerMainPanel.dispatch(OrderManagerMainPanel.Action.SWITCH_MANAGER);
    }

    public void printOrder() {
        String fileName = "Hoa_Don_" + System.currentTimeMillis();
        if (selectedOrder.getCustomer() != null) {
            fileName = "Hoa_Don_" + selectedOrder.getCustomer().getName() + "_" + System.currentTimeMillis();
        }

        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Paragraph title = new Paragraph("Hoa Don", font);

            // Creating a list
            com.itextpdf.text.List list = new com.itextpdf.text.List();

            // Add elements to the list
            list.add("Khach hang: " + selectedOrder.getCustomer() != null ? selectedOrder.getCustomer().getName() : "---");
            list.add("Ngay dat: " + selectedOrder.getCreateDate());
            list.add("\n");

            // Table
            PdfPTable table = new PdfPTable(4);
            Stream.of("San pham", "Don gia", "So luong", "Thanh tien")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.WHITE);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(columnTitle));
                        table.addCell(header);
                    });
            selectedOrder.getOrderDetails().forEach(item -> {
                table.addCell(item.getDevice().getName());
                table.addCell(String.valueOf(item.getPrice()));
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(String.valueOf(item.getAmount()));
            });
            // Summary
            com.itextpdf.text.List listSumary = new com.itextpdf.text.List();
            listSumary.add("Tong: " + Formatter.currencyFormat(selectedOrder.getAmount()) + " VND \n");

            document.add(title);
            document.add(list);
            document.add(table);
            document.add(listSumary);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//	public void printShippingNote() {
//		String fileName = "Phieu_Giao_Hang_" + System.currentTimeMillis();
//		if(selectedOrder.getCustomer() != null ){
//			fileName = "Phieu_Giao_Hang_"+ selectedOrder.getCustomer().getName() +"_"+ System.currentTimeMillis();
//		}
//
//		try {
//		Document document = new Document();
//		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName+ ".pdf"));
//		document.open();
//		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
//		Paragraph title = new Paragraph("Phieu giao hang", font);
//		
//		// Creating a list
//	      com.itextpdf.text.List list = new com.itextpdf.text.List();  
//	      
//	      // Add elements to the list       
//	      list.add("Khach hang: "+selectedOrder.getCustomer()!= null ? selectedOrder.getCustomer().getName() : "---");      
//	      list.add("Ngay dat: "+ selectedOrder.getCreateDate());
//	      list.add("-------------------------------------");
//	      list.add("Nguoi nhan: "+selectedOrder.getReceiver());      
//	      list.add("So dien thoai: "+selectedOrder.getPhone());    
//	      list.add("Dia chi: " + selectedOrder.getAddress());
//	      list.add("Ngay giao: "+ selectedOrder.getReceiveDate());
//	      list.add("\n");       
//
//	      // Table
//		PdfPTable table = new PdfPTable(4);
//	    Stream.of("San pham", "Don gia", "So luong", "Thanh tien")
//	      .forEach(columnTitle -> {
//	        PdfPCell header = new PdfPCell();
//	        header.setBackgroundColor(BaseColor.WHITE);
//	        header.setBorderWidth(2);
//	        header.setPhrase(new Phrase(columnTitle));
//	        table.addCell(header);
//	    });
//	    selectedOrder.getOrderDetails().forEach(item -> {
//	    	table.addCell(item.getDevice().getName());
//	    	table.addCell(String.valueOf(item.getPrice()));
//	    	table.addCell(String.valueOf(item.getQuantity()));
//	    	table.addCell(String.valueOf(item.getAmount()));
//	    });
//	    // Summary
//	      com.itextpdf.text.List listSumary = new com.itextpdf.text.List();  
//	    listSumary.add("Tien hang: " + Formatter.currencyFormat(OrderCreateController.provisional) + " VND \n");
//	    listSumary.add("Giam gia: " + Formatter.currencyFormat(OrderCreateController.totalComboDiscount) + " VND \n");
//	    listSumary.add("Tong: " + Formatter.currencyFormat(selectedOrder.getAmount()) + " VND \n");
//
//		document.add(title);
//		document.add(list);
//		document.add(table);
//		document.add(listSumary);
//		document.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
