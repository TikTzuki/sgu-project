package com.tourism.GUI;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MouseEvent;
import org.w3c.dom.views.AbstractView;

public class CustomTable extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomTable(int numRows, int numColumns) {
		super(numRows, numColumns);
		setColor();
	}

	public CustomTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		setColor();
	}

	public CustomTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
		setColor();
	}

	public CustomTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		setColor();
	}

	public CustomTable(TableModel dm) {
		super(dm);
		setColor();
	}

	public CustomTable(Vector<? extends Vector> rowData, Vector<?> columnNames) {
		super(rowData, columnNames);
		setColor();
	}

	public CustomTable(DefaultTableModel dataModel) {
		super(dataModel);
		setColor();
	}
	
	public CustomTable() {
		setColor();
	}

	
	private void setColor() {
		getTableHeader().setForeground(Resources.SECONDARY_DARK);
		getTableHeader().setBackground(Resources.PRIMARY_DARK);
		setBackground(Resources.PRIMARY);
		setSelectionBackground(Resources.SECONDARY);
		setSelectionForeground(Resources.PRIMARY);
		setGridColor(Resources.SECONDARY_DARK);
	}
	
}
