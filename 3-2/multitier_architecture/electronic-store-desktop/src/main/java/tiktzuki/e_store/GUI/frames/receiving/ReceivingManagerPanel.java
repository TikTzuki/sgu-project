package tiktzuki.e_store.GUI.frames.receiving;

import javax.swing.JPanel;

import tiktzuki.e_store.DAL.EmployeeRepository;
import tiktzuki.e_store.DAL.ProviderRepository;
import tiktzuki.e_store.DAL.ReceivingNoteRepository;
import tiktzuki.e_store.DTO.Device;
import tiktzuki.e_store.DTO.Employee;
import tiktzuki.e_store.DTO.ReceivingNote;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.frames.device.DeviceMainPanel;
import tiktzuki.e_store.GUI.frames.device.DeviceMainPanel1;
import tiktzuki.e_store.GUI.frames.device.TestFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tiktzuki.e_store.GUI.customcomponents.DatePicker;
import tiktzuki.e_store.GUI.customcomponents.TTable;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JButton;

public class ReceivingManagerPanel extends JPanel {
    private JTable table;
    private JTextField txt_timKiem;
    private JButton btn_them;
    private JButton btn_xem;
    private JButton btn_sua;
    private JButton btnNewButton;
    private JButton btnTm;
    DefaultTableModel model;
    private ReceivingNoteRepository receivingNoteRepository = new ReceivingNoteRepository();
    private ProviderRepository providerRepository = new ProviderRepository();
    private EmployeeRepository employeeRepository = new EmployeeRepository();
    DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");

    /**
     * Create the panel.
     */
    public ReceivingManagerPanel() {
        initComp();
        initData();
    }

    private void initComp() {
        setBackground(Color.WHITE);
        setLayout(null);
        setPreferredSize(new Dimension(1100, 700));
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        panel.setBounds(0, 0, 1100, 124);
        panel.setBackground(Color.WHITE);
        add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Tìm kiếm");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblNewLabel.setBounds(80, 42, 70, 31);
        panel.add(lblNewLabel);

        txt_timKiem = new JTextField();
        txt_timKiem.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txt_timKiem.setBounds(150, 42, 180, 38);
        panel.add(txt_timKiem);
        txt_timKiem.setColumns(10);

        btnNewButton = new JButton("Chọn ngày");
        btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
        btnNewButton.setBackground(Resources.C_PRIMARY);
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.setBounds(350, 42, 98, 38);
        panel.add(btnNewButton);
        btnNewButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                txt_timKiem.setText(new DatePicker().getPickedDate("dd/MM/yyyy"));
            }
        });

        btnTm = new JButton("Tìm");
        btnTm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnTm.setForeground(Color.WHITE);
        btnTm.setFont(new Font("Times New Roman", Font.BOLD, 12));
        btnTm.setBackground(Resources.C_PRIMARY);
        btnTm.setBounds(150, 82, 70, 31);
        panel.add(btnTm);


        btn_them = new JButton("Tạo phiếu nhập");
        btn_them.setForeground(new Color(255, 255, 255));
        btn_them.setBackground(Resources.C_PRIMARY);
        btn_them.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_them.setBounds(560, 41, 136, 38);
        panel.add(btn_them);

        btn_sua = new JButton("Bổ sung");
        btn_sua.setForeground(Color.WHITE);
        btn_sua.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_sua.setBackground(Resources.C_PRIMARY);
        btn_sua.setBounds(735, 41, 127, 38);
        panel.add(btn_sua);

        btn_xem = new JButton("Xem chi tiết");
        btn_xem.setForeground(Color.WHITE);
        btn_xem.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_xem.setBackground(Resources.C_PRIMARY);
        btn_xem.setBounds(901, 41, 127, 38);
        panel.add(btn_xem);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.WHITE);
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1.setBounds(0, 135, 1100, 565);
        add(panel_1);
        panel_1.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 1080, 406);
        panel_1.add(scrollPane);

        table = new TTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null},
                },
                new String[]{
                        "Id", "Nh\u00E2n vi\u00EAn", "Ng\u00E0y l\u1EADp", "T\u1ED5ng ti\u1EC1n", "Nh\u00E0 cung c\u1EA5p"
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
    }

    private void initData() {
        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        receivingNoteRepository.findAll().forEach(receiving -> {
            model.addRow(new Object[]{
                    receiving.getId(), employeeRepository.findNameById(receiving.getStaffId()), receiving.getCreateDate(),
                    receiving.getAmount(), providerRepository.findNameById(receiving.getProviderId())
            });
        });

        btnTm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                List<ReceivingNote> receivingNotes = receivingNoteRepository.searchByDate(txt_timKiem.getText());
                receivingNotes.forEach(receiving -> {
                    model.addRow(new Object[]{
                            receiving.getId(), employeeRepository.findNameById(receiving.getStaffId()), receiving.getCreateDate(),
                            receiving.getAmount(), providerRepository.findNameById(receiving.getProviderId())
                    });
                });
            }
        });

        btn_them.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ReceivingMainPanel.initAddReceivingPanel();
            }
        });

        btn_sua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ReceivingMainPanel.selectedReceiving != null && ReceivingMainPanel.selectedReceiving.getId() != null) {
                    ReceivingMainPanel.initModifyReceivingNote();
                }
            }
        });

        btn_xem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ReceivingMainPanel.selectedReceiving != null && ReceivingMainPanel.selectedReceiving.getId() != null) {
                    ReceivingMainPanel.initDetailReceivingNote();
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selectedId = (Integer) table.getValueAt(table.getSelectedRow(), 0);

                for (ReceivingNote receivingNote : ReceivingMainPanel.receivingNotes) {
                    if (receivingNote.getId() == selectedId) {
                        ReceivingMainPanel.selectedReceiving = receivingNote;
                    }
                }
            }
        });


    }

    public static void main(String[] args) {
        new TestFrame(new ReceivingManagerPanel());

    }
}
