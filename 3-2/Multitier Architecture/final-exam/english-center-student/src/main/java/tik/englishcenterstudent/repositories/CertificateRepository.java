package tik.englishcenterstudent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tik.englishcenterstudent.models.Certificate;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer>{

}
