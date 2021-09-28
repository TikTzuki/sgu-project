package tik.englishcenterstudent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tik.englishcenterstudent.models.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    Manager findByUsernameAndPassword(String username, String password);
}
