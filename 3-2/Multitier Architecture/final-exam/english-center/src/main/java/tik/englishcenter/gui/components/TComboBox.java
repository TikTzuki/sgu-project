package tik.englishcenter.gui.components;

import javax.swing.JComboBox;

public class TComboBox<T> extends JComboBox<TComboBoxItem<T>> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public T getSelectedObject() {
        TComboBoxItem<T> obj = (TComboBoxItem<T>) super.getSelectedItem();
        if (obj != null)
            return obj.getObject();
        return null;
    }
}
