package tik.englishcenter.gui.frames;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tik.englishcenter.gui.components.*;
import tik.englishcenter.models.Examination;
import tik.englishcenter.models.Room;
import tik.englishcenter.services.ExaminationSerivce;
import tik.englishcenter.services.ExamineeService;
import tik.englishcenter.services.RoomService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.List;

@Component
public class RoomPanel extends TPanel {
    @Autowired
    RoomService roomService;
    @Autowired
    ExaminationSerivce examinationSerivce;

    TFormControl<TComboBox<Examination>> cbxExamination;
    TFormControl<JTextField> txtRoomSize;
    TButton btnOrderExaminee;

    TFormControl<TComboBox<Room>> cbxRoom;
    TButton btnLoadRoom;

    TFormControl<JTextField> txtPhone;
    TFormControl<JTextField> txtName;
    TButton btnSearch;

    TTable tblExaminee;
    JScrollPane scollerExaminee;

    TButton btnSaveResult;

    @Override
    public void init() {
        this.init(null, null);
    }

    public void init(Integer examinationId, Integer roomId) {
        this.removeAll();
        initComp();
        initData(examinationId, roomId);
    }

    private void initComp() {
        cbxExamination = new TFormControl<>(new TComboBox<>(), "Examination");
        txtRoomSize = new TFormControl<>(new JTextField(), "Room size");
        btnOrderExaminee = new TButton("Order Examination's Rooms");

        cbxRoom = new TFormControl<>(new TComboBox<>(), "Room");
        btnLoadRoom = new TButton("Load Room");

        txtPhone = new TFormControl<>(new JTextField(), "Phone number");
        txtName = new TFormControl<>(new JTextField(), "Name");
        btnSearch = new TButton("Search");
        TPanel pnlSearch = new TPanel("Search");

        tblExaminee = new TTable() {
            @Override
            public boolean editCellAt(int row, int column, EventObject e) {
                if (column > 5)
                    return super.editCellAt(row, column, e);
                return false;
            }

            ;
        };
        scollerExaminee = new JScrollPane(tblExaminee);

        btnSaveResult = new TButton("Save result");

        cbxExamination.getPnl().setBounds(20, 10, 250, 60);
        txtRoomSize.getPnl().setBounds(280, 10, 200, 60);
        btnOrderExaminee.setBounds(500, 20, 180, 40);

        cbxRoom.getPnl().setBounds(new Rectangle(20, 80, 250, 60));
        btnLoadRoom.setBounds(new Rectangle(300, 90, 120, 40));

        txtPhone.getPnl().setBounds(new Rectangle(10, 20, 200, 60));
        txtName.getPnl().setBounds(new Rectangle(10, 90, 200, 60));
        btnSearch.setBounds(new Rectangle(220, 60, 100, 40));
        pnlSearch.setBounds(new Rectangle(710, 10, 390, 160));
        pnlSearch.add(txtPhone.getPnl());
        pnlSearch.add(txtName.getPnl());
        pnlSearch.add(btnSearch);

        scollerExaminee.setBounds(new Rectangle(20, 200, 1000, 400));

        btnSaveResult.setBounds(new Rectangle(500, 620, 160, 40));
        this.add(cbxExamination.getPnl());
        this.add(txtRoomSize.getPnl());
        this.add(btnOrderExaminee);
        this.add(cbxRoom.getPnl());
        this.add(btnLoadRoom);
        this.add(pnlSearch);
        this.add(scollerExaminee);
        this.add(btnSaveResult);
    }

    private void initData(Integer examinationId, Integer roomId) {
        List<Examination> examinations = examinationSerivce.getAllExamination();
//        Examination combobox
        examinations.forEach(ex -> {
            TComboBoxItem<Examination> item = new TComboBoxItem<>(ex, ex.name);
            cbxExamination.getComponent().addItem(item);
            if (ex.id.equals(examinationId))
                cbxExamination.getComponent().setSelectedItem(item);
        });
        if (examinations.size() > 0) {
            Examination lastExamination = cbxExamination.getComponent().getSelectedObject();
//            Room combobox
            lastExamination.rooms.forEach(room -> {
                TComboBoxItem<Room> item = new TComboBoxItem<>(room, room.name);
                cbxRoom.getComponent().addItem(item);
                if (room.id.equals(roomId))
                    cbxRoom.getComponent().setSelectedItem(item);
            });

//            Examinee table
            if (lastExamination.rooms.size() > 0) {
                tblExaminee.setModel(roomService.loadRoom(
                        cbxRoom.getComponent().getSelectedObject().id
                ));
            }
        }

        cbxExamination.getComponent().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbxRoom.getComponent().removeAllItems();
                Examination currentEx = cbxExamination.getComponent().getSelectedObject();
                currentEx.rooms.forEach(room -> {
                    cbxRoom.getComponent().addItem(new TComboBoxItem<>(room, room.name));
                });
            }
        });

        btnOrderExaminee.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Integer exId = cbxExamination.getComponent().getSelectedObject().id;
                String roomSizeString = txtRoomSize.getComponent().getText().toString();
                if (roomSizeString.isEmpty()) {
                    new JOptionPane("Room size is required").setVisible(true);
                    return;
                }
                Integer roomSize = Integer.valueOf(roomSizeString);
                examinationSerivce.orderExaminees(exId, roomSize);
                init();
            }
        });

        btnLoadRoom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Integer examinationId = cbxExamination.getComponent().getSelectedObject().id;
                Integer roomId = cbxRoom.getComponent().getSelectedObject().id;
                init(examinationId, roomId);
            }
        });

        btnSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String name = txtName.getComponent().getText();
                String phone = txtPhone.getComponent().getText();
                DefaultTableModel model = roomService.searchAndLoadExaminees(name, phone);
                tblExaminee.setModel(model);
            }
        });

        btnSaveResult.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                roomService.saveResult(tblExaminee.getModel());
                init();
            }
        });
    }
}
