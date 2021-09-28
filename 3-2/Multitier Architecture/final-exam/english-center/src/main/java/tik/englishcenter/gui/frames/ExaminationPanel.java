package tik.englishcenter.gui.frames;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import tik.englishcenter.gui.Resources;
import tik.englishcenter.gui.components.*;
import tik.englishcenter.models.Certificate;
import tik.englishcenter.models.Examination;
import tik.englishcenter.models.Room;
import tik.englishcenter.services.ExaminationSerivce;
import tik.englishcenter.services.RoomService;
import tik.englishcenter.utils.DateFormat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

@Component
public class ExaminationPanel extends TPanel {
    @Autowired
    ExaminationSerivce examinationService;

    @Autowired
    RoomService roomService;

    @Autowired
    DateFormat dateFormat;

    TFormControl<JTextField> txtExaminationName;
    TFormControl<JTextField> txtStartDate;
    TFormControl<JTextField> txtEndDate;
    TButton btnCreateExamination;

    TFormControl<TComboBox<Examination>> cbxExamination;
    TFormControl<TComboBox<Certificate>> cbxCertificate;
    TButton btnCreateRoom;

    public void init() {
        this.removeAll();
        initComp();
        initData();
    }

    private void initComp() {
        txtExaminationName = new TFormControl<>(new JTextField(), "Examination Name");
        txtStartDate = new TFormControl<>(new JTextField(), "Start Date", true);
        txtEndDate = new TFormControl<>(new JTextField(), "Start Date", true);
        btnCreateExamination = new TButton("Create examination");
        JPanel pnlAddExamination = new TPanel();

        cbxExamination = new TFormControl<>(new TComboBox<>(), "Examination");
        cbxCertificate = new TFormControl<>(new TComboBox<>(), "Certificate");
        btnCreateRoom = new TButton("Create room");
        JPanel pnlAddRoom = new TPanel();

        txtExaminationName.getPnl().setBounds(new Rectangle(10, 10, 250, 60));
        txtStartDate.getPnl().setBounds(new Rectangle(10, 80, 250, 60));
        txtEndDate.getPnl().setBounds(new Rectangle(10, 160, 250, 60));
        btnCreateExamination.setBounds(new Rectangle(30, 240, 100, 40));
        pnlAddExamination.add(txtExaminationName.getPnl());
        pnlAddExamination.add(txtStartDate.getPnl());
        pnlAddExamination.add(txtEndDate.getPnl());
        pnlAddExamination.add(btnCreateExamination);

        cbxExamination.getPnl().setBounds(new Rectangle(10, 10, 250, 60));
        cbxCertificate.getPnl().setBounds(new Rectangle(10, 80, 250, 60));
        btnCreateRoom.setBounds(new Rectangle(30, 160, 100, 40));
        pnlAddRoom.add(cbxExamination.getPnl());
        pnlAddRoom.add(cbxCertificate.getPnl());
        pnlAddRoom.add(btnCreateRoom);

        this.setLayout(new GridLayout(1, 2, 10, 10));
        this.add(pnlAddExamination);
        this.add(pnlAddRoom);
        this.setPreferredSize(Resources.MAIN_CONTENT);
        this.setBackground(Resources.C_LIGHT);
    }

    private void initData() {
        cbxExamination.getComponent().removeAllItems();
        List<Examination> examinationList = examinationService.getAllExamination();
        examinationList.forEach(ex -> {
            cbxExamination.getComponent().addItem(new TComboBoxItem<>(ex, ex.name));
        });

        examinationService.getAllCertificates().forEach(cert -> {
            cbxCertificate.getComponent().addItem(new TComboBoxItem<>(cert, cert.id));
        });


        btnCreateExamination.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String name = txtExaminationName.getComponent().getText();
                Date startDate = dateFormat.parse(txtStartDate.getComponent().getText());
                Date endDate = dateFormat.parse(txtEndDate.getComponent().getText());
                examinationService.createEmaination(new Examination(name, startDate, endDate));
                init();
            }
        });

        btnCreateRoom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Examination ex = cbxExamination.getComponent().getSelectedObject();
                Certificate certificate = cbxCertificate.getComponent().getSelectedObject();
                Room newRoom = new Room();
                newRoom.examination = ex;
                newRoom.certificate = certificate;
                roomService.createRoom(newRoom);
                init();
            }
        });
    }
}
