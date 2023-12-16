package tiktzuki.e_store.GUI.customcomponents;

import javax.swing.JLabel;

import tiktzuki.e_store.services.Formatter;

public class TCurrencyLabel extends JLabel {
    public TCurrencyLabel() {
        super();
    }

    public TCurrencyLabel(String title) {
        super(title);
    }

    @Override
    public void setText(String text) {
        super.setText(Formatter.currencyFormat(text));
    }
}
