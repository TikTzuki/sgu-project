package tik.englishcenter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import tik.englishcenter.models.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    public Manager findByUsernameAndPassword(String username, String password);
}
