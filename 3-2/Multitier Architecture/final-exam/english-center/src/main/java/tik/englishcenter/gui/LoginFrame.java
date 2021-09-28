package tik.englishcenter.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tik.englishcenter.gui.components.MessageDialog;
import tik.englishcenter.models.Manager;
import tik.englishcenter.services.ManagerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Controller
public class LoginFrame extends JFrame {
    @Autowired
    ManagerService managerService;

    JLabel lblLogin;
    JLabel lblBackground;

    JTextField txtUserName;

    JPasswordField pwfPassword;

    JButton btnLogin;
    JPanel pnlContent;

    @Autowired
    MainFrame mainFrame;

    public LoginFrame() {
    }

    public void init() {
        initData();
        initComp();
//        TODO: remove this
//        this.dispose();
//        mainFrame.init();
    }

    public void initData() {
        lblLogin = new JLabel("Đăng nhập", JLabel.CENTER);
        lblBackground = new JLabel(Resources.LOGIN_BACKGROUND);

        txtUserName = new JTextField("manager");

        pwfPassword = new JPasswordField("123");
        btnLogin = new JButton("Đăng nhập");
        pnlContent = new JPanel(null);
    }

    public void initComp() {
        lblLogin.setBounds(0, 50, 300, 50);
        lblLogin.setFont(new Font("Arial", Font.BOLD, 30));
        lblLogin.setForeground(Resources.C_SECONDARY);
        pnlContent.add(lblLogin);

        txtUserName.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Resources.C_SECONDARY), "Tên đăng nhập", 0, 1));
        txtUserName.setBounds(50, 150, 200, 50);
        txtUserName.setForeground(Resources.C_SECONDARY);
        txtUserName.setBackground(Resources.C_LIGHT);
        pnlContent.add(txtUserName);

        pwfPassword.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Resources.C_SECONDARY), "Mật khẩu", 0, 1));
        pwfPassword.setBounds(50, 250, 200, 50);
        pwfPassword.setForeground(Resources.C_SECONDARY);
        pwfPassword.setBackground(Resources.C_LIGHT);
        pnlContent.add(pwfPassword);

        btnLogin.setBounds(70, 370, 150, 45);
        btnLogin.setBackground(Resources.C_PRIMARY);
        btnLogin.setForeground(Resources.C_LIGHT);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 20));
        btnLogin.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                login();
            }
        });
        pnlContent.add(btnLogin);

        lblBackground.setBounds(0, 0, 300, 500);
        pnlContent.add(lblBackground);
        lblBackground.setBounds(0, 0, 300, 500);
        pnlContent.setPreferredSize(new Dimension(300, 500));
        this.setContentPane(pnlContent);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void login() {
        Manager user = managerService.login(txtUserName.getText(), pwfPassword.getPassword());
        if (user == null) {
            new MessageDialog("Thông tin đăng nhập sai");
        } else {
            this.dispose();
            mainFrame.init();
        }
    }

    public static void main(String[] args) {
        LoginFrame loginFrame = new LoginFrame();
    }
}