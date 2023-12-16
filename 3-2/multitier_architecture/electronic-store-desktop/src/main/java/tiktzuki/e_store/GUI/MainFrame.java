package tiktzuki.e_store.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lombok.Data;
import tiktzuki.e_store.DAL.EmployeeRepository;
import tiktzuki.e_store.DTO.Employee;
import tiktzuki.e_store.DTO.Staff;
import tiktzuki.e_store.GUI.customcomponents.FindOrderDialog;
import tiktzuki.e_store.GUI.customcomponents.OrderPreviewDialog;
import tiktzuki.e_store.GUI.customcomponents.TButton;
import tiktzuki.e_store.GUI.frames.catalog.CatalogMainPanel;
import tiktzuki.e_store.GUI.frames.createorder.CreateOrderMainPanel;
import tiktzuki.e_store.GUI.frames.customer.CustomerMainPanel;
import tiktzuki.e_store.GUI.frames.device.AddDevice1;
import tiktzuki.e_store.GUI.frames.device.DeviceMainPanel;
import tiktzuki.e_store.GUI.frames.employee.EmployeeMainPanel;
import tiktzuki.e_store.GUI.frames.order.OrderManagerMainPanel;
import tiktzuki.e_store.GUI.frames.receiving.ReceivingMainPanel;
import tiktzuki.e_store.GUI.frames.shippingnote.ShippingNoteMainPanel;

public class MainFrame extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public enum Action {
        CATALOG,
        DEVICE_MANAGER,
        CREATE_ORDER,
        ORDER_MANAGER,
        SHIPPING_NOTE_MANAGER,
        RECEIVING_MANAGER
    }

    EmployeeRepository empRepos = new EmployeeRepository();
    public static Employee CURRENT_USER;
    // Top menu
    private JPanel pnlTopBar;
    OrderPreviewDialog orderPreviewDialog = new OrderPreviewDialog();
    FindOrderDialog orderDialog = new FindOrderDialog();
    // Left side menu
    private JPanel pnlLeftSideMenu;
    static JLabel functionSelected;
    String titleMenuItems[];
    ImageIcon iconMenuItems[];
    static MenuItem pnlMenuItems[];

    // Array Jpanel nội dung chính
    static JPanel[] pnlMainContents;
    public static JPanel pnlCatalog;
    public static JPanel pnlDeviceManager;
    public static JPanel pnlCreateOrder;
    public static JPanel pnlOrderManager;
    public static JPanel pnlShippingNoteManager;
    public static JPanel pnlReceivingManager;
    public static JPanel pnlCustomerManager;
    public static JPanel pnlEmployeeManager;
    public static JPanel pnlAddDevice;
    static JPanel layeredContent;


    public MainFrame() {
        super();
        CURRENT_USER = empRepos.findById(1).get();
        initData();
        initComp();
    }

    public MainFrame(Employee staff) {
        super();
        CURRENT_USER = staff;
        initData();
        initComp();
    }

    private void initData() {
        // Left side menu
        pnlLeftSideMenu = new JPanel(new FlowLayout(FlowLayout.CENTER, -2, -2));
        pnlTopBar = new JPanel(null);
        functionSelected = new JLabel("--------\\/--------");

        titleMenuItems = new String[]{"CATALOG", "Thiết bị", "Đặt hàng", "Đơn hàng", "Phiếu giao hàng", "Nhập", "Quản lý nhân viên",
                "Quản lý khách hàng"};
        iconMenuItems = new ImageIcon[]{Resources.CATALOG, Resources.DEVICE, Resources.ORDER, Resources.ORDER, Resources.BOX,
                Resources.RECEIVING_NOTE, Resources.STAFF, Resources.CUSTOMER};
        pnlMenuItems = new MenuItem[titleMenuItems.length];
        // Array Jpanel nội dung chính
        pnlCatalog = new CatalogMainPanel();
        pnlDeviceManager = new DeviceMainPanel();
        pnlCreateOrder = new CreateOrderMainPanel();
        pnlOrderManager = new OrderManagerMainPanel();
        pnlShippingNoteManager = new ShippingNoteMainPanel();
        pnlReceivingManager = new ReceivingMainPanel();
        pnlEmployeeManager = new EmployeeMainPanel();
        pnlCustomerManager = new CustomerMainPanel();
        pnlMainContents = new JPanel[]{pnlCatalog, pnlDeviceManager, pnlCreateOrder, pnlOrderManager, pnlShippingNoteManager, pnlReceivingManager,
                pnlEmployeeManager, pnlCustomerManager};
        layeredContent = new JPanel();
    }

    private void initComp() {
        setLayout(new BorderLayout());

        // Left side menu
        pnlLeftSideMenu.setPreferredSize(new Dimension(200, 600));
        pnlLeftSideMenu.setBackground(Resources.C_PRIMARY_DARK);
        Dimension dimensionMenuItem = new Dimension(204, 50);

        for (int i = 0; i < titleMenuItems.length; i++) {
            pnlMenuItems[i] = new MenuItem(titleMenuItems[i], iconMenuItems[i]);
            pnlMenuItems[i].setBackground(Resources.C_PRIMARY_DARK);
            pnlMenuItems[i].setPreferredSize(dimensionMenuItem);
            int selectedPanelIndex = i;
            pnlMenuItems[i].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    switchPanel(selectedPanelIndex);
                }
            });
            pnlLeftSideMenu.add(pnlMenuItems[i]);
        }
        // Top mennu
        ImageIcon iconApp = Resources.DEVICE;
        JLabel lblLogo = new JLabel(iconApp);
        lblLogo.setBounds(20, 0, 160, 50);

        // Selected
        functionSelected = new JLabel("--------\\\\/--------");
        functionSelected.setFont(Resources.F_H3Regular);
        functionSelected.setForeground(Resources.C_SECONDARY);
        functionSelected.setBounds(650, 0, 300, 50);
        // User
        JLabel lblUser = new JLabel("Hello, " + CURRENT_USER.getName());
        lblUser.setBounds(1150, 0, 200, 50);
        // Btn Order Preview
        TButton btnOrderPreview = new TButton(Resources.MENU_ICON);
        btnOrderPreview.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                OrderPreviewDialog.dispatch(OrderPreviewDialog.Action.DISPLAY);
            }
        });
        btnOrderPreview.setBounds(1000, 0, 50, 50);

        pnlTopBar.add(lblLogo);
        pnlTopBar.add(functionSelected);
        pnlTopBar.add(lblUser);
        pnlTopBar.add(btnOrderPreview);
        pnlTopBar.setBackground(Resources.C_PRIMARY_DARK);
        pnlTopBar.setPreferredSize(new Dimension(1100, 50));

        this.add(pnlLeftSideMenu, BorderLayout.WEST);
        this.add(pnlTopBar, BorderLayout.NORTH);
        // Main content
        layeredContent.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        layeredContent.setPreferredSize(new Dimension(1110, 700));

