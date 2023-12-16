package tiktzuki.e_store.GUI.frames.device;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import tiktzuki.e_store.DAL.BrandRepository;
import tiktzuki.e_store.DAL.DeviceDetailRepository;
import tiktzuki.e_store.DAL.DeviceRepository;
import tiktzuki.e_store.DAL.DeviceStatusRepository;
import tiktzuki.e_store.DAL.DeviceTypeRepository;
import tiktzuki.e_store.DAL.SpecificationDetailRepository;
import tiktzuki.e_store.DAL.SpecificationRepository;
import tiktzuki.e_store.DTO.Device;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.customcomponents.TTable;

public class DetailDevicePanel extends JPanel {
    private JTextField txt_tenThietBi;
    private JTextField txt_gia;
    private JTextField txt_soLuong;
    private JTextArea txt_moTa;
    private JLabel lbl_Anh;
    private JButton btn_quayLai;
    private static String pathFile;
    public DeviceRepository deviceRepository = new DeviceRepository();
    public DeviceDetailRepository detailRepository = new DeviceDetailRepository();
    public DeviceTypeRepository deviceTypeRepository = new DeviceTypeRepository();
    public SpecificationRepository specificationRepository = new SpecificationRepository();
    public SpecificationDetailRepository specificationDetailRepository = new SpecificationDetailRepository();
    public BrandRepository brandRepository = new BrandRepository();
    public DeviceStatusRepository deviceStatusRepository = new DeviceStatusRepository();
    private JTable table_Chitietthongso;
    DefaultTableModel model;
    public static Device device;
    private JTextField txt_loaiThietBi;
    private JTextField txt_hang;
    private JTextField txt_trangThai;

    /**
     * Create the panel.
     */
    public DetailDevicePanel() {
        device = DeviceMainPanel.selectedDevice;
        initComp();
        initData();
    }

    public DetailDevicePanel(int id) {
        DetailDevicePanel.device = deviceRepository.findById(id);
        initComp();
        initData();
    }

    private void initComp() {
        this.setBackground(Resources.C_LIGHT);
        setPreferredSize(new Dimension(1100, 700));
        setBorder(new LineBorder(new Color(0, 0, 0)));
        this.setBackground(Color.WHITE);
        setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 0, 1100, 350);
        panel.setBorder(new LineBorder(new Color(0, 0, 0)));
        add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Tên thiết bị");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblNewLabel.setBounds(327, 29, 97, 23);
        panel.add(lblNewLabel);

        txt_tenThietBi = new JTextField(device.getId() != null ? device.getName().toString() : "");
        txt_tenThietBi.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txt_tenThietBi.setBounds(434, 22, 200, 35);
        panel.add(txt_tenThietBi);
        txt_tenThietBi.setColumns(10);
        txt_tenThietBi.setEnabled(false);

        JLabel lblLoiThitB = new JLabel("Loại thiết bị");
        lblLoiThitB.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblLoiThitB.setBounds(327, 81, 97, 23);
        panel.add(lblLoiThitB);

        JLabel lblHng = new JLabel("Hãng");
        lblHng.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblHng.setBounds(327, 138, 97, 23);
        panel.add(lblHng);

        JLabel lblTrngThi = new JLabel("Trạng thái");
        lblTrngThi.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblTrngThi.setBounds(327, 291, 97, 23);
        panel.add(lblTrngThi);

        txt_gia = new JTextField(device.getId() != null ? device.getPrice().toString() : "");
        txt_gia.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txt_gia.setColumns(10);
        txt_gia.setBounds(434, 179, 200, 35);
        panel.add(txt_gia);
        txt_gia.setEnabled(false);

        txt_soLuong = new JTextField(device.getId() != null ? device.getQuantity().toString() : "");
        txt_soLuong.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txt_soLuong.setColumns(10);
        txt_soLuong.setBounds(434, 232, 200, 35);
        panel.add(txt_soLuong);
        txt_soLuong.setEnabled(false);

        JLabel lblGiBn = new JLabel("Giá bán");
        lblGiBn.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblGiBn.setBounds(327, 186, 97, 23);
        panel.add(lblGiBn);

