package tik.englishcenter.gui.components;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import lombok.Data;
import tik.englishcenter.gui.Resources;

@Data
public class TFormControl<T> {
	public static final int WIDTH_S = 100;
	T component;
	TButton btnDate;
	JPanel pnl;
	String title;
	
	public TFormControl(JComponent comp, String title) {
		Border blackline = BorderFactory.createLineBorder(Resources.C_PRIMARY_DARK);
		TitledBorder titleBorder = BorderFactory.createTitledBorder(blackline, title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP);
		comp.setPreferredSize(Resources.TXT_SEARCH);
		this.component= (T) comp;
		pnl = new JPanel();
		pnl.setBackground(Resources.C_LIGHT);
		pnl.setBorder(titleBorder);
		pnl.add(comp);
	}

	public TFormControl(JComponent comp, String title, int width) {
		Border blackline = BorderFactory.createLineBorder(Resources.C_PRIMARY_DARK);
		TitledBorder titleBorder = BorderFactory.createTitledBorder(blackline, title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP);
		comp.setPreferredSize(new Dimension(width, Resources.EDGE_XXXS));
		this.component= (T) comp;
		pnl = new JPanel();
		pnl.setBackground(Resources.C_LIGHT);
		pnl.setBorder(titleBorder);
		pnl.add(comp);
	}	
	public TFormControl(JTextField comp, String title, boolean hasDatePicker) {
		this.component= (T) comp;
		pnl = new JPanel();
		Border blackline = BorderFactory.createLineBorder(Resources.C_PRIMARY_DARK);
		TitledBorder titleBorder = BorderFactory.createTitledBorder(blackline, title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP);
		if(hasDatePicker) {
			comp.setPreferredSize(Resources.TXT_SEARCH_DATE);
			btnDate = new TButton(Resources.CALENDAR_ICON);
			btnDate.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					comp.setText(new DatePicker().setPickedDate());
				};
			});
		} else {
			comp.setPreferredSize(Resources.TXT_SEARCH);
		}
		pnl.setBorder(titleBorder);
		pnl.setBackground(Resources.C_LIGHT);
		pnl.add(comp);
		pnl.add(btnDate);
	}
}