//		for (int i = 0; i < pnlMainContents.length; i++) {
//			this.layeredContent.add(pnlMainContents[i], pnlMainContents.length - i, 0);
//		}

        this.layeredContent.add(pnlMainContents[0]);
        this.setResizable(false);
        this.add(layeredContent, BorderLayout.CENTER);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void switchPanel(int selectPanelIndex) {

        for (int i = 0; i < pnlMenuItems.length; i++) {
            if (i == selectPanelIndex) {
                pnlMenuItems[i].setSelectedState();
                functionSelected.setText(pnlMenuItems[i].getTitle());
                continue;
            }
            pnlMenuItems[i].setUnselectedState();
        }

        layeredContent.removeAll();
        layeredContent.add(createNewInstance(pnlMainContents[selectPanelIndex].getClass()));
        layeredContent.revalidate();
        layeredContent.getParent().repaint();
    }

    private static Component createNewInstance(Class<?> primary) {
        try {
            return (Component) primary.getDeclaredConstructor().newInstance();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return new JPanel();
    }

    public static void dispatch(Action action) {
        switch (action) {
            case CATALOG: {

                break;
            }
            case CREATE_ORDER: {
                switchPanel(2);
                break;
            }
            case ORDER_MANAGER: {
                switchPanel(3);
                break;
            }
            case DEVICE_MANAGER: {

                break;
            }
            case RECEIVING_MANAGER: {

                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + action);
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}

@Data
class MenuItem extends JPanel {
    String title;
    ImageIcon icon;
    JLabel label;

    public MenuItem(String title, ImageIcon icon) {
        super(new GridLayout());
        this.title = title;
        this.icon = icon;
        initComp();
    }

    public void initComp() {
        label = new JLabel(this.title, this.icon, JLabel.CENTER);
        label.setForeground(Resources.C_SECONDARY);
        this.add(label);
    }

    public void setSelectedState() {
        label.setForeground(Resources.C_SECONDARY);
    }

    public void setUnselectedState() {
        label.setForeground(Resources.C_SECONDARY);
    }
}