        JLabel lblSLng = new JLabel("Số lượng");
        lblSLng.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblSLng.setBounds(327, 239, 97, 23);
        panel.add(lblSLng);

        JPanel panel_2 = new JPanel();
        panel_2.setBackground(Color.WHITE);
        panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        panel_2.setBounds(34, 29, 195, 204);
        panel.add(panel_2);
        panel_2.setLayout(null);

        lbl_Anh = new JLabel("");
        lbl_Anh.setBackground(Color.WHITE);
        lbl_Anh.setBounds(10, 7, 175, 186);
        panel_2.add(lbl_Anh);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(703, 63, 370, 257);
        panel.add(scrollPane);

        table_Chitietthongso = new TTable();
        table_Chitietthongso.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Th\u00F4ng s\u1ED1", "Chi ti\u1EBFt th\u00F4ng s\u1ED1"
                }
        ) {
            boolean[] columnEditables = new boolean[]{
                    false, false
            };

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        table_Chitietthongso.getColumnModel().getColumn(1).setPreferredWidth(99);
        scrollPane.setViewportView(table_Chitietthongso);

        JLabel lblBngChiTit = new JLabel("Bảng chi tiết");
        lblBngChiTit.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblBngChiTit.setBounds(703, 29, 97, 23);
        panel.add(lblBngChiTit);

        txt_loaiThietBi = new JTextField(device.getId() != null ? deviceTypeRepository.findNameById(device.getDeviceTypeId()) : "");
        txt_loaiThietBi.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txt_loaiThietBi.setColumns(10);
        txt_loaiThietBi.setBounds(434, 74, 200, 35);
        panel.add(txt_loaiThietBi);
        txt_loaiThietBi.setEnabled(false);

        txt_hang = new JTextField(device.getId() != null ? brandRepository.findNameById(device.getBrandId()) : "");
        txt_hang.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txt_hang.setColumns(10);
        txt_hang.setBounds(434, 126, 200, 35);
        panel.add(txt_hang);
        txt_hang.setEnabled(false);

        txt_trangThai = new JTextField(device.getId() != null ? deviceStatusRepository.findNameById(device.getDeviceStatusId()) : "");
        txt_trangThai.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txt_trangThai.setColumns(10);
        txt_trangThai.setBounds(434, 284, 200, 35);
        panel.add(txt_trangThai);
        setPreferredSize(new Dimension(1100, 700));
        txt_trangThai.setEnabled(false);
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.WHITE);
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1.setBounds(0, 360, 1100, 340);
        add(panel_1);
        panel_1.setLayout(null);

        txt_moTa = new JTextArea(device.getId() != null ? device.getDescription().toString() : "");
        txt_moTa.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txt_moTa.setBounds(44, 47, 1008, 191);
        panel_1.add(txt_moTa);
        txt_moTa.setEnabled(false);
        txt_moTa.setBorder(getBorder());
        JLabel lblMT = new JLabel("Mô tả");
        lblMT.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblMT.setBounds(530, 11, 97, 23);
        panel_1.add(lblMT);

        btn_quayLai = new JButton("Quay lại");
        btn_quayLai.setForeground(Color.WHITE);
        btn_quayLai.setBackground(Color.BLUE);

        btn_quayLai.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_quayLai.setBounds(512, 267, 102, 35);
        panel_1.add(btn_quayLai);

    }

    private void initData() {
        loadModifyTable();
        loadModifyImage();
        btn_quayLai.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeviceMainPanel.initMainDevicePanel();
            }
        });

    }

    private void loadModifyTable() {
        model = (DefaultTableModel) table_Chitietthongso.getModel();
        model.setRowCount(0);
        if (device.getId() != null) {
            detailRepository.findByDeviceId(device.getId()).forEach(item -> {
                model.addRow(new Object[]{
                        specificationRepository.findNameById(specificationDetailRepository.findSpecIDById(item.getSpecificationDetailId()))
                        , specificationDetailRepository.findNameById(item.getSpecificationDetailId())
                });
            });
        }
    }


    private void loadModifyImage() {
        if (device.getId() != null) {
            java.io.File file = new java.io.File(device.getImage());
            BufferedImage b;
            try {
                b = ImageIO.read(file);
                lbl_Anh.setIcon(new ImageIcon(b));

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

    }
}
