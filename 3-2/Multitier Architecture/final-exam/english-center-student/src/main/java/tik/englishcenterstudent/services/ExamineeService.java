package tik.englishcenterstudent.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Service;
import tik.englishcenterstudent.models.Examinee;
import tik.englishcenterstudent.models.Room;
import tik.englishcenterstudent.repositories.ExaminationRepository;
import tik.englishcenterstudent.repositories.ExamineeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamineeService {
    @Autowired
    ExamineeRepository repository;

    @Autowired
    ExaminationRepository examinationRepository;

    public List<Examinee> createExaminee(List<Examinee> examinees) {
        for (Examinee examinee : examinees) {
            examinee.examination = examinationRepository.findById(examinee.examination.id).get();
            List<Examinee> ex = repository.findByIdentityOrPhoneAndExaminationId(examinee.identity, examinee.phone, examinee.examination.id);
            if (ex.size() != 0)
                throw new HttpMessageConversionException(String.format("%s, %s already exist!", examinee.identity, examinee.phone));
        }
        return repository.saveAll(examinees);
    }

    public List<Examinee> getByRoomId(Integer roomId) {
        return repository.findByRoomId(roomId);
    }

    public List<Room> getRoomOfExaminee(String name, String phone) {
        List<Examinee> examinees = repository.searchByPhoneAndName(phone, name);
        return examinees.stream().map(examinee -> examinee.room).collect(Collectors.toList());
    }

    public Examinee getByExamineeId(String id) {
        return repository.findByExamineeId(id).orElseGet(Examinee::new);
    }
}
