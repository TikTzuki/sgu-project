package tik.englishcenter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tik.englishcenter.models.Manager;
import tik.englishcenter.repositories.ManagerRepository;

@Service
public class ManagerService {
    @Autowired
    ManagerRepository repository;
    public Manager CURRENT_USER;

    public Manager login(String username, char[] password) {
        return repository.findByUsernameAndPassword(username, String.valueOf(password));
    }
}
