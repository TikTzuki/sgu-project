package tiktzuki.e_store.GUI.frames.customer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import tiktzuki.e_store.DAL.CustomerRepository;
import tiktzuki.e_store.DAL.EmployeeRepository;
import tiktzuki.e_store.DAL.RoleRepository;
import tiktzuki.e_store.DTO.Customer;
import tiktzuki.e_store.DTO.Employee;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.customcomponents.TTable;
import tiktzuki.e_store.GUI.frames.device.TestFrame;
import tiktzuki.e_store.GUI.frames.employee.EmployeeManagerPanel;

import javax.swing.JPasswordField;

public class CustomerManagerPanel extends JPanel {
    private JTable table;
    private JTextField txt_timKiem;
    private JButton btn_them;
    private JButton btn_sua;
    DefaultTableModel model;
    private CustomerRepository customerRepository = new CustomerRepository();
    private JPanel panel_2;
    private JLabel lblHTn;
    private JTextField txt_hoten;
    private JTextField txt_taikhoan;
    private JLabel lblTiKhon;
    private JLabel lblMtKhu;
    private JLabel lblaCh_1;
    private JTextField txt_Email;
    private JButton btn_tim;
    private JButton btn_xoa;
    private static SecureRandom random;
    private JTextField txt_matkhau;

    /**
     * Create the panel.
     */
    public CustomerManagerPanel() {
        initComp();
        initData();
    }

