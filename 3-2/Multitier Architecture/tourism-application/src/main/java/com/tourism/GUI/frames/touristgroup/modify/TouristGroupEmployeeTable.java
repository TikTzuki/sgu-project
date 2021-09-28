package com.tourism.GUI.frames.touristgroup.modify;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.tourism.BUS.PositionController;
import com.tourism.BUS.TouristGroupController;
import com.tourism.DTO.TourPosition;
import com.tourism.DTO.TouristGroup;
import com.tourism.GUI.CustomTable;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.touristgroup.TouristGroupMainPanel;
import com.tourism.GUI.util.ConfirmDialog;
import com.tourism.GUI.util.MessageDialog;

public class TouristGroupEmployeeTable extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PositionController positionController = new PositionController();
	TouristGroupController touristGroupController = new TouristGroupController();
	TouristGroup TG;
	
	GroupLayout layout;
	JLabel lblEmployeeList;
	
	JComboBox<String> cbxPosition = new JComboBox<String>(); 
	JButton btnAdd;
	
	JPanel pnlSelectedEmployee;
	JLabel lblSelectedEmployee;
	JLabel lblSelectedEmployeeId;
	JLabel lblSelectedPosition;
	JLabel lblSelectedPositionId;
	
	JButton btnRemove;
	
	JScrollPane scroller;
	JTable tbl;
	DefaultTableModel model;
	
	public TouristGroupEmployeeTable() {
		TG = TouristGroupMainPanel.selectedTouristGroup;
		initData();
		initComp();
	}
	
	public void initData() {
		layout  = new GroupLayout(this);
		model = new DefaultTableModel(new Object[] {"Mã", "Tên", "Sđt", "#"}, 0);
		
		lblEmployeeList = new JLabel("Danh sách nhân viên");
		
		btnAdd = new JButton(Resources.ADD_ICON);
		
		pnlSelectedEmployee = new JPanel();
		lblSelectedEmployee = new JLabel("Nhân viên");
		lblSelectedEmployeeId = new JLabel();
		lblSelectedPosition = new JLabel("Vị trí");
		lblSelectedPositionId = new JLabel();
		
		btnRemove = new JButton("Xóa");
		
		tbl = new CustomTable(model);	
		scroller = new JScrollPane(tbl);
		
		cbxPosition.addItem("0. Tất cả");
		positionController.getAll().forEach(position->{
			cbxPosition.addItem(position.getId() +". " + position.getName());
		});
	}
	
	public void initComp() {
		btnAdd.setBackground(Resources.PRIMARY_DARK);
		btnAdd.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				Optional<TourPosition> opt = new AddEmployeeToTouristGroupDialog(TG).addEmployeeToTouristGroup();
				opt.ifPresent(tourPosition -> {
					for(TourPosition obj: TG.getTourPositions()) {
						if(tourPosition.getEmployeeId() == obj.getEmployeeId() && tourPosition.getPositionId() == obj.getPositionId()) {
							new MessageDialog("Nhân viên đã có trong danh sách");
							return;
						}
					}
						TG.getTourPositions().add(tourPosition);
				});
				loadTable();
			}
		});
		
		cbxPosition.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				loadTable();
			}
		});
		
		pnlSelectedEmployee.add(lblSelectedEmployee);
		pnlSelectedEmployee.add(lblSelectedEmployeeId);
		pnlSelectedEmployee.add(lblSelectedPosition);
		pnlSelectedEmployee.add(lblSelectedPositionId);
		pnlSelectedEmployee.add(btnRemove);
		pnlSelectedEmployee.setBackground(Resources.PRIMARY);
		
		btnRemove.setBackground(Resources.PRIMARY_DARK);
		btnRemove.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				if(new ConfirmDialog("Xóa nhân viên khỏi danh sách?").confirm()) {
					Long employeeId = Long.valueOf(lblSelectedEmployeeId.getText());
					Long positionId= Long.valueOf(lblSelectedPositionId.getText());
					TouristGroupMainPanel.selectedTouristGroup.getTourPositions().removeIf(
							tourPosition->(
							tourPosition.getEmployeeId() == employeeId 
							&& tourPosition.getPositionId() == positionId));
					loadTable();
				}
			}
				
		});
		
		loadTable();
		tbl.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				String empId = tbl.getValueAt(tbl.getSelectedRow(), 0).toString();
				lblSelectedEmployeeId.setText(empId);
				String positionId = tbl.getValueAt(tbl.getSelectedRow(), 3).toString();
				lblSelectedPositionId.setText(positionId);
			}
		});
		tbl.setBackground(Resources.PRIMARY);
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(lblEmployeeList))
				.addGroup(layout.createSequentialGroup()
						.addComponent(btnAdd, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS)
						.addComponent(cbxPosition)
						.addComponent(pnlSelectedEmployee))
				.addComponent(scroller));
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(lblEmployeeList)
				.addGroup(layout.createParallelGroup()
						.addComponent(btnAdd, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS)
						.addComponent(cbxPosition)
						.addComponent(pnlSelectedEmployee))
				.addComponent(scroller));
		this.setLayout(layout);
		this.setBackground(Resources.PRIMARY);
	}
	
	private void loadTable() {
		String selectedString = cbxPosition.getSelectedItem().toString();
		Long positionId = Long.valueOf(selectedString.substring(0, selectedString.indexOf(".")));
		model.setRowCount(0);
		if(TouristGroupMainPanel.selectedTouristGroup.getTourPositions() != null)
			touristGroupController.groupAllTourPositionByTourPositionId(TouristGroupMainPanel.selectedTouristGroup, positionId).forEach(tourPosition ->{
				model.addRow(new Object[] {
						tourPosition.getEmployeeId(), 
						tourPosition.getEmployee().getName(), 
						tourPosition.getEmployee().getPhoneNumber(), 
						tourPosition.getPositionId()
				});
			}); 
	}
}
