package tik.englishcenterstudent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tik.englishcenterstudent.models.Auth;

@Repository
public interface AuthorityRepository extends JpaRepository<Auth, Integer> {

}
