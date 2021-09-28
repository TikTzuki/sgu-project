package com.tourism.GUI.frames.touristgroup.modify;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.tourism.BUS.CustomerController;
import com.tourism.BUS.TouristGroupController;
import com.tourism.DTO.Customer;
import com.tourism.DTO.TouristGroup;
import com.tourism.GUI.CustomTable;

public class AddCustomerToTouristGroupDialog {
	JDialog dialog;
	JPanel pnl;
	GroupLayout layout;
	
	DefaultTableModel model;
	JTable tbl;
	JScrollPane scroller;
	
	JButton btnAdd;
	JButton btnCancel;
	
	CustomerController customerController;
	TouristGroupController touristGroupController;
	TouristGroup TG;
	Customer selectedCustomer;
	
	public AddCustomerToTouristGroupDialog(TouristGroup TG) {
		this.TG = TG;
		initData();
		initComp();
	}
	
	private void initData() {
		dialog = new JDialog();
		pnl = new JPanel();
		layout = new GroupLayout(pnl);
		
		model = new DefaultTableModel(new Object[] {"Mã", "Tên", "Sđt", "Căn cước", "Giới tính", "Địa chỉ"}, 0);
		tbl = new CustomTable(model);
		scroller = new JScrollPane(tbl);
		
		btnAdd = new JButton("Thêm");
		btnCancel = new JButton("Xóa");
		
		customerController = new CustomerController();
		touristGroupController = new TouristGroupController();
		
		customerController.getAll().forEach(customer->{
			model.addRow(new Object[] {
					customer.getId(),
					customer.getName(),
					customer.getPhoneNumber(),
					customer.getIdentityCard(),
					customer.getGender(),
					customer.getStreet() + ", " + customer.getAddress3() + ", " + customer.getAddress2() +", " + customer.getAddress1()
			});
		});
	}
	
	private void initComp() {
		btnAdd.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				Long customerId = Long.valueOf(tbl.getValueAt(tbl.getSelectedRow(), 0).toString());
				selectedCustomer = customerController.getById(customerId);
				dialog.dispose();
			}
		});
		
		btnCancel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				selectedCustomer = null;
				dialog.dispose();
			}
		});
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(scroller)
				.addGroup(layout.createSequentialGroup()
						.addComponent(btnAdd)
						.addComponent(btnCancel)));
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(scroller)
				.addGroup(layout.createParallelGroup()
						.addComponent(btnAdd)
						.addComponent(btnCancel)));
		
		pnl.setLayout(layout);
		dialog.add(pnl);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
	}
	
	public Optional<Customer> addCustomerToTouristGroup(){
		return selectedCustomer!= null ? Optional.of(selectedCustomer) : Optional.empty();
	}
}
