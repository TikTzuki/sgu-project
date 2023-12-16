package tiktzuki.e_store.GUI.customcomponents;

import javax.swing.Icon;
import javax.swing.JButton;

import tiktzuki.e_store.GUI.Resources;

public class TButton extends JButton {
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
