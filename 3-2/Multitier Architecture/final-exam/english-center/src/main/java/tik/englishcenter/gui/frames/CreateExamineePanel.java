package tik.englishcenter.gui.frames;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;
import tik.englishcenter.gui.Resources;
import tik.englishcenter.gui.components.*;
import tik.englishcenter.models.Certificate;
import tik.englishcenter.models.Examination;
import tik.englishcenter.repositories.CertificateRepository;
import tik.englishcenter.services.ExaminationSerivce;
import tik.englishcenter.services.ExamineeService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import tik.englishcenter.models.Certificate;

@Component
public class CreateExamineePanel extends TPanel {
    @Autowired
    ExamineeService examineeService;
    @Autowired
    ExaminationSerivce examinationSerivce;
    @Autowired
    CertificateRepository certificateRepository;

    TFormControl<TComboBox<Examination>> cbxExamination;
    TFormControl<TComboBox<String>> cbxCertificate;
    TFormControl<JTextField> txtRowAdd;
    TButton btnAddRow;
    TButton btnRemoveRow;

    TTable tblExaminee;
    JScrollPane scroller;
    TButton btnCreate;

    @Override
    public void init() {
        this.removeAll();
        this.initComp();
        this.initData();
    }

    private void initComp() {
        cbxExamination = new TFormControl<>(new TComboBox<>(), "Examination");
        cbxCertificate = new TFormControl<>(new TComboBox<>(), "Certificate");
        txtRowAdd = new TFormControl<>(new JTextField(), "Row");
        btnAddRow = new TButton("Add");
        btnRemoveRow = new TButton("Remove last row");

        tblExaminee = new TTable(examineeService.initTableModel());
        scroller = new JScrollPane(tblExaminee);
        btnCreate = new TButton("Create");

        cbxExamination.getPnl().setBounds(new Rectangle(50, 10, 250, 60));
        cbxCertificate.getPnl().setBounds(new Rectangle(300, 10, 250, 60));
        txtRowAdd.getPnl().setBounds(new Rectangle(560, 10, 200, 60));
        btnAddRow.setBounds(new Rectangle(770, 20, 100, 40));
        btnRemoveRow.setBounds(new Rectangle(880, 20, 200, 40));

        scroller.setBounds(new Rectangle(50, 80, 600, 300));
        btnCreate.setBounds(new Rectangle(80, 480, 100, 40));

        this.setLayout(null);
        this.add(cbxExamination.getPnl());
        this.add(cbxCertificate.getPnl());
        this.add(txtRowAdd.getPnl());
        this.add(btnAddRow);
        this.add(btnRemoveRow);
        this.add(scroller);
        this.add(btnCreate);
        this.setPreferredSize(Resources.MAIN_CONTENT);
        this.setBackground(Resources.C_LIGHT);
    }

    private void initData() {
        examinationSerivce.getAllExamination().forEach(ex -> {
            cbxExamination.getComponent().addItem(new TComboBoxItem<>(ex, ex.name));
        });

        certificateRepository.findAll().forEach(cert -> {
            cbxCertificate.getComponent().addItem(new TComboBoxItem<>(cert.id, cert.id));
        });

        btnAddRow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    int newRowCount = Integer.parseInt(txtRowAdd.getComponent().getText());
                    for (int i = 0; i < newRowCount; i++) {
                        tblExaminee.getModel().addRow(new Object[]{null, null, null, null});
                    }
                } catch (NumberFormatException ex) {
                    new JOptionPane("Only number").setVisible(true);
                    return;
                }
            }
        });

        btnRemoveRow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (tblExaminee.getModel().getRowCount() > 1)
                    tblExaminee.getModel().removeRow(tblExaminee.getModel().getRowCount() - 1);
            }
        });

        btnCreate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TableModel model = tblExaminee.getModel();
                String certificateId = cbxCertificate.getComponent().getSelectedObject();
                Examination examination = cbxExamination.getComponent().getSelectedObject();
                examineeService.createExaminee(model, certificateId, examination);
            }
        });
    }
}
