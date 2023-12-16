package tik.englishcenter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tik.englishcenter.models.Auth;

@Repository
public interface AuthorityRepository extends JpaRepository<Auth, Integer> {

}
