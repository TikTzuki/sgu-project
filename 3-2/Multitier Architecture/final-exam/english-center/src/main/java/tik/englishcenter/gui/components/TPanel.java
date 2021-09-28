package tik.englishcenter.gui.components;

import tik.englishcenter.gui.Resources;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TPanel extends JPanel {
    public TPanel() {
        this.setBackground(Resources.C_LIGHT);
        this.setLayout(null);
    }

    public TPanel(LayoutManager layoutManager) {
        super(layoutManager);
    }

    public TPanel(String title) {
        Border blackline = BorderFactory.createLineBorder(Resources.C_PRIMARY_DARK);
        TitledBorder titleBorder = BorderFactory.createTitledBorder(blackline, title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP);
        this.setBorder(titleBorder);
        this.setBackground(Resources.C_LIGHT);
        this.setLayout(null);
    }

    public void init() {
    }

    public Component add(TPanel comp) {
        comp.init();
        return super.add(comp);
    }
}
