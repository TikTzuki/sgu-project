package tik.englishcenterstudent.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Service;
import tik.englishcenterstudent.models.Certificate;
import tik.englishcenterstudent.models.CertificateSequence;
import tik.englishcenterstudent.models.Examination;
import tik.englishcenterstudent.models.Room;
import tik.englishcenterstudent.repositories.ExaminationRepository;
import tik.englishcenterstudent.repositories.RoomRepository;

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
    List<Certificate> certificates;

    public Room createRoom(Room room) {
        Optional<Examination> ex = examinationRepository.findById(room.examination.id);
        if (!ex.isPresent()) {
            return null;
        }
        Examination examination = ex.get();

        Optional<CertificateSequence> certificateSequenceOptional = examination.certificateSequences
                .stream().filter(seq -> seq.certificate.id.equals(room.certificate.id)).findAny();
        if (!certificateSequenceOptional.isPresent())
            return null;
        Optional<Certificate> certificateOptional = certificates.stream().filter(cert -> cert.id.equals(room.certificate.id)).findAny();
        if (!certificateOptional.isPresent())
            return null;

        CertificateSequence sequence = certificateSequenceOptional.get();
        sequence.roomSequence++;

        Certificate cert = certificateOptional.get();

        room.setName(String.format("%sP%2$3s", cert.id, sequence.roomSequence).replace(' ', '0'));

        System.out.println(examination);
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
}
