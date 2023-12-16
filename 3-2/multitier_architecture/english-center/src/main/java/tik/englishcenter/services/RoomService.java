package tik.englishcenter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Service;
import tik.englishcenter.models.*;
import tik.englishcenter.repositories.CertificateRepository;
import tik.englishcenter.repositories.ExaminationRepository;
import tik.englishcenter.repositories.ExamineeRepository;
import tik.englishcenter.repositories.RoomRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    public static final String LOG = "RUN_TEST" + RoomService.class.getName();
    @Autowired
    RoomRepository repository;

    @Autowired
    ExaminationRepository examinationRepository;

    @Autowired
    ExamineeRepository examineeRepository;

    @Autowired
    CertificateRepository certificateRepository;

    public Room createRoom(Room room) {
        Optional<Examination> ex = examinationRepository.findById(room.examination.id);
        if (!ex.isPresent()) {
            return null;
        }
        Examination examination = ex.get();

        for (CertificateSequence seq : examination.certificateSequences) {
            if (seq.certificate.id.equals(room.certificate.id)) {
                seq.roomSequence++;
                room.setName(String.format("%sP%2$3s", room.certificate.id, seq.roomSequence).replace(' ', '0'));
            }
        }

        repository.save(room);
        examinationRepository.save(examination);
        return room;
    }

    public Room getLastRoom() {
        Room room = repository.findTopByOrderByIdDesc();
        if (room == null) {
            throw new HttpMessageConversionException("No room Not Found!");
        }
        return room;
    }

    public Room getRoom(Integer roomId) {
        Optional<Room> roomOptional = repository.findById(roomId);
        if (!roomOptional.isPresent())
            throw new HttpMessageConversionException(String.format("Examination %s Not Found!", roomId));

        return roomOptional.get();
    }

    public List<Room> getRoomsByExaminationId(Integer examinationId) {
        return repository.findByExaminationId(examinationId);
    }

    public DefaultTableModel initTableModel() {
        return new DefaultTableModel(new Object[]{"#", "Name", "Phone", "Gender", "Indetity", "Certificate Id", "Listenning", "Writing", "Speaking", "Reading"}, 0);
    }

    public DefaultTableModel loadRoom(Integer roomId) {
        DefaultTableModel model = this.initTableModel();
        Optional<Room> roomOptional = repository.findById(roomId);
        if (!roomOptional.isPresent()) {
            new JOptionPane("This room is not present").setVisible(true);
            return model;
        }
        Room room = roomOptional.get();
        flushExamineeModel(room.examinees, model);
        return model;
    }

    public List<Examinee> searchExaminees(String name, String phone) {
        List<Examinee> examinees = examineeRepository.searchByPhoneAndName(phone, name);
        return examinees;
    }

    public DefaultTableModel searchAndLoadExaminees(String name, String phone) {
        DefaultTableModel model = initTableModel();
        if (name.isEmpty()) {
            name = null;
        }
        if (phone.isEmpty()) {
            phone = null;
        }
        flushExamineeModel(searchExaminees(name, phone), model);
        return model;
    }

    private void flushExamineeModel(List<Examinee> examinees, DefaultTableModel model) {
        examinees.forEach(examinee -> {
            model.addRow(new Object[]{examinee.examineeId, examinee.name, examinee.phone, examinee.gender, examinee.identity, examinee.certificateId, examinee.listenning, examinee.writing, examinee.speaking, examinee.reading});
        });
    }

    public void saveResult(TableModel model) {
        List<Examinee> examinees = new ArrayList<>();
        for (int row = 0; row < model.getRowCount(); row++) {
            Examinee examinee = examineeRepository.findByExamineeId(model.getValueAt(row, 0).toString());
            try {
                Float listening = Float.valueOf(model.getValueAt(row, 6).toString());
                Float writing = Float.valueOf(model.getValueAt(row, 7).toString());
                Float speaking = Float.valueOf(model.getValueAt(row, 8).toString());
                Float reading = Float.valueOf(model.getValueAt(row, 9).toString());
                examinee.listenning = listening;
                examinee.writing = writing;
                examinee.speaking = speaking;
                examinee.reading = reading;
                examinees.add(examinee);
            } catch (NumberFormatException ex) {
                new JOptionPane("Only number").setVisible(true);
                return;
            }
        }
        examinees.forEach(examinee -> examineeRepository.save(examinee));
    }
}
