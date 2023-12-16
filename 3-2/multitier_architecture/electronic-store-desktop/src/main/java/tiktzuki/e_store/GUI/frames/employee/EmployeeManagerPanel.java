package tiktzuki.e_store.GUI.frames.employee;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import tiktzuki.e_store.DAL.EmployeeRepository;
import tiktzuki.e_store.DAL.RoleRepository;
import tiktzuki.e_store.DTO.Employee;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.customcomponents.TTable;
import tiktzuki.e_store.GUI.frames.device.TestFrame;
import tiktzuki.e_store.GUI.frames.receiving.ReceivingManagerPanel;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EmployeeManagerPanel extends JPanel {

    private JTable table;
    private JTextField txt_timKiem;
    private JButton btn_them;
    private JButton btn_sua;
    DefaultTableModel model;
    private EmployeeRepository employeeRepository = new EmployeeRepository();
    private RoleRepository roleRepository = new RoleRepository();
    private JPanel panel_2;
    private JLabel lblHTn;
    private JTextField txt_hoten;
    private JTextField txt_diachi;
    private JLabel lblaCh;
    private JTextField txt_taikhoan;
    private JLabel lblTiKhon;
    private JLabel lblGiiTnh;
    private JLabel lblSinThoi;
    private JLabel lblMtKhu;
    private JTextField txt_sodienthoai;
    private JTextField txt_matKhau;
    private JComboBox cbb_gioitinh;
    private JLabel lblCmnd;
    private JTextField txt_cmnd;
    private JLabel lblaCh_1;
    private JLabel lblChcV;
    private JTextField txt_Email;
    private JComboBox cbb_chucvu;
    private JButton btn_tim;
    private JButton btn_xoa;

    /**
     * Create the panel.
     */
    public EmployeeManagerPanel() {
        initComp();
        initData();
    }

    private void initComp() {
        setBackground(Color.WHITE);
        setLayout(null);
        setPreferredSize(new Dimension(1100, 700));
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        panel.setBounds(0, 243, 1100, 124);
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

        btn_them = new JButton("Thêm nhân viên");
        btn_them.setForeground(new Color(255, 255, 255));
        btn_them.setBackground(Resources.C_PRIMARY);
        btn_them.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_them.setBounds(496, 41, 140, 38);
        panel.add(btn_them);

        btn_sua = new JButton("Sửa thông tin");
        btn_sua.setForeground(Color.WHITE);
        btn_sua.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_sua.setBackground(Resources.C_PRIMARY);
        btn_sua.setBounds(689, 41, 140, 38);
        panel.add(btn_sua);

        btn_xoa = new JButton("Xóa nhân viên");
        btn_xoa.setForeground(Color.WHITE);
        btn_xoa.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_xoa.setBackground(Resources.C_PRIMARY);
        btn_xoa.setBounds(881, 41, 127, 38);
        panel.add(btn_xoa);
        btn_xoa.setEnabled(false);
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.WHITE);
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        panel_1.setBounds(0, 378, 1100, 322);
        add(panel_1);
        panel_1.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 21, 1080, 248);
        panel_1.add(scrollPane);

        table = new TTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null},
                },
                new String[]{
                        "Id", "H\u1ECD t\u00EAn", "Gi\u1EDBi t\u00EDnh", "S\u1ED1 \u0111i\u1EC7n tho\u1EA1i", "Ch\u1EE9c v\u1EE5"
                }
        ) {
            boolean[] columnEditables = new boolean[]{
                    false, true, true, true, true
            };

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        scrollPane.setViewportView(table);

        panel_2 = new JPanel();
        panel_2.setBackground(Color.WHITE);
        panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        panel_2.setBounds(0, 0, 1100, 232);
        add(panel_2);
        panel_2.setLayout(null);

        lblHTn = new JLabel("Họ tên:");
        lblHTn.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblHTn.setBounds(30, 27, 61, 38);
        panel_2.add(lblHTn);

        txt_hoten = new JTextField();
        txt_hoten.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txt_hoten.setColumns(10);
        txt_hoten.setBounds(117, 28, 180, 38);
        panel_2.add(txt_hoten);

        txt_diachi = new JTextField();
        txt_diachi.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txt_diachi.setColumns(10);
        txt_diachi.setBounds(117, 106, 180, 38);
        panel_2.add(txt_diachi);

        lblaCh = new JLabel("Địa chỉ:");
        lblaCh.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblaCh.setBounds(30, 105, 61, 38);
        panel_2.add(lblaCh);

        txt_taikhoan = new JTextField();
        txt_taikhoan.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txt_taikhoan.setColumns(10);
        txt_taikhoan.setBounds(117, 184, 180, 38);
        panel_2.add(txt_taikhoan);

        lblTiKhon = new JLabel("Tài khoản:");
        lblTiKhon.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblTiKhon.setBounds(30, 183, 77, 38);
        panel_2.add(lblTiKhon);

        lblGiiTnh = new JLabel("Giới tính:");
        lblGiiTnh.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblGiiTnh.setBounds(377, 27, 96, 38);
        panel_2.add(lblGiiTnh);

        lblSinThoi = new JLabel("Số điện thoại:");
        lblSinThoi.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblSinThoi.setBounds(377, 106, 96, 38);
        panel_2.add(lblSinThoi);

        lblMtKhu = new JLabel("Mật khẩu:");
        lblMtKhu.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblMtKhu.setBounds(377, 184, 96, 38);
        panel_2.add(lblMtKhu);

        txt_sodienthoai = new JTextField();
        txt_sodienthoai.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txt_sodienthoai.setColumns(10);
        txt_sodienthoai.setBounds(483, 106, 180, 38);
        panel_2.add(txt_sodienthoai);

        txt_matKhau = new JTextField();
        txt_matKhau.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txt_matKhau.setColumns(10);
        txt_matKhau.setBounds(483, 183, 180, 38);
        panel_2.add(txt_matKhau);

        cbb_gioitinh = new JComboBox();
        cbb_gioitinh.setModel(new DefaultComboBoxModel(new String[]{"Nam", "Nữ"}));
        cbb_gioitinh.setBackground(Color.WHITE);
        cbb_gioitinh.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        cbb_gioitinh.setBounds(483, 28, 180, 38);
        panel_2.add(cbb_gioitinh);

        lblCmnd = new JLabel("CMND:");
        lblCmnd.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblCmnd.setBounds(748, 27, 86, 38);
        panel_2.add(lblCmnd);

        txt_cmnd = new JTextField();
        txt_cmnd.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txt_cmnd.setColumns(10);
        txt_cmnd.setBounds(838, 28, 180, 38);
        panel_2.add(txt_cmnd);

        lblaCh_1 = new JLabel("Email:");
        lblaCh_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblaCh_1.setBounds(748, 106, 86, 38);
        panel_2.add(lblaCh_1);

        lblChcV = new JLabel("Chức vụ:");
        lblChcV.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblChcV.setBounds(748, 184, 86, 38);
        panel_2.add(lblChcV);

        txt_Email = new JTextField();
        txt_Email.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txt_Email.setColumns(10);
        txt_Email.setBounds(838, 106, 180, 38);
        panel_2.add(txt_Email);

        cbb_chucvu = new JComboBox();
        cbb_chucvu.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        cbb_chucvu.setBackground(Color.WHITE);
        cbb_chucvu.setBounds(838, 184, 180, 38);
        panel_2.add(cbb_chucvu);

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

        roleRepository.findAll().forEach(role -> {
            cbb_chucvu.addItem(role.getName());
        });

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Optional<Employee> employee;
                employee = employeeRepository.findById((int) table.getValueAt(table.getSelectedRow(), 0));
                txt_cmnd.setText(employee.get().getIdentifyCard());
                txt_diachi.setText(employee.get().getAddress());
                txt_Email.setText(employee.get().getEmail());
                txt_hoten.setText(employee.get().getName());
                txt_matKhau.setText(employee.get().getPassword());
                txt_sodienthoai.setText(employee.get().getPhone());
                txt_taikhoan.setText(employee.get().getUsername());
                cbb_chucvu.getModel().setSelectedItem(roleRepository.findNameById(employee.get().getRoleId()));
                ;
                cbb_gioitinh.getModel().setSelectedItem(employee.get().getGender());
                btn_them.setEnabled(false);
                btn_sua.setEnabled(true);
                btn_xoa.setEnabled(true);
                txt_taikhoan.setEnabled(false);
                txt_matKhau.setEditable(false);

            }
        });

        btn_them.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Employee employee = new Employee();
                if (testCommit() == true) {
                    if (employeeRepository.findUserName(txt_taikhoan.getText()).equals("")) {

                        employee.setName(txt_hoten.getText());
                        employee.setAddress(txt_diachi.getText());
                        employee.setEmail(txt_Email.getText());
                        employee.setGender(cbb_gioitinh.getSelectedItem().toString());
                        employee.setPassword(txt_matKhau.getText());
                        employee.setPhone(txt_sodienthoai.getText());
                        employee.setUsername(txt_taikhoan.getText());
                        employee.setIdentifyCard(txt_cmnd.getText());
                        employee.setRoleId(roleRepository.findIdByName(cbb_chucvu.getSelectedItem().toString()));
                        employee.setStatus("Active");
                        employeeRepository.insert(employee);
                        reset();
                        loadTable();
                    } else {
                        JOptionPane.showMessageDialog(txt_sodienthoai, "Tài khoản đã tồn tại.");
                    }
                }
            }
        });

        btn_sua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Employee employee = new Employee();
                if (testCommit() == true) {
                    employee.setId((Integer) table.getValueAt(table.getSelectedRow(), 0));
                    employee.setName(txt_hoten.getText());
                    employee.setAddress(txt_diachi.getText());
                    employee.setEmail(txt_Email.getText());
                    employee.setGender(cbb_gioitinh.getSelectedItem().toString());
                    employee.setPassword(txt_matKhau.getText());
                    employee.setPhone(txt_sodienthoai.getText());
                    employee.setUsername(txt_taikhoan.getText());
                    employee.setIdentifyCard(txt_cmnd.getText());
                    employee.setRoleId(roleRepository.findIdByName(cbb_chucvu.getSelectedItem().toString()));
                    employee.setStatus("Active");
                    employeeRepository.update(employee);
                    btn_sua.setEnabled(false);
                    btn_xoa.setEnabled(false);
                    btn_them.setEnabled(true);
                    txt_taikhoan.setEnabled(true);
                    txt_matKhau.setEditable(true);

                    loadTable();
                    reset();
                }
            }

        });

        btn_tim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                List<Employee> employees = employeeRepository.searchByName(txt_timKiem.getText());
                employees.forEach(employee -> {
                    model.addRow(new Object[]{
                            employee.getId(), employee.getName(), employee.getGender(), employee.getPhone(), roleRepository.findNameById(employee.getRoleId())
                    });
                });
            }
        });

        btn_xoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int value = JOptionPane.showConfirmDialog(btn_them, "Bạn chắc chắn muốn xóa?");
                if (value == 0) {
                    employeeRepository.updateStatusDeleted((int) table.getValueAt(table.getSelectedRow(), 0));
                    btn_sua.setEnabled(false);
                    btn_xoa.setEnabled(false);
                    btn_them.setEnabled(true);
                    txt_taikhoan.setEnabled(true);
                    txt_matKhau.setEditable(true);

                    loadTable();
                    reset();
                }
            }
        });
    }

    private boolean testCommit() {
        if (txt_hoten.getText().equals("")) {
            JOptionPane.showMessageDialog(txt_sodienthoai, "Họ tên không được để trống");
            return false;
        }
        if (txt_diachi.getText().equals("")) {
            JOptionPane.showMessageDialog(txt_sodienthoai, "Địa chỉ không được bỏ trống");
            return false;
        }
        if (txt_cmnd.getText().equals("")) {
            JOptionPane.showMessageDialog(txt_sodienthoai, "Chứng minh nhân dân không được bỏ trống");
            return false;
        }
        if (txt_Email.getText().equals("")) {
            JOptionPane.showMessageDialog(txt_sodienthoai, "Email không được bỏ trống");
            return false;
        }
        if (txt_matKhau.getText().equals("")) {
            JOptionPane.showMessageDialog(txt_sodienthoai, "Mật khẩu không được bỏ trống");
            return false;
        }
        if (txt_taikhoan.getText().equals("")) {
            JOptionPane.showMessageDialog(txt_sodienthoai, "Tài khoản không được bỏ trống");
            return false;
        }
        if (txt_sodienthoai.getText().equals("")) {
            JOptionPane.showMessageDialog(txt_sodienthoai, "Số điện thoại không được bỏ trống");
            return false;
        }
        if (txt_matKhau.getText().equals("")) {
            JOptionPane.showMessageDialog(txt_sodienthoai, "Mật khẩu không được bỏ trống");
            return false;
        }
        if ((txt_sodienthoai.getText().toString().matches(".*[^0-9].*")) || txt_sodienthoai.getText().length() != 10) {
            JOptionPane.showMessageDialog(txt_sodienthoai, "Số điện thoại phải đủ 10 số và không ghi kí tự.");
            return false;
        }
        if (txt_cmnd.getText().toString().matches(".*[^0-9].*") || txt_cmnd.getText().length() != 9) {
            JOptionPane.showMessageDialog(txt_sodienthoai, "Chứng minh nhân dân phải đủ 9 số và không ghi kí tự.");
            return false;
        }
        if (!txt_Email.getText().matches("\\w+@\\w+[.]\\w+")) {
            JOptionPane.showMessageDialog(txt_sodienthoai, "Mail phải đúng định dạng abc@gmail.com");
            return false;
        }
        return true;
    }

    private void reset() {
        txt_cmnd.setText("");
        txt_diachi.setText("");
        txt_Email.setText("");
        txt_hoten.setText("");
        txt_matKhau.setText("");
        txt_sodienthoai.setText("");
        txt_taikhoan.setText("");
    }

    private void loadTable() {
        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        employeeRepository.findAllNotDeleted().forEach(employee -> {
            model.addRow(new Object[]{
                    employee.getId(), employee.getName(), employee.getGender(), employee.getPhone(), roleRepository.findNameById(employee.getRoleId())
            });
        });
    }

    public static void main(String[] args) {
        new TestFrame(new EmployeeManagerPanel());

    }
}
