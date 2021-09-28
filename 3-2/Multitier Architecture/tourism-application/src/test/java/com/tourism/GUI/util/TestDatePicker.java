package com.tourism.GUI.util;

import java.awt.Button;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;

import junit.framework.TestCase;

public class TestDatePicker extends TestCase {
	public TestDatePicker(String name) {
		super(name);
	}
	
	public void testDatePicker() {
		new TestFrame();
	}
}

class TestFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btn;
	DatePicker datePicker;
	public TestFrame() {
		init();
		this.add(btn);
	}
	public void init() {
		btn = new JButton("pick date");
		btn.setSize(new Dimension(100,100));
		//---------------
		datePicker = new DatePicker(btn);
		System.out.println( datePicker.setPickedDateYearMonthDate());
		//--------------
		setSize(300,300);
		setVisible(true);
	}
}