package tiktzuki.e_store.GUI.frames.device;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class TestFrame extends JFrame {
	public TestFrame(JPanel pnl) {
		add(pnl);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
	}
}