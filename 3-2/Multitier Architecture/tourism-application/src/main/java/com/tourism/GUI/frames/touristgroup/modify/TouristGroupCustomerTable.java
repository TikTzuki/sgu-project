package com.tourism.GUI.frames.touristgroup.modify;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.tourism.BUS.CustomerController;
import com.tourism.BUS.TouristGroupController;
import com.tourism.DTO.Customer;
import com.tourism.DTO.TouristGroup;
import com.tourism.GUI.CustomTable;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.touristgroup.TouristGroupMainPanel;
import com.tourism.GUI.util.ConfirmDialog;
import com.tourism.GUI.util.MessageDialog;

public class TouristGroupCustomerTable extends JPanel {
	private static final long serialVersionUID = 1L;
	TouristGroupController touristGroupController = new TouristGroupController();
	CustomerController customerController = new CustomerController();
	
	GroupLayout layout;
	JLabel lblCustomerList;
	
	JButton btnAdd;
	
	JPanel pnlSelectedCustomer;
	JLabel lblSelectedCustomer;
	JLabel lblSelectedCustomerId;
	JButton btnRemove;
	
	JScrollPane scroller;
	JTable tbl;
	DefaultTableModel model;
	TouristGroup TG;
	public TouristGroupCustomerTable() {
		TG = TouristGroupMainPanel.selectedTouristGroup;
		initData();
		initComp();
	}
	
	public void initData() {
		layout  = new GroupLayout(this);
		model = new DefaultTableModel(new Object[] {"Mã", "Tên", "Số điện thoại"}, 0);
		
		lblCustomerList = new JLabel("Danh sách khách hàng");
		
		btnAdd = new JButton(Resources.ADD_ICON);
		
		pnlSelectedCustomer = new JPanel();
		lblSelectedCustomer = new JLabel("Khách hàng");
		lblSelectedCustomerId = new JLabel();
		btnRemove = new JButton("Xóa");
		
		tbl = new CustomTable(model);	
		scroller = new JScrollPane(tbl);
		
	}
	
	public void initComp() {
		btnAdd.setBackground(Resources.PRIMARY_DARK);
		btnAdd.setPreferredSize(Resources.SQUARE_XXS);
		btnAdd.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				Optional<Customer> opt = new AddCustomerToTouristGroupDialog(TG).addCustomerToTouristGroup();
				opt.ifPresent(customer -> {
					for(Customer obj: TG.getCustomers()) {
						if(obj.getId() == customer.getId()) {
							new MessageDialog("Khách hàng đã có trong danh sách");
							return;
						}
					}
					TG.getCustomers().add(customer);
				});
				loadTable();
			}
		});
		
		pnlSelectedCustomer.add(lblSelectedCustomer);
		pnlSelectedCustomer.add(lblSelectedCustomerId);
		pnlSelectedCustomer.setBackground(Resources.PRIMARY);
		
		btnRemove.setBackground(Resources.PRIMARY_DARK);
		btnRemove.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				if(new ConfirmDialog("Xóa khách hàng khỏi danh sách").confirm()) {
					Long customerId = Long.valueOf(lblSelectedCustomerId.getText());
					TouristGroupMainPanel.selectedTouristGroup.getCustomers().removeIf(
							customer->(customer.getId()==customerId));
					loadTable();
				}
			}
		});
		
		loadTable();
		tbl.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				String customerId = tbl.getValueAt(tbl.getSelectedRow(), 0).toString();
				lblSelectedCustomerId.setText(customerId);
			}
		});
		tbl.setBackground(Resources.PRIMARY);
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(lblCustomerList))
				.addGroup(layout.createSequentialGroup()
						.addComponent(btnAdd, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS)
						.addContainerGap()
						.addComponent(pnlSelectedCustomer)
						.addComponent(btnRemove))
				.addComponent(scroller));
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(lblCustomerList)
				.addGroup(layout.createParallelGroup()
						.addComponent(btnAdd, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS)
						.addComponent(pnlSelectedCustomer)
						.addComponent(btnRemove))
				.addComponent(scroller));
		this.setLayout(layout);
		this.setBackground(Resources.PRIMARY);
	}
	
	private void loadTable() {
		model.setRowCount(0);
		if(TouristGroupMainPanel.selectedTouristGroup.getCustomers()!=null)
			TouristGroupMainPanel.selectedTouristGroup.getCustomers().forEach(customer ->{
				model.addRow(new Object[] {
						customer.getId(),
						customer.getName(),
						customer.getPhoneNumber()
				});
			});
	}
}
