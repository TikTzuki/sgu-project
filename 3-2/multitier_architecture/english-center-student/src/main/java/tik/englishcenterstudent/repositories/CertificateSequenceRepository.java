package tik.englishcenterstudent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tik.englishcenterstudent.models.CertificateSequence;

@Repository
public interface CertificateSequenceRepository extends JpaRepository<CertificateSequence, Integer> {
}
