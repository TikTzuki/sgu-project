package tik.englishcenterstudent.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Service;
import tik.englishcenterstudent.models.*;
import tik.englishcenterstudent.repositories.CertificateRepository;
import tik.englishcenterstudent.repositories.ExaminationRepository;
import tik.englishcenterstudent.repositories.RoomRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExaminationSerivce {
    @Autowired
    ExaminationRepository repository;

    @Autowired
    CertificateRepository certificateRepository;

    @Autowired
    RoomRepository roomRepository;

    public List<Certificate> createCertificates(List<Certificate> certificates) {
        certificateRepository.saveAll(certificates);
        return certificates;
    }

    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }

    public Examination createEmaination(Examination examination) {
        examination.setCertificateSequences(this.getAllCertificates().stream().map(cert -> new CertificateSequence(
                cert,
                examination
        )).collect(Collectors.toList()));

        repository.save(examination);

        return examination;
    }

    public List<Examination> getAllExamination() {
        return repository.findAllByOrderByIdDesc();
    }

    public void orderExaminees(Integer examinationId, Integer roomSize) {
        Optional<Examination> examinationOptional = repository.findById(examinationId);
        if (!examinationOptional.isPresent())
            throw new HttpMessageConversionException(String.format("Examination %s Not Found!", examinationId));
        Examination ex = examinationOptional.get();
        if (ex.examinees.size() == 0)
            throw new HttpMessageConversionException("There no examinee!");

        for (CertificateSequence certificateSequence : ex.certificateSequences) {
            List<Examinee> sortedExaminees = ex.examinees.stream()
                    .filter(examinee -> examinee.certificateId.equals(certificateSequence.certificate.id))
                    .sorted(new Comparator<Examinee>() {
                        @Override
                        public int compare(Examinee o1, Examinee o2) {
                            String[] o1WordName = o1.name.split(" ");
                            String[] o2WordName = o2.name.split(" ");
                            return o1WordName[o1WordName.length - 1].compareToIgnoreCase(o2WordName[o2WordName.length - 1]);
                        }
                    }).collect(Collectors.toList());

            int cursor = 0;
            int offset = 0;
            for (Room room : ex.rooms.stream().filter(r -> r.certificate.id.equals(certificateSequence.certificate.id)).collect(Collectors.toList())) {

                CertificateSequence sequence = ex.certificateSequences.stream().filter(seq -> seq.certificate.id.equals(room.certificate.id)).findAny().get();

                while (cursor < (offset + 1) * roomSize) {
                    Examinee examinee = sortedExaminees.get(cursor);
                    examinee.examineeId = String.format("%s%2$3s", room.certificate.id, sequence.examineeSequence).replace(" ", "0");
                    examinee.room = room;
                    room.examinees.add(examinee);
                    sequence.examineeSequence++;
                    cursor++;
                }
                offset++;
            }
        }

        roomRepository.saveAll(ex.rooms);
    }
}
