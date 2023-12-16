package tiktzuki.e_store.GUI.frames.receiving;

import java.awt.Dimension;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tiktzuki.e_store.DAL.DeviceRepository;
import tiktzuki.e_store.DAL.EmployeeRepository;
import tiktzuki.e_store.DAL.ProviderRepository;
import tiktzuki.e_store.DAL.ReceivingDetailRepository;
import tiktzuki.e_store.DAL.ReceivingNoteRepository;
import tiktzuki.e_store.DTO.Device;
import tiktzuki.e_store.DTO.ReceivingDetail;
import tiktzuki.e_store.DTO.ReceivingNote;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.customcomponents.TTable;
import tiktzuki.e_store.GUI.frames.device.TestFrame;

public class AddReceivingPanel extends JPanel {
    private static JTextField txt_nhaCungCap;
    private JTextField txt_tongTien;
    private static JTextField txt_maThietBi;
    private static JTextField txt_tenThietBi;
    private JTextField txt_soLuong;
    private JTextField txt_donGia;
    private JButton btn_chonNhaCungCap;
    private JButton btn_chonThietBi;
    private JButton btn_luuThietBi;
    private JButton btn_luu;
    private JButton btn_quayLai;
    private JTable table;
    private JLabel lbl_maNv;
    private JLabel lbl_ngayLapPhieu;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private JButton btn_xoaThietBi;
    LocalDateTime now = LocalDateTime.now();
    DefaultTableModel model;
    private ReceivingNoteRepository receivingNoteRepository = new ReceivingNoteRepository();
    private ReceivingDetailRepository receivingDetailRepository = new ReceivingDetailRepository();
    private DeviceRepository deviceRepository = new DeviceRepository();
    private ProviderRepository providerRepository = new ProviderRepository();
    private EmployeeRepository employeeRepository = new EmployeeRepository();
    public static ReceivingNote receivingNote;

    /**
     * Create the panel.
     */
    public AddReceivingPanel() {
        receivingNote = ReceivingMainPanel.selectedReceiving;
        initComp();
        initData();
    }

    public AddReceivingPanel(int id) {
        AddReceivingPanel.receivingNote = receivingNoteRepository.findById(id);
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

        btn_chonNhaCungCap = new JButton("Chọn");
        btn_chonNhaCungCap.setForeground(Color.WHITE);
        btn_chonNhaCungCap.setBackground(Resources.C_PRIMARY);
        btn_chonNhaCungCap.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_chonNhaCungCap.setBounds(580, 56, 73, 23);
        panel.add(btn_chonNhaCungCap);

        JLabel lblTngTin = new JLabel("Tổng tiền:");
        lblTngTin.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblTngTin.setBounds(781, 24, 73, 17);
        panel.add(lblTngTin);

        txt_tongTien = new JTextField("0");
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

        JLabel lblMThitB = new JLabel("Mã thiết bị:");
        lblMThitB.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblMThitB.setBounds(41, 26, 73, 17);
        panel_1.add(lblMThitB);

        txt_maThietBi = new JTextField();
        txt_maThietBi.setColumns(10);
        txt_maThietBi.setBounds(124, 18, 78, 35);
        panel_1.add(txt_maThietBi);
        txt_maThietBi.setEnabled(false);

        JLabel lblTnThitB = new JLabel("Tên thiết bị:");
        lblTnThitB.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblTnThitB.setBounds(239, 26, 88, 17);
        panel_1.add(lblTnThitB);

        txt_tenThietBi = new JTextField();
        txt_tenThietBi.setColumns(10);
        txt_tenThietBi.setBounds(325, 18, 192, 35);
        panel_1.add(txt_tenThietBi);
        txt_tenThietBi.setEnabled(false);

        JLabel lblSLng = new JLabel("Số lượng:");
        lblSLng.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblSLng.setBounds(565, 28, 73, 17);
        panel_1.add(lblSLng);

        txt_soLuong = new JTextField();
        txt_soLuong.setColumns(10);
        txt_soLuong.setBounds(635, 18, 155, 35);
        panel_1.add(txt_soLuong);

        JLabel lblnGi = new JLabel("Đơn giá:");
        lblnGi.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblnGi.setBounds(836, 28, 73, 17);
        panel_1.add(lblnGi);

        txt_donGia = new JTextField();
        txt_donGia.setColumns(10);
        txt_donGia.setBounds(901, 18, 155, 35);
        panel_1.add(txt_donGia);

        btn_chonThietBi = new JButton("Chọn thiết bị");
        btn_chonThietBi.setForeground(Color.WHITE);
        btn_chonThietBi.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_chonThietBi.setBackground(Resources.C_PRIMARY);
        btn_chonThietBi.setBounds(392, 66, 125, 25);
        panel_1.add(btn_chonThietBi);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 145, 1080, 363);
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
        lblDanhSchThit.setBounds(466, 117, 134, 17);
        panel_1.add(lblDanhSchThit);

