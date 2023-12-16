package tiktzuki.e_store.GUI.frames.catalog;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import tiktzuki.e_store.GUI.Resources;

public class CatalogContentPanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CatalogContentPanel(List<CatalogItem> items) {
        super();
        initComp();
        loadPagedFilteredItem(items);
    }

    private void initComp() {
        this.setPreferredSize(new Dimension(1050, 700));
    }

    public void loadPagedFilteredItem(List<CatalogItem> items) {
        this.removeAll();
        items.forEach(catalogItem -> {
            this.add(catalogItem);
        });
        if (this.getParent() != null) {
            this.getParent().revalidate();
            this.repaint();
        }
    }
}
