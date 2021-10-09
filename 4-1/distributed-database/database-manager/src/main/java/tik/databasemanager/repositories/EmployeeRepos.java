package tik.databasemanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tik.databasemanager.models.Employee;

@Repository
public interface EmployeeRepos extends JpaRepository<Employee, Integer> {
}
