package tik.englishcenter.gui;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tik.englishcenter.gui.components.TPanel;
import tik.englishcenter.gui.frames.CreateExamineePanel;
import tik.englishcenter.gui.frames.ExaminationPanel;
import tik.englishcenter.gui.frames.RoomPanel;
import tik.englishcenter.repositories.ManagerRepository;
import tik.englishcenter.services.ManagerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Controller
public class MainFrame extends JFrame {
    @Autowired
    ManagerService managerService;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    ExaminationPanel examinationPanel;

    @Autowired
    CreateExamineePanel createExamineePanel;

    @Autowired
    RoomPanel roomPanel;

    public enum Action {
        EXAMINATION,
        ADD_EXAMINEE,
        ROOM
    }

    //    Top menu
    private JPanel pnlTopBar;
    //    Left side menu
    private JPanel pnlLeftSideMenu;
    static JLabel functionSelected;
    String titleMenuItems[];
    ImageIcon iconMenuItems[];
    static MenuItem pnlMenuItems[];

    // Array Jpanel nội dung chính
    static TPanel[] pnlMainContents;
    static TPanel pnlMainContentWrapper;


    public void init() {
        managerService.CURRENT_USER = managerRepository.findById(1).get();
        initData();
        initComp();
    }

    private void initData() {
        // Left side menu
        pnlLeftSideMenu = new JPanel(new FlowLayout(FlowLayout.CENTER, -2, -2));
        pnlTopBar = new JPanel(null);
        functionSelected = new JLabel("--------\\/--------");

        titleMenuItems = new String[]{"Examination", "Add Examinees", "Room"};
        iconMenuItems = new ImageIcon[]{Resources.CATALOG, Resources.DEVICE, Resources.ORDER};
        pnlMenuItems = new MenuItem[titleMenuItems.length];
        // Array Jpanel nội dung chính
        pnlMainContents = new TPanel[]{examinationPanel, createExamineePanel, roomPanel};
        pnlMainContentWrapper = new TPanel(new GridLayout());
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
        JLabel lblUser = new JLabel("Hello, " + managerService.CURRENT_USER.getUsername());
        lblUser.setBounds(1150, 0, 200, 50);
        // Btn Order Preview

        pnlTopBar.add(lblLogo);
        pnlTopBar.add(functionSelected);
        pnlTopBar.add(lblUser);
        pnlTopBar.setBackground(Resources.C_PRIMARY_DARK);
        pnlTopBar.setPreferredSize(new Dimension(1100, 50));

        this.add(pnlLeftSideMenu, BorderLayout.WEST);
        this.add(pnlTopBar, BorderLayout.NORTH);
        // Main content

        pnlMainContentWrapper.add(pnlMainContents[0]);

        this.add(pnlMainContentWrapper, BorderLayout.CENTER);
        this.setResizable(false);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void switchPanel(int selectPanelIndex) {

        for (int i = 0; i < pnlMenuItems.length; i++) {
            if (i == selectPanelIndex) {
                pnlMenuItems[i].setSelectedState();
                functionSelected.setText(pnlMenuItems[i].getTitle());
                continue;
            }
            pnlMenuItems[i].setUnselectedState();
        }

        pnlMainContentWrapper.removeAll();
        pnlMainContentWrapper.add(pnlMainContents[selectPanelIndex]);
        pnlMainContentWrapper.revalidate();
        pnlMainContentWrapper.getParent().repaint();
    }


    public void dispatch(Action action) {
        switch (action) {
            case EXAMINATION: {
                switchPanel(0);
                break;
            }
            case ADD_EXAMINEE: {
                switchPanel(1);
                break;
            }
            case ROOM: {
                switchPanel(2);
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + action);
        }
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