        btn_luuThietBi = new JButton("Lưu thiết bị");
        btn_luuThietBi.setForeground(Color.WHITE);
        btn_luuThietBi.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_luuThietBi.setBackground(Resources.C_PRIMARY);
        btn_luuThietBi.setBounds(854, 113, 113, 25);
        panel_1.add(btn_luuThietBi);

        btn_luu = new JButton("Lưu");
        btn_luu.setForeground(Color.WHITE);
        btn_luu.setBackground(Resources.C_PRIMARY);
        btn_luu.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_luu.setBounds(404, 538, 89, 41);
        panel_1.add(btn_luu);

        btn_quayLai = new JButton("Quay lại");
        btn_quayLai.setForeground(Color.WHITE);
        btn_quayLai.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_quayLai.setBackground(Resources.C_PRIMARY);
        btn_quayLai.setBounds(586, 538, 89, 41);
        panel_1.add(btn_quayLai);

        btn_xoaThietBi = new JButton("Xóa thiết bị");
        btn_xoaThietBi.setForeground(Color.WHITE);
        btn_xoaThietBi.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_xoaThietBi.setBackground(Resources.C_PRIMARY);
        btn_xoaThietBi.setBounds(977, 113, 113, 25);
        panel_1.add(btn_xoaThietBi);
    }

    private void initData() {
        btn_chonNhaCungCap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddProviderDialog().setVisible(true);
            }
        });

        btn_chonThietBi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddDeviceDialog().setVisible(true);
            }
        });

        btn_quayLai.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ReceivingMainPanel.initAddReceivingManagerPanel();
            }
        });

        btn_xoaThietBi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model = (DefaultTableModel) table.getModel();
                int index = table.getSelectedRow();
                model.removeRow(index);
                setTongTien();
            }
        });

        btn_luuThietBi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model = (DefaultTableModel) table.getModel();
                String a = txt_maThietBi.getText().toString();
                if (testCommit() == true) {
                    int soLuong = Integer.parseInt(txt_soLuong.getText().toString());
                    int donGia = Integer.parseInt(txt_donGia.getText().toString());
                    int thanhTien = soLuong * donGia;
                    int count = model.getRowCount();
                    for (int i = 0; i < count; i++) {
                        String b = model.getValueAt(i, 0).toString();
                        if (a.equals(b)) {
                            model.removeRow(i);
                            i = count - 1;
                        }
                    }
                    model.addRow(new Object[]{
                            txt_maThietBi.getText(), txt_tenThietBi.getText(), soLuong, donGia, thanhTien
                    });
                    txt_donGia.setText("");
                    txt_soLuong.setText("");
                    txt_maThietBi.setText("");
                    txt_tenThietBi.setText("");
                    setTongTien();
                }
            }
        });

        btn_luu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model = (DefaultTableModel) table.getModel();
                int ktThietBi = model.getRowCount();
                if (!txt_nhaCungCap.getText().equals("") && ktThietBi != 0) {
                    ReceivingNote receivingNote = new ReceivingNote();
                    receivingNote.setStaffId(Integer.parseInt(lbl_maNv.getText().toString()));
                    receivingNote.setCreateDate(lbl_ngayLapPhieu.getText());
                    receivingNote.setProviderId(providerRepository.findIdByName(txt_nhaCungCap.getText().toString()));
                    receivingNote.setAmount(Integer.parseInt(txt_tongTien.getText().toString()));
                    //update receiNote
                    if (AddReceivingPanel.receivingNote.getId() != null) {
                        receivingNote.setId(AddReceivingPanel.receivingNote.getId());
                        receivingNote.setAmount(AddReceivingPanel.receivingNote.getAmount() + Integer.parseInt(txt_tongTien.getText().toString()));
                        receivingNoteRepository.update(receivingNote);
                    }
                    //insert receiNote
                    else {
                        receivingNoteRepository.insert(receivingNote);
                    }
                    ReceivingDetail receivingDetail = new ReceivingDetail();
                    Device device = new Device();
                    model = (DefaultTableModel) table.getModel();
                    int count = model.getRowCount();
                    //update detail
                    if (AddReceivingPanel.receivingNote.getId() != null) {
//					for(int i =0 ;i < count; i++) {
//						receivingDetailRepository.deleteByReceivingId(AddReceivingPanel.receivingNote.getId());
//					}
                        for (int i = 0; i < count; i++) {
                            receivingDetail.setReceivingId(AddReceivingPanel.receivingNote.getId());
                            receivingDetail.setDeviceId(Integer.parseInt(model.getValueAt(i, 0).toString()));
                            receivingDetail.setQuantity(Integer.parseInt(model.getValueAt(i, 2).toString()));
                            receivingDetail.setPrice(Integer.parseInt(model.getValueAt(i, 3).toString()));
                            receivingDetail.setAmount(Integer.parseInt(model.getValueAt(i, 4).toString()));
                            receivingDetailRepository.insert(receivingDetail);
                            int soLuongThietBiHienTai = deviceRepository.getQuantityById(Integer.parseInt(model.getValueAt(i, 0).toString()));
                            int soLuongThietBiNhap = Integer.parseInt(model.getValueAt(i, 2).toString());
                            device.setId(Integer.parseInt(model.getValueAt(i, 0).toString()));
                            device.setQuantity(soLuongThietBiHienTai + soLuongThietBiNhap);
                            deviceRepository.updateQuantity(device);
                        }
                    }//insert detail
                    else {
                        for (int i = 0; i < count; i++) {
                            //Them chi tiet phieu nhap
                            receivingDetail.setReceivingId(receivingNoteRepository.findLastId());
                            receivingDetail.setDeviceId(Integer.parseInt(model.getValueAt(i, 0).toString()));
                            receivingDetail.setQuantity(Integer.parseInt(model.getValueAt(i, 2).toString()));
                            receivingDetail.setPrice(Integer.parseInt(model.getValueAt(i, 3).toString()));
                            receivingDetail.setAmount(Integer.parseInt(model.getValueAt(i, 4).toString()));
                            receivingDetailRepository.insert(receivingDetail);
                            //Them so luong thiet bi
                            int soLuongThietBiHienTai = deviceRepository.getQuantityById(Integer.parseInt(model.getValueAt(i, 0).toString()));
                            int soLuongThietBiNhap = Integer.parseInt(model.getValueAt(i, 2).toString());
                            device.setId(Integer.parseInt(model.getValueAt(i, 0).toString()));
                            device.setQuantity(soLuongThietBiHienTai + soLuongThietBiNhap);
                            deviceRepository.updateQuantity(device);
                            reset();
                        }
                    }
                    ReceivingMainPanel.initAddReceivingManagerPanel();
                } else {
                    JOptionPane.showMessageDialog(btn_chonThietBi, "Không bỏ trống nhà cung cấp và danh sách thiết bị.");
                }
            }
        });
    }

    private boolean testCommit() {
        if (txt_soLuong.getText().equals("") || txt_donGia.getText().equals("") || txt_maThietBi.getText().equals("")) {
            JOptionPane.showMessageDialog(btn_chonThietBi, "Không được bỏ trống!");
            return false;
        }
        if (txt_soLuong.getText().matches("^[a-zA-Z]+$") || txt_donGia.getText().matches("^[a-zA-Z]+$")) {
            JOptionPane.showMessageDialog(btn_chonThietBi, "Số lượng và đơn giá không được ghi kí tự!");
            return false;
        }
        return true;
    }

    private void setTongTien() {
        int tongTien = 0;
        int thanhTienHienCo = 0;
        int count2 = model.getRowCount();
        for (int i = 0; i < count2; i++) {
            thanhTienHienCo = Integer.parseInt(model.getValueAt(i, 4).toString());
            tongTien = tongTien + thanhTienHienCo;
        }
        txt_tongTien.setText(String.valueOf(tongTien));
    }


    private void reset() {
        txt_nhaCungCap.setText("");
        txt_tongTien.setText("");
    }

    public static void setNhaCungCap(String data) {
        txt_nhaCungCap.setText(data);
    }

    public static void setMaThietBi(String data) {
        txt_maThietBi.setText(data);
    }

    public static void setTenThietBi(String data) {
        txt_tenThietBi.setText(data);
    }

    public static void main(String[] args) {
        new TestFrame(new AddReceivingPanel());

    }

}
