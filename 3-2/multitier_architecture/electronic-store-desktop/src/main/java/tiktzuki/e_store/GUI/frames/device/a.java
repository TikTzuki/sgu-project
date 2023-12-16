package tiktzuki.e_store.GUI.frames.device;

import javax.swing.JFrame;
import javax.swing.JPanel;

import tiktzuki.e_store.DAL.BrandRepository;
import tiktzuki.e_store.DAL.DeviceRepository;
import tiktzuki.e_store.DAL.DeviceTypeRepository;
import tiktzuki.e_store.DTO.Device;
import tiktzuki.e_store.GUI.MainFrame;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.customcomponents.TTable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class a extends JPanel {
    private JTextField txt_timKiem;
    private JButton btn_them = new JButton("Tạo mới");
    private JButton btn_sua = new JButton("Cập nhật");
    private JButton btn_xoa;
    public static JPanel mainContent;
    public DeviceRepository deviceRepository = new DeviceRepository();
    public DeviceTypeRepository deviceTypeRepository = new DeviceTypeRepository();
    public BrandRepository brandRepository = new BrandRepository();
    private List<Device> devices = new ArrayList<Device>();
    DefaultTableModel model;
    JScrollPane scrollPane;
    private JPanel panel;
    private JTable table;
    private JButton btn_xem;
    private JButton btn_tim;

    /**
     * Create the panel.
     */
    public a() {

        //=========================================
        initComp();
        initData();

    }

    private void initData() {
        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        devices = deviceRepository.findAll();
        devices.forEach(device -> {
            model.addRow(new Object[]{
                    device.getId(), device.getName(), deviceTypeRepository.findNameById(device.getDeviceTypeId()), device.getPrice(),
                    device.getQuantity(), brandRepository.findNameById(device.getBrandId())
            });
        });
        btn_them.setBackground(Color.BLUE);
        btn_them.setBounds(539, 23, 103, 35);
        panel.add(btn_them);
        btn_sua.setBackground(Color.BLUE);
        btn_sua.setBounds(690, 23, 103, 35);
        panel.add(btn_sua);

        btn_xoa = new JButton("Xóa");
        btn_xoa.setBackground(Color.BLUE);
        btn_xoa.setBounds(839, 23, 101, 35);
        panel.add(btn_xoa);
        btn_xoa.setForeground(Color.WHITE);
        btn_xoa.setFont(new Font("Times New Roman", Font.BOLD, 14));

        btn_xem = new JButton("Chi tiết");
        btn_xem.setForeground(Color.WHITE);
        btn_xem.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_xem.setBackground(Color.BLUE);
        btn_xem.setBounds(987, 22, 103, 35);
        panel.add(btn_xem);

        btn_tim = new JButton("Tìm");
        btn_tim.setForeground(Color.WHITE);
        btn_tim.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_tim.setBackground(Color.BLUE);
        btn_tim.setBounds(398, 22, 70, 35);
        panel.add(btn_tim);
        //Chuyen slide modify device
        btn_sua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (DeviceMainPanel.selectedDevice != null && DeviceMainPanel.selectedDevice.getId() != null) {
                    DeviceMainPanel.initModifyDevice();
                }
            }
        });
        //Chuyen slide Add device
        btn_them.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeviceMainPanel.initAddDevicePanel();
            }
        });

//		btn_xoa.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			 deviceRepository.deleteByDeviceId(Integer.parseInt(model.getValueAt(table.getSelectedRow(), 0).toString()));
//			}
//		});

        btn_xem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (DeviceMainPanel.selectedDevice != null && DeviceMainPanel.selectedDevice.getId() != null) {
                    DeviceMainPanel.initDetailDevice();
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selectedId = (Integer) table.getValueAt(table.getSelectedRow(), 0);

                for (Device device : DeviceMainPanel.devices) {
                    if (device.getId() == selectedId) {
                        DeviceMainPanel.selectedDevice = device;
                    }
                }
            }
        });
    }

    private void initComp() {
        //--------
        setBorder(new LineBorder(new Color(0, 0, 0)));
        this.setBackground(Color.WHITE);
        setLayout(null);

        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 0, 1100, 85);
        panel.setBorder(new LineBorder(new Color(0, 0, 0)));
        add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Tìm kiếm");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblNewLabel.setBounds(36, 29, 84, 23);
        panel.add(lblNewLabel);

        txt_timKiem = new JTextField();
        txt_timKiem.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txt_timKiem.setBounds(112, 22, 276, 35);
        panel.add(txt_timKiem);
        txt_timKiem.setColumns(10);


        btn_them.setForeground(Color.WHITE);
        btn_them.setFont(new Font("Times New Roman", Font.BOLD, 14));
        setPreferredSize(new Dimension(1100, 700));

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.WHITE);
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1.setBounds(0, 86, 1100, 614);
        add(panel_1);
        panel_1.setLayout(null);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 11, 1100, 592);
        panel_1.add(scrollPane);
        scrollPane.setPreferredSize(new Dimension(1100, 300));

        table = new TTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null, null},
                },
                new String[]{
                        "Id", "T\u00EAn thi\u1EBFt B\u1ECB", "Lo\u1EA1i thi\u1EBFt b\u1ECB", "Gi\u00E1", "S\u1ED1 l\u01B0\u1EE3ng", "H\u00E3ng"
                }
        ) {
            boolean[] columnEditables = new boolean[]{
                    true, false, true, false, true, false
            };

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        scrollPane.setViewportView(table);


        btn_sua.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_sua.setForeground(Color.WHITE);

    }

    public static void main(String[] args) {
        new TestFrame(new a());

    }
}
