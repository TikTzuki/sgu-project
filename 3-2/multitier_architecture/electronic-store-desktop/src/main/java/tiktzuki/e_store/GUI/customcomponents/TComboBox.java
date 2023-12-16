package tiktzuki.e_store.GUI.customcomponents;

import javax.swing.JComboBox;

public class TComboBox<T> extends JComboBox<TComboBoxItem<T>> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public T getSelectedObject() {
        TComboBoxItem<T> obj = (TComboBoxItem<T>) super.getSelectedItem();
        return obj.getObject();
    }
}
