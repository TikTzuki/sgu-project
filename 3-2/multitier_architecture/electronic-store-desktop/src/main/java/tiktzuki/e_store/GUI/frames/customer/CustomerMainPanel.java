package tiktzuki.e_store.GUI.frames.customer;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import tiktzuki.e_store.GUI.Resources;

public class CustomerMainPanel extends JPanel {
    public static JPanel mainContent;

    public CustomerMainPanel() {
        super(new FlowLayout(0, 0, 0));
        mainContent = new JPanel(new FlowLayout(0, 0, 0));
        mainContent.add(new CustomerManagerPanel());
        this.add(mainContent);
    }
}
