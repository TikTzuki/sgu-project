package tiktzuki.e_store.GUI.frames.device;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;

import tiktzuki.e_store.DAL.BrandRepository;
import tiktzuki.e_store.DAL.DeviceDetailRepository;
import tiktzuki.e_store.DAL.DeviceRepository;
import tiktzuki.e_store.DAL.DeviceStatusRepository;
import tiktzuki.e_store.DAL.DeviceTypeRepository;
import tiktzuki.e_store.DAL.SpecificationDetailRepository;
import tiktzuki.e_store.DAL.SpecificationRepository;
import tiktzuki.e_store.DTO.Device;
import tiktzuki.e_store.DTO.DeviceDetail;
import tiktzuki.e_store.DTO.Specification;
import tiktzuki.e_store.DTO.SpecificationDetail;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.customcomponents.TTable;
import tiktzuki.e_store.services.ImageUtils;
import tiktzuki.e_store.services.LoadingImageService;

import javax.swing.JTextArea;

public class AddDevice1 extends JPanel {
    private JTextField txt_tenThietBi;
    private JTextField txt_gia;
    private JTextField txt_soLuong;
    private JComboBox cbb_loaiThietBi;
    private JComboBox cbb_trangThai;
    private JComboBox cbb_Hang;
    private JTextArea txt_moTa;
    private JButton btn_luu;
    private JButton btn_chonAnh;
    private JLabel lbl_Anh;
    private JButton btn_quayLai;
    private JComboBox cbb_thongSo;
    private JComboBox cbb_chiTietThongSo;
    private static String pathFile;
    private JButton btn_AddThongSo;
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

    /**
     * Create the panel.
     */
    public AddDevice1() {
        device = DeviceMainPanel.selectedDevice;
        initComp();
        initData();
    }

    public AddDevice1(int id) {
        AddDevice1.device = deviceRepository.findById(id);
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

        btn_chonAnh = new JButton("Chọn ảnh");
        btn_chonAnh.setForeground(Color.WHITE);
        btn_chonAnh.setBackground(Color.BLUE);
        btn_chonAnh.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_chonAnh.setBounds(84, 255, 97, 25);
        panel.add(btn_chonAnh);

        JLabel lblNewLabel = new JLabel("Tên thiết bị");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblNewLabel.setBounds(327, 29, 97, 23);
        panel.add(lblNewLabel);

        txt_tenThietBi = new JTextField(device.getId() != null ? device.getName().toString() : "");
        txt_tenThietBi.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txt_tenThietBi.setBounds(434, 22, 200, 35);
        panel.add(txt_tenThietBi);
        txt_tenThietBi.setColumns(10);

        JLabel lblLoiThitB = new JLabel("Loại thiết bị");
        lblLoiThitB.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblLoiThitB.setBounds(327, 81, 97, 23);
        panel.add(lblLoiThitB);

        cbb_loaiThietBi = new JComboBox();
        cbb_loaiThietBi.setForeground(Color.BLACK);
        cbb_loaiThietBi.setBackground(Color.WHITE);

        cbb_loaiThietBi.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        cbb_loaiThietBi.setBounds(434, 75, 200, 35);
        panel.add(cbb_loaiThietBi);

        cbb_Hang = new JComboBox();
        cbb_Hang.setForeground(new Color(0, 0, 0));
        cbb_Hang.setBackground(Color.WHITE);
        cbb_Hang.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        cbb_Hang.setBounds(434, 132, 200, 35);
        panel.add(cbb_Hang);

        cbb_trangThai = new JComboBox();
        cbb_trangThai.setBackground(Color.WHITE);
        cbb_trangThai.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        cbb_trangThai.setBounds(434, 285, 200, 35);
        panel.add(cbb_trangThai);

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

        txt_soLuong = new JTextField(device.getId() != null ? device.getQuantity().toString() : "0");
        txt_soLuong.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txt_soLuong.setColumns(10);
        txt_soLuong.setBounds(434, 232, 200, 35);
        panel.add(txt_soLuong);
        if (device.getId() != null) {
            txt_soLuong.setEnabled(true);
        } else {
            txt_soLuong.setEnabled(false);
        }
        JLabel lblGiBn = new JLabel("Giá bán");
        lblGiBn.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblGiBn.setBounds(327, 186, 97, 23);
        panel.add(lblGiBn);

        JLabel lblSLng = new JLabel("Số lượng");
        lblSLng.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblSLng.setBounds(327, 239, 97, 23);
        panel.add(lblSLng);

        JLabel lblBus = new JLabel("Thông số");
        lblBus.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblBus.setBounds(703, 29, 97, 23);
        panel.add(lblBus);

        JLabel lblNewLabel_1_1 = new JLabel("Chi tiết thông số");
        lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblNewLabel_1_1.setBounds(703, 81, 108, 23);
        panel.add(lblNewLabel_1_1);

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

        cbb_thongSo = new JComboBox();
        cbb_thongSo.setBackground(Color.WHITE);
        cbb_thongSo.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        cbb_thongSo.setBounds(819, 23, 200, 35);
        panel.add(cbb_thongSo);

        cbb_chiTietThongSo = new JComboBox();
        cbb_chiTietThongSo.setBackground(Color.WHITE);
        cbb_chiTietThongSo.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        cbb_chiTietThongSo.setBounds(819, 75, 200, 35);
        panel.add(cbb_chiTietThongSo);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(703, 145, 318, 175);
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
        lblBngChiTit.setBounds(703, 115, 97, 23);
        panel.add(lblBngChiTit);

        btn_AddThongSo = new JButton("+");
        btn_AddThongSo.setForeground(Color.WHITE);
        btn_AddThongSo.setBackground(Color.BLUE);

        btn_AddThongSo.setBounds(1027, 75, 46, 35);
        panel.add(btn_AddThongSo);
        setPreferredSize(new Dimension(1100, 700));

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
        txt_moTa.setBorder(getBorder());

        JLabel lblMT = new JLabel("Mô tả");
        lblMT.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblMT.setBounds(530, 11, 97, 23);
        panel_1.add(lblMT);

        btn_luu = new JButton("Lưu");
        btn_luu.setForeground(Color.WHITE);
        btn_luu.setBackground(Color.BLUE);
        btn_luu.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_luu.setBounds(408, 266, 102, 35);
        panel_1.add(btn_luu);

        btn_quayLai = new JButton("Quay lại");
        btn_quayLai.setForeground(Color.WHITE);
        btn_quayLai.setBackground(Color.BLUE);

        btn_quayLai.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_quayLai.setBounds(583, 266, 102, 35);
        panel_1.add(btn_quayLai);


    }

