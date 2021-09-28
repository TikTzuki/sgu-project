package tik.englishcenter.gui.components;

import tik.englishcenter.gui.Resources;

import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class TBorderPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TBorderPanel(String title) {
		Border blackline = BorderFactory.createLineBorder(Resources.C_PRIMARY_DARK);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(blackline, title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP);
		this.setBorder(titledBorder);
		this.setBackground(Resources.C_LIGHT);
	}
	public TBorderPanel(String title, LayoutManager layout) {
		super(layout);
		Border blackline = BorderFactory.createLineBorder(Resources.C_PRIMARY_DARK);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(blackline, title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP);
		this.setBorder(titledBorder);
		this.setBackground(Resources.C_LIGHT);
	}
}
