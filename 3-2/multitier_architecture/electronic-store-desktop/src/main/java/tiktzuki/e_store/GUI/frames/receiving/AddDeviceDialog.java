package tiktzuki.e_store.GUI.frames.receiving;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import tiktzuki.e_store.DAL.DeviceRepository;
import tiktzuki.e_store.GUI.customcomponents.TTable;

public class AddDeviceDialog extends JDialog {
    private JTable table;
    private JButton btn_chon;
    private JButton cancelButton;
    DefaultTableModel model;
    private DeviceRepository deviceRepository = new DeviceRepository();
    private final JPanel contentPanel = new JPanel();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            AddDeviceDialog dialog = new AddDeviceDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public AddDeviceDialog() {
        initComp();
        initData();
    }

    private void initComp() {
        setBounds(100, 100, 500, 300);
        getContentPane().setLayout(null);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setBackground(Color.WHITE);
            buttonPane.setBounds(0, 228, 484, 33);
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane);
            {
                btn_chon = new JButton("Chọn");
                btn_chon.setForeground(Color.WHITE);
                btn_chon.setBackground(Color.BLUE);
                btn_chon.setFont(new Font("Times New Roman", Font.BOLD, 12));
                btn_chon.setActionCommand("OK");
                buttonPane.add(btn_chon);
                getRootPane().setDefaultButton(btn_chon);
            }
            {
                cancelButton = new JButton("Thoát");
                cancelButton.setBackground(Color.BLUE);
                cancelButton.setForeground(Color.WHITE);
                cancelButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
        {
            JPanel panel = new JPanel();
            panel.setBounds(0, 0, 484, 229);
            getContentPane().add(panel);
            panel.setLayout(null);
            {
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.getVerticalScrollBar().setBackground(Color.WHITE);

                scrollPane.setBounds(0, 0, 484, 229);
                panel.add(scrollPane);
                {
                    table = new TTable();
                    table.setModel(new DefaultTableModel(
                            new Object[][]{
                                    {null, null},
                            },
                            new String[]{
                                    "Id", "T\u00EAn thi\u1EBFt b\u1ECB"
                            }
                    ) {
                        boolean[] columnEditables = new boolean[]{
                                false, false
                        };

                        public boolean isCellEditable(int row, int column) {
                            return columnEditables[column];
                        }
                    });
                    table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                    scrollPane.setViewportView(table);
                }
            }
        }
    }

    private void initData() {
        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        deviceRepository.findAll().forEach(device -> {
            model.addRow(new Object[]{
                    device.getId(), device.getName()
            });
        });

        btn_chon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = table.getSelectedRow();
                    AddReceivingPanel.setTenThietBi(model.getValueAt(index, 1).toString());
                    AddReceivingPanel.setMaThietBi(model.getValueAt(index, 0).toString());
                    dispose();
                } catch (IndexOutOfBoundsException ez) {
                    JOptionPane.showMessageDialog(btn_chon, "Bạn chưa chọn nhà cung cấp");
                }

            }
        });
    }
}