    private void initData() {
        deviceStatusRepository.getAllNotDelete().forEach(status -> {
            cbb_trangThai.addItem(status.getName());
        });

        brandRepository.getAll().forEach(brand -> {
            cbb_Hang.addItem(brand.getName());
        });

        deviceTypeRepository.getAll().forEach(type -> {
            cbb_loaiThietBi.addItem(type.getName());
        });

        //lay list theo loai thiet bi
        cbb_loaiThietBi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cbb_thongSo.removeAllItems();
                model = (DefaultTableModel) table_Chitietthongso.getModel();
                model.setRowCount(0);
                String a = cbb_loaiThietBi.getSelectedItem().toString();
                int id = deviceTypeRepository.findIdByName(a);
                List<Specification> specs = specificationRepository.findById(id);
                specs.forEach(spec -> {
                    //fill cbb
                    cbb_thongSo.addItem(spec.getName());
                    //fill table
                    model.addRow(new Object[]{
                            spec.getName()
                    });
                });

            }
        });

        //lay list theo loai thong so
        cbb_thongSo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cbb_chiTietThongSo.removeAllItems();
                int id = specificationRepository.findIdByName(cbb_thongSo.getSelectedItem().toString());
                List<SpecificationDetail> specsa = specificationDetailRepository.findById(id);
                specsa.forEach(speca -> {
                    cbb_chiTietThongSo.addItem(speca.getName());
                });
            }
        });

        btn_AddThongSo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String thongSo = cbb_thongSo.getSelectedItem().toString();
                String chiTietthongSo = cbb_chiTietThongSo.getSelectedItem().toString();
                model = (DefaultTableModel) table_Chitietthongso.getModel();
                int count = table_Chitietthongso.getRowCount();
                for (int i = 0; i < count; i++) {
                    String a = model.getValueAt(i, 0).toString();
                    if (thongSo == a) {
                        model.setValueAt(chiTietthongSo, i, 1);
                    }
                }

            }
        });

        btn_luu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String trangThai = cbb_trangThai.getSelectedItem().toString();
                String loai = cbb_loaiThietBi.getSelectedItem().toString();
                String hang = cbb_Hang.getSelectedItem().toString();
                if (testCommit() == true) {
                    Device device = new Device();
                    device.setName(txt_tenThietBi.getText());
                    device.setImage(pathFile);
                    device.setPrice(Integer.parseInt(txt_gia.getText()));
                    device.setQuantity(Integer.parseInt(txt_soLuong.getText()));
                    device.setDescription(txt_moTa.getText());
                    device.setBrandId(brandRepository.findIdByName(hang));
                    device.setDeviceStatusId(deviceStatusRepository.findIdByName(trangThai));
                    device.setDeviceTypeId(deviceTypeRepository.findIdByName(loai));
                    //update
                    if (AddDevice1.device.getId() != null) {
                        device.setId(AddDevice1.device.getId());
                        if (pathFile == null) {
                            device.setImage(AddDevice1.device.getImage());
                        } else {
                            device.setImage(pathFile);
                        }
                        deviceRepository.update(device);
                        DeviceDetail deviceDetail = new DeviceDetail();
                        for (int i = 0; i < model.getRowCount(); i++) {
                            if (model.getValueAt(i, 1).toString() == "") {
                                JOptionPane.showMessageDialog(txt_tenThietBi, "Yêu cầu nhập đủ chi tiết thiết bị");
                            } else {
                                detailRepository.deleteByDeviceId(AddDevice1.device.getId());
                            }
                        }
                        for (int i = 0; i < model.getRowCount(); i++) {
                            if (model.getValueAt(i, 1).toString() == "") {
                                JOptionPane.showMessageDialog(txt_tenThietBi, "Yêu cầu nhập đủ chi tiết thiết bị");
                            } else {
                                deviceDetail.setDeviceId(AddDevice1.device.getId());
                                deviceDetail.setSpecificationDetailId(
                                        specificationDetailRepository.findIdByName(model.getValueAt(i, 1).toString()));
                                detailRepository.insert(deviceDetail);
                            }
                        }
                        //add
                    } else {
                        deviceRepository.insert(device);
                        // Add deviceDetail
                        DeviceDetail deviceDetail = new DeviceDetail();
                        for (int i = 0; i < model.getRowCount(); i++) {
                            if (model.getValueAt(i, 1).toString() == "") {
                                JOptionPane.showMessageDialog(txt_tenThietBi, "Yêu cầu nhập đủ chi tiết thiết bị");
                            } else {
                                deviceDetail.setDeviceId(deviceRepository.findLastId());
                                deviceDetail.setSpecificationDetailId(
                                        specificationDetailRepository.findIdByName(model.getValueAt(i, 1).toString()));
                                detailRepository.insert(deviceDetail);
                            }
                        }
                    }
                    reset();
                }
            }
        });

        btn_chonAnh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ImageUtils imageUtils = new ImageUtils();
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // chi hien thi file
                int returnValue = fileChooser.showOpenDialog(lbl_Anh);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = imageUtils.savePhysicalImage(fileChooser.getSelectedFile());
                    System.out.println(file.getName());
                    // lay path file
                    pathFile = file.getName();
                    BufferedImage b;
                    try {
                        b = ImageIO.read(file);
//						lbl_Anh.setIcon(new ImageIcon(b));
                        new LoadingImageService(lbl_Anh, Resources.UPLOAD_PATH + file.getName()).execute();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        btn_quayLai.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeviceMainPanel.initMainDevicePanel();
            }
        });
        loadModifyCombobox();
        loadModifyTable();
        loadModifyImage();
    }

    private void reset() {
        txt_gia.setText("");
        txt_moTa.setText("");
        txt_soLuong.setText("");
        txt_tenThietBi.setText("");
        lbl_Anh.setIcon(null);
        model.setRowCount(0);
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

    private void loadModifyCombobox() {
        if (device.getId() != null) {
            cbb_loaiThietBi.setSelectedIndex(device.getDeviceTypeId() - 1);
            cbb_Hang.setSelectedIndex(device.getBrandId() - 1);
            cbb_trangThai.setSelectedIndex(device.getDeviceStatusId() - 1);
        }
    }

    private void loadModifyImage() {
        if (device.getId() != null) {
//			java.io.File file= new java.io.File(device.getImage());
//			BufferedImage b;
//			try {
//				b = ImageIO.read(file);
            new LoadingImageService(lbl_Anh, Resources.UPLOAD_PATH + device.getImage()).execute();
            ;
//				lbl_Anh.setIcon(new ImageIcon(b));
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
        }

    }

    public Boolean testCommit() {
        if (txt_tenThietBi.getText().equals("")) {
            JOptionPane.showMessageDialog(txt_tenThietBi, "Tên thiết bị không được bỏ trống");
            return false;
        }
        if (txt_moTa.getText().equals("")) {
            JOptionPane.showMessageDialog(txt_tenThietBi, "Mô tả không được bỏ trống");
            return false;
        }
        if (txt_soLuong.getText().equals("")) {
            JOptionPane.showMessageDialog(txt_tenThietBi, "Số lượng không được bỏ trống");
            return false;
        }
        if (txt_gia.getText().equals("")) {
            JOptionPane.showMessageDialog(txt_tenThietBi, "Giá tiền không được bỏ trống");
            return false;
        }
        if (txt_gia.getText().matches("^[a-zA-Z]+$")) {
            JOptionPane.showMessageDialog(txt_tenThietBi, "Số tiền không được ghi chữ.");
            return false;
        }
        if (txt_soLuong.getText().matches("^[a-zA-Z]+$")) {
            JOptionPane.showMessageDialog(txt_tenThietBi, "Số lượng không được ghi chữ.");
            return false;
        }
        if (cbb_Hang.getSelectedItem().toString().equals("")) {
            return false;
        }
        if (cbb_loaiThietBi.getSelectedItem().toString().equals("")) {
            return false;
        }
        if (cbb_trangThai.getSelectedItem().toString().equals("")) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        new TestFrame(new AddDevice1());

    }
}
