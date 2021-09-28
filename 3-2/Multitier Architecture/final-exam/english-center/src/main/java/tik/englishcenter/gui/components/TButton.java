package tik.englishcenter.gui.components;

import tik.englishcenter.gui.Resources;

import javax.swing.Icon;
import javax.swing.JButton;


public class TButton extends JButton{
	public TButton() {
		this.setBackground(Resources.C_LIGHT);
		this.setForeground(Resources.C_DARK);
	}

	public TButton(Icon icon) {
		super(icon);
		this.setBackground(Resources.C_LIGHT);
		this.setForeground(Resources.C_DARK);
	}
	
	public TButton(String title) {
		super(title);
		this.setBackground(Resources.C_LIGHT);
		this.setForeground(Resources.C_DARK);
	}
}
