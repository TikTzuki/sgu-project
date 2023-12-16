package tiktzuki.e_store.GUI.frames.receiving;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import tiktzuki.e_store.DAL.DeviceRepository;
import tiktzuki.e_store.DAL.EmployeeRepository;
import tiktzuki.e_store.DAL.ProviderRepository;
import tiktzuki.e_store.DAL.ReceivingDetailRepository;
import tiktzuki.e_store.DAL.ReceivingNoteRepository;
import tiktzuki.e_store.DTO.ReceivingNote;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.customcomponents.TTable;

public class DetailReceivingPanel extends JPanel {
    private static JTextField txt_nhaCungCap;
    private JTextField txt_tongTien;
    private JButton btn_quayLai;
    private JTable table;
    private JLabel lbl_maNv;
    private JLabel lbl_ngayLapPhieu;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDateTime now = LocalDateTime.now();
    DefaultTableModel model;
    public static ReceivingNote receivingNote;
    private ProviderRepository providerRepository = new ProviderRepository();
    private EmployeeRepository employeeRepository = new EmployeeRepository();
    private ReceivingDetailRepository receivingDetailRepository = new ReceivingDetailRepository();
    private ReceivingNoteRepository receivingNoteRepository = new ReceivingNoteRepository();
    private DeviceRepository deviceRepository = new DeviceRepository();


    /**
     * Create the panel.
     */
    public DetailReceivingPanel() {
        receivingNote = ReceivingMainPanel.selectedReceiving;
        initComp();
        initData();
    }

    public DetailReceivingPanel(int id) {
        DetailReceivingPanel.receivingNote = receivingNoteRepository.findById(id);
        initComp();
        initData();
    }

    private void initComp() {
        setForeground(Color.WHITE);
        setPreferredSize(new Dimension(1100, 700));
        setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 0, 1100, 99);
        add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Nhân viên:");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblNewLabel.setBounds(106, 24, 73, 17);
        panel.add(lblNewLabel);

        lbl_maNv = new JLabel(receivingNote.getId() != null ? receivingNote.getStaffId().toString() : "1");
        lbl_maNv.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_maNv.setBounds(177, 24, 15, 17);
        panel.add(lbl_maNv);

        JLabel lbl_tenNV = new JLabel(receivingNote.getId() != null ? "- " + employeeRepository.findNameById(receivingNote.getStaffId()) : "- Admin");
        lbl_tenNV.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_tenNV.setBounds(200, 24, 73, 17);
        panel.add(lbl_tenNV);

        JLabel lblNgafy = new JLabel("Ngày lập phiếu:");
        lblNgafy.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblNgafy.setBounds(106, 59, 98, 17);
        panel.add(lblNgafy);

        lbl_ngayLapPhieu = new JLabel(receivingNote.getId() != null ? receivingNote.getCreateDate().toString() : dtf.format(now));
        lbl_ngayLapPhieu.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_ngayLapPhieu.setBounds(209, 59, 155, 17);
        panel.add(lbl_ngayLapPhieu);
        lbl_ngayLapPhieu.setText(dtf.format(now));

        JLabel lblNhCungCp = new JLabel("Nhà cung cấp:");
        lblNhCungCp.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblNhCungCp.setBounds(402, 24, 98, 17);
        panel.add(lblNhCungCp);

        txt_nhaCungCap = new JTextField(receivingNote.getId() != null ? providerRepository.findNameById(receivingNote.getProviderId()) : "");
        txt_nhaCungCap.setBounds(498, 16, 155, 35);
        txt_nhaCungCap.setEnabled(false);
        panel.add(txt_nhaCungCap);
        txt_nhaCungCap.setColumns(10);

        JLabel lblTngTin = new JLabel("Tổng tiền:");
        lblTngTin.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblTngTin.setBounds(781, 24, 73, 17);
        panel.add(lblTngTin);

        txt_tongTien = new JTextField(receivingNote.getId() != null ? receivingNote.getAmount().toString() : "0");
        txt_tongTien.setColumns(10);
        txt_tongTien.setBounds(864, 16, 155, 35);
        panel.add(txt_tongTien);
        txt_tongTien.setEnabled(false);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        panel_1.setBackground(Color.WHITE);
        panel_1.setBounds(0, 99, 1100, 590);
        add(panel_1);
        panel_1.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 61, 1080, 447);
        panel_1.add(scrollPane);

        table = new TTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "M\u00E3 thi\u1EBFt b\u1ECB", "T\u00EAn thi\u1EBFt b\u1ECB", "S\u1ED1 l\u01B0\u1EE3ng", "\u0110\u01A1n gi\u00E1", "Th\u00E0nh ti\u1EC1n"
                }
        ) {
            boolean[] columnEditables = new boolean[]{
                    false, false, false, false, false
            };

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        scrollPane.setViewportView(table);

        JLabel lblDanhSchThit = new JLabel("Danh sách thiết bị");
        lblDanhSchThit.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblDanhSchThit.setBounds(482, 21, 134, 17);
        panel_1.add(lblDanhSchThit);

        btn_quayLai = new JButton("Quay lại");
        btn_quayLai.setForeground(Color.WHITE);
        btn_quayLai.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_quayLai.setBackground(Resources.C_PRIMARY);
        btn_quayLai.setBounds(503, 538, 89, 41);
        panel_1.add(btn_quayLai);
    }

    private void initData() {
        btn_quayLai.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ReceivingMainPanel.initAddReceivingManagerPanel();
            }
        });
        loadModifyTable();

    }

    private void loadModifyTable() {
        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        if (receivingNote.getId() != null) {
            table.setEnabled(false);
            receivingDetailRepository.findListByReceivingId(receivingNote.getId()).forEach(receivingDetail -> {
                model.addRow(new Object[]{
                        receivingDetail.getDeviceId(), deviceRepository.findNameById(receivingDetail.getDeviceId()), receivingDetail.getQuantity(),
                        receivingDetail.getPrice(), receivingDetail.getAmount()
                });
            });
        }
    }

}
