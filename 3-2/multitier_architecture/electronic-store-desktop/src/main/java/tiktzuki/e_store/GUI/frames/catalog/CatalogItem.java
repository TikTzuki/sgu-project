package tiktzuki.e_store.GUI.frames.catalog;

import javax.swing.JPanel;
import javax.swing.JTextField;

import tiktzuki.e_store.DTO.Combo;
import tiktzuki.e_store.DTO.Device;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.customcomponents.TButton;
import tiktzuki.e_store.services.Formatter;
import tiktzuki.e_store.services.ImageUtils;
import tiktzuki.e_store.services.LoadingImageService;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class CatalogItem extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Logger logger = Logger.getLogger(getClass().getName());
    Device device;
    JLabel lblImage;
    JPanel pnlImageWrapper;
    JLabel lblName;
    JLabel lblPrice;
    JTextField txtQuantity;
    TButton btnAddToOrderPreview;

    public CatalogItem(Device device) {
        super();
        this.device = device;
        loadData();
        loadComp();
    }

    public CatalogItem(Combo combo) {
        super();
        loadData(combo);
        loadComp();
    }

    private void loadData() {
        lblImage = new JLabel();
        new LoadingImageService(lblImage, Resources.UPLOAD_PATH + device.getImage()).execute();
        pnlImageWrapper = new JPanel(null);

        lblName = new JLabel(
                String.format("<html><body style=\"text-align: center;\">%s</body></html>", device.getName()));
        lblPrice = new JLabel(Formatter.currencyFormat(device.getPrice()));
        txtQuantity = new JTextField("1");
        btnAddToOrderPreview = new TButton(Resources.ADD_ICON);
    }

    private void loadData(Combo combo) {
        lblImage = new JLabel(ImageUtils.loadImage(Resources.UPLOAD_PATH + combo.getImage(), 200, 200));
        pnlImageWrapper = new JPanel(null);
        lblName = new JLabel(
                String.format("<html><body style=\"text-align: center;\">%s</body></html>", combo.getComboName()));
        lblPrice = new JLabel("-" + Formatter.currencyFormat(combo.getDiscountValue()));
    }

    private void loadComp() {
        pnlImageWrapper.setBounds(0, 0, CARD_WIDTH, CARD_WRAPPER_HEIGHT);
        pnlImageWrapper.setPreferredSize(IMAGE_WRAPPER);
        lblImage.setBounds(0, 0, CARD_WIDTH, CARD_WRAPPER_HEIGHT);

        pnlImageWrapper.setBackground(Resources.C_WHITE);
        pnlImageWrapper.add(lblImage);

        lblName.setPreferredSize(NAME_SIZE);
        lblName.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                logger.info("clicked");
            }
        });
        this.add(pnlImageWrapper);
        this.add(lblName);
        this.add(lblPrice);
        if (txtQuantity != null) {
            txtQuantity.setPreferredSize(new Dimension(60, 20));
            txtQuantity.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    txtQuantity.setText(txtQuantity.getText().replaceAll("(?!\\d).+", ""));
                }
            });
            this.add(txtQuantity);
        }
        if (btnAddToOrderPreview != null) {
            btnAddToOrderPreview.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    CatalogMainPanel.orderCreateController.addOrderItem(device, Integer.valueOf(txtQuantity.getText()));
                }
            });
            this.add(btnAddToOrderPreview);
        }
        this.setBackground(Resources.C_WHITE);
        this.setPreferredSize(CARD_SIZE);
    }

    private static int CARD_WIDTH = 200;
    private static int CARD_HEIGHT = 300;
    private static int CARD_WRAPPER_HEIGHT = 200;
    private static Dimension IMAGE_WRAPPER = new Dimension(CARD_WIDTH, CARD_WRAPPER_HEIGHT);
    private static Dimension CARD_SIZE = new Dimension(CARD_WIDTH, CARD_HEIGHT);
    private static Dimension NAME_SIZE = new Dimension(CARD_WIDTH, 60);
}
