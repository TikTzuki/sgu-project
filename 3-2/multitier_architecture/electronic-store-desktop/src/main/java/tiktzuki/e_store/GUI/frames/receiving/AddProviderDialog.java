package tiktzuki.e_store.GUI.frames.receiving;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tiktzuki.e_store.DAL.ProviderRepository;
import tiktzuki.e_store.GUI.customcomponents.TTable;

public class AddProviderDialog extends JDialog {
    private JTable table;
    private JButton btn_chon;
    private JButton cancelButton;
    DefaultTableModel model;
    private ProviderRepository providerRepository = new ProviderRepository();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            AddProviderDialog dialog = new AddProviderDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public AddProviderDialog() {
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
                                    "Id", "T\u00EAn nh\u00E0 cung c\u1EA5p"
                            }
                    ) {
                        boolean[] columnEditables = new boolean[]{
                                false, true
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
        providerRepository.findAll().forEach(provider -> {
            model.addRow(new Object[]{
                    provider.getId(), provider.getName()
            });
        });

        btn_chon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = table.getSelectedRow();
                    AddReceivingPanel.setNhaCungCap(model.getValueAt(index, 1).toString());
                    dispose();
                } catch (IndexOutOfBoundsException ez) {
                    JOptionPane.showMessageDialog(btn_chon, "Bạn chưa chọn nhà cung cấp");
                }

            }
        });
    }
}
