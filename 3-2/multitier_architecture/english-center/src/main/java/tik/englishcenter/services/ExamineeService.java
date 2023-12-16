package tik.englishcenter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.annotation.Validated;
import tik.englishcenter.constants.Messages;
import tik.englishcenter.models.Examination;
import tik.englishcenter.models.Examinee;
import tik.englishcenter.repositories.ExamineeRepository;
import tik.englishcenter.repositories.RoomRepository;
import tik.englishcenter.utils.ExceptionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Validated
public class ExamineeService {
    @Autowired
    ExamineeRepository repository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    private ExceptionHandler handler;

    private Validator validator;

    public ExamineeService(Validator validator) {
        this.validator = validator;
    }

    public List<Examinee> createExaminee(List<Examinee> examinees, Integer examinationId) {
        for (Examinee examinee : examinees) {
            List<Examinee> ex = repository.findByIdentityOrPhoneAndExaminationId(examinee.identity, examinee.phone, examinationId);
            if (ex.size() != 0)
                throw new HttpMessageConversionException(String.format("%s, %s already exist!", examinee.identity, examinee.phone));
        }
        return repository.saveAll(examinees);
    }

    public List<Examinee> createExaminee(TableModel model, String certificateId, Examination examination) {
        System.out.println(model);
        List<Examinee> examinees = new ArrayList<>();
        for (int row = 0; row < model.getRowCount(); row++) {
            try {
                String name = model.getValueAt(row, 0).toString();
                String phone = model.getValueAt(row, 1).toString();
                String gender = model.getValueAt(row, 2).toString();
                String identity = model.getValueAt(row, 3).toString();
                Examinee examinee = new Examinee(name, phone, gender, identity, examination, certificateId);
                handler.handle(validator.validate(examinee));
                examinees.add(examinee);
            } catch (NullPointerException exception) {
                new JOptionPane(Messages.CANNOT_EMPTY).createDialog("Error").setVisible(true);
                throw exception;
            }
        }
        return repository.saveAll(examinees);
    }

    public List<Examinee> getByRoomId(Integer roomId) {
        return repository.findByRoomId(roomId);
    }

    public DefaultTableModel initTableModel() {
        return new DefaultTableModel(new Object[]{"Name", "Phone", "Gender", "Identity"}, 1);
    }

}