    private void initComp() {
        setBackground(Color.WHITE);
        setLayout(null);
        setPreferredSize(new Dimension(1100, 700));
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        panel.setBounds(0, 181, 1100, 124);
        panel.setBackground(Color.WHITE);
        add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Tìm kiếm");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblNewLabel.setBounds(31, 45, 70, 31);
        panel.add(lblNewLabel);

        txt_timKiem = new JTextField();
        txt_timKiem.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txt_timKiem.setBounds(117, 42, 180, 38);
        panel.add(txt_timKiem);
        txt_timKiem.setColumns(10);

        btn_them = new JButton("Thêm khách hàng");
        btn_them.setForeground(new Color(255, 255, 255));
        btn_them.setBackground(Resources.C_PRIMARY);
        btn_them.setFont(new Font("Times New Roman", Font.BOLD, 13));
        btn_them.setBounds(496, 41, 140, 38);
        panel.add(btn_them);

        btn_sua = new JButton("Sửa thông tin");
        btn_sua.setForeground(Color.WHITE);
        btn_sua.setFont(new Font("Times New Roman", Font.BOLD, 13));
        btn_sua.setBackground(Resources.C_PRIMARY);
        btn_sua.setBounds(689, 41, 140, 38);
        panel.add(btn_sua);

        btn_xoa = new JButton("Xóa khách hàng");
        btn_xoa.setForeground(Color.WHITE);
        btn_xoa.setFont(new Font("Times New Roman", Font.BOLD, 13));
        btn_xoa.setBackground(Resources.C_PRIMARY);
        btn_xoa.setBounds(881, 41, 127, 38);
        panel.add(btn_xoa);
        btn_xoa.setEnabled(false);
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.WHITE);
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        panel_1.setBounds(0, 332, 1100, 368);
        add(panel_1);
        panel_1.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 21, 1080, 296);
        panel_1.add(scrollPane);

        table = new TTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                },
                new String[]{
                        "Id", "Email", "T\u00EAn t\u00E0i kho\u1EA3n", "H\u1ECD t\u00EAn"
                }
        ) {
            boolean[] columnEditables = new boolean[]{
                    false, true, true, true
            };

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        scrollPane.setViewportView(table);

        panel_2 = new JPanel();
        panel_2.setBackground(Color.WHITE);
        panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        panel_2.setBounds(0, 0, 1100, 158);
        add(panel_2);
        panel_2.setLayout(null);

        lblHTn = new JLabel("Họ tên:");
        lblHTn.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblHTn.setBounds(222, 27, 61, 38);
        panel_2.add(lblHTn);

        txt_hoten = new JTextField();
        txt_hoten.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txt_hoten.setColumns(10);
        txt_hoten.setBounds(309, 28, 180, 38);
        panel_2.add(txt_hoten);

        txt_taikhoan = new JTextField();
        txt_taikhoan.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txt_taikhoan.setColumns(10);
        txt_taikhoan.setBounds(309, 93, 180, 38);
        panel_2.add(txt_taikhoan);

        lblTiKhon = new JLabel("Tài khoản:");
        lblTiKhon.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblTiKhon.setBounds(222, 92, 77, 38);
        panel_2.add(lblTiKhon);

        lblMtKhu = new JLabel("Mật khẩu:");
        lblMtKhu.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblMtKhu.setBounds(612, 92, 77, 38);
        panel_2.add(lblMtKhu);

        lblaCh_1 = new JLabel("Email:");
        lblaCh_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblaCh_1.setBounds(612, 27, 61, 38);
        panel_2.add(lblaCh_1);

        txt_Email = new JTextField();
        txt_Email.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txt_Email.setColumns(10);
        txt_Email.setBounds(694, 28, 180, 38);
        panel_2.add(txt_Email);

        txt_matkhau = new JTextField();
        txt_matkhau.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txt_matkhau.setColumns(10);
        txt_matkhau.setBounds(694, 93, 180, 38);
        panel_2.add(txt_matkhau);

        btn_sua.setEnabled(false);

        btn_tim = new JButton("Tìm");
        btn_tim.setForeground(Color.WHITE);
        btn_tim.setBackground(Resources.C_PRIMARY);
        btn_tim.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_tim.setBounds(306, 50, 89, 23);
        panel.add(btn_tim);
    }

    private void initData() {
        loadTable();

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Optional<Customer> customer;
                customer = customerRepository.findById((int) table.getValueAt(table.getSelectedRow(), 0));
                txt_Email.setText(customer.get().getEmail());
                txt_hoten.setText(customer.get().getName());
                txt_matkhau.setText(customer.get().getPassword());
                txt_taikhoan.setText(customer.get().getUsername());
                btn_them.setEnabled(false);
                btn_sua.setEnabled(true);
                btn_xoa.setEnabled(true);
                txt_taikhoan.setEnabled(false);
                txt_matkhau.setEditable(false);
            }
        });

        btn_them.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Customer customer = new Customer();
                if (testCommit() == true) {
                    if (customerRepository.findUserName(txt_taikhoan.getText()).equals("")) {
                        customer.setEmail(txt_Email.getText());
                        customer.setName(txt_hoten.getText());
                        customer.setUsername(txt_taikhoan.getText());
                        customer.setPassword(txt_matkhau.getText());
                        customer.setStatus("Active");

                        customerRepository.insert(customer);
                        reset();
                        loadTable();
                    } else {
                        JOptionPane.showMessageDialog(btn_them, "Tài khoản đã tồn tại.");
                    }

                }
            }
        });

        btn_sua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Customer customer = new Customer();
                if (testCommit() == true) {
                    customer.setId((Integer) table.getValueAt(table.getSelectedRow(), 0));
                    customer.setEmail(txt_Email.getText());
                    customer.setName(txt_hoten.getText());
                    customer.setUsername(txt_taikhoan.getText());
                    customer.setPassword(txt_matkhau.getText());
                    customer.setStatus("Active");

                    customerRepository.update(customer);
                    btn_sua.setEnabled(false);
                    btn_xoa.setEnabled(false);
                    btn_them.setEnabled(true);
                    txt_taikhoan.setEnabled(true);
                    txt_matkhau.setEditable(true);

                    reset();
                    loadTable();
                }
            }
        });

        btn_xoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int value = JOptionPane.showConfirmDialog(btn_them, "Bạn chắc chắn muốn xóa?");
                if (value == 0) {
                    customerRepository.updateStatusDeleted((int) table.getValueAt(table.getSelectedRow(), 0));
                    btn_sua.setEnabled(false);
                    btn_xoa.setEnabled(false);
                    btn_them.setEnabled(true);
                    txt_taikhoan.setEnabled(true);
                    txt_matkhau.setEditable(true);

                    loadTable();
                    reset();
                }
            }
        });

        btn_tim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                List<Customer> employees = customerRepository.searchByName(txt_timKiem.getText());
                employees.forEach(customer -> {
                    model.addRow(new Object[]{
                            customer.getId(), customer.getEmail(), customer.getUsername(), customer.getName()
                    });
                });
            }
        });
    }

    private boolean testCommit() {
        if (txt_hoten.getText().equals("")) {
            JOptionPane.showMessageDialog(btn_them, "Họ tên không được để trống");
            return false;
        }
        if (txt_Email.getText().equals("")) {
            JOptionPane.showMessageDialog(btn_them, "Email không được bỏ trống");
            return false;
        }
        if (txt_matkhau.getText().equals("")) {
            JOptionPane.showMessageDialog(btn_them, "Mật khẩu không được bỏ trống");
            return false;
        }
        if (txt_taikhoan.getText().equals("")) {
            JOptionPane.showMessageDialog(btn_them, "Tài khoản không được bỏ trống");
            return false;
        }

        if (!txt_Email.getText().matches("\\w+@\\w+[.]\\w+")) {
            JOptionPane.showMessageDialog(btn_them, "Mail phải đúng định dạng abc@gmail.com");
            return false;
        }
        return true;
    }

    private void reset() {
        txt_Email.setText("");
        txt_hoten.setText("");
        txt_matkhau.setText("");
        txt_taikhoan.setText("");
    }

    private void loadTable() {
        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        customerRepository.findAllNotDeleted().forEach(customer -> {
            model.addRow(new Object[]{
                    customer.getId(), customer.getEmail(), customer.getUsername(), customer.getName()
            });
        });
    }

    public static void main(String[] args) {
        new TestFrame(new CustomerManagerPanel());
    }
}
