package tiktzuki.e_store.GUI.frames.customer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JTextFieldDateEditor;

import tiktzuki.e_store.DAL.CustomerRepository;
import tiktzuki.e_store.DTO.Customer;
import tiktzuki.e_store.GUI.customcomponents.MessageDialog;
import tiktzuki.e_store.GUI.customcomponents.TButton;
import tiktzuki.e_store.GUI.customcomponents.TFormControl;

public class CustomerCreateDialog {
    CustomerRepository cusRepos = new CustomerRepository();
    JDialog dialog;
    JPanel pnlBackground;

    TFormControl<JTextField> txtName = new TFormControl<JTextField>(new JTextField(), "Tên");
    TFormControl<JTextField> txtAddress = new TFormControl<JTextField>(new JTextField(), "Địa chỉ");
    TFormControl<JTextField> txtPhone = new TFormControl<JTextField>(new JTextField(), "Số điện thoại");

    TButton btnConfirm;
    TButton btnCancle;

    public CustomerCreateDialog() {
        dialog = new JDialog();
        dialog.setModal(true);

        btnConfirm.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (validateForm()) {
                    Customer cus = commitCustomerForm();
                    cusRepos.insert(cus);
                }
            }

            ;
        });
        btnCancle.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dialog.dispose();
            }
        });

        pnlBackground.add(txtName.getPnl());
        pnlBackground.add(txtAddress.getPnl());
        pnlBackground.add(txtPhone.getPnl());

        dialog.setContentPane(pnlBackground);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
    }

    private boolean validateForm() {
        if (txtName.getComponent().getText().equals("") || txtAddress.getComponent().getText().equals("") || txtPhone.getComponent().getText().equals("")) {
            new MessageDialog("Khong duoc de trong thong tin");
            return false;
        }
        return true;
    }

    private Customer commitCustomerForm() {
        Customer cus = new Customer();
        cus.setName(txtName.getComponent().getText());
        cus.setUsername(System.currentTimeMillis() + "" + Math.round((Math.random() * 1000)));
        cus.setPassword(System.currentTimeMillis() + "" + Math.round((Math.random() * 1000)));
        cus.setEmail(System.currentTimeMillis() + "" + Math.round((Math.random() * 1000)));
        cus.setAddress(txtAddress.getComponent().getText());
        cus.setPhone(txtPhone.getComponent().getText());
        return cus;
    }
}
