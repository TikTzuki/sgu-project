package tik.englishcenter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tik.englishcenter.models.CertificateSequence;

@Repository
public interface CertificateSequenceRepository extends JpaRepository<CertificateSequence, Integer> {
}
