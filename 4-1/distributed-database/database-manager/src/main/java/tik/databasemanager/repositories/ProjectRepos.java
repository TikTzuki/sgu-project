package tik.databasemanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tik.databasemanager.models.Project;

@Repository
public interface ProjectRepos extends JpaRepository<Project, Integer> {
}
