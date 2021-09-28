package tik.englishcenterstudent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tik.englishcenterstudent.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    void deleteByUsername(String username);

    Optional<User> findByUsernameOrPhoneOrIdentity(String username, String phone, String identity);
}
