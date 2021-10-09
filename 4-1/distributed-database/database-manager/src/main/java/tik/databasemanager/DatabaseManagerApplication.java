package tik.databasemanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tik.databasemanager.models.*;
import tik.databasemanager.repositories.DepartmentRepos;
import tik.databasemanager.repositories.EmployeeRepos;
import tik.databasemanager.repositories.ProjectRepos;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class DatabaseManagerApplication {
    @Autowired
    DepartmentRepos departmentRepos;

    @Autowired
    EmployeeRepos employeeRepos;

    @Autowired
    ProjectRepos projectRepos;

    @PostConstruct
    public void startUp() {
        Employee leader = new Employee(
                null,
                Gender.MALE.name(),
                "LongTPT",
                BigDecimal.valueOf(5_000_000),
                "Nguyen Khoai, p2, q4, TPHCM",
                null,
                null
        );
        Employee emp1 = new Employee(
                null,
                Gender.FEMALE.name(),
                "VuLTH",
                BigDecimal.valueOf(10_000_000),
                "Vinh Cuu, Dong Nai",
                null,
                null
        );
        emp1.setEmployeeRelationships(
                List.of(
                        new EmployeeRelationship(null, "Me Vu", Gender.FEMALE.name(), "ME CUA VU", new Date(), emp1),
                        new EmployeeRelationship(null, "Cha Vu", Gender.MALE.name(), "Cha cua vu", new Date(), emp1)
                )
        );

        Department d1 = new Department(
                null,
                "HR",
                "Floor 3",
                null,
                new Date()
        );

        Department d2 = new Department(
                null,
                "DEV BACK-END",
                "Floor 3",
                null,
                new Date()
        );

        Project p1 = new Project(null, "LOS", null, "FLOOR 5", null);
        Project p2 = new Project(null, "eKYC", null, "FLOOR 4", null);

        EmployeeProject ep = new EmployeeProject(leader, p1, 1000L);
        EmployeeProject ep2 = new EmployeeProject(emp1, p1, 1000L);

//        p1.setEmployeeProjecs(List.of(ep, ep2));


        projectRepos.saveAll(List.of(p1, p2));
        employeeRepos.saveAll(List.of(leader, emp1));

        leader.setEmployeeProjects(List.of(ep, ep2));
        employeeRepos.saveAll(List.of(leader, emp1));

        d1.setLeader(leader);
        d2.setLeader(emp1);
        departmentRepos.saveAll(List.of(d1, d2));

        p1.setDepartment(d1);
        p2.setDepartment(d2);
        projectRepos.saveAll(List.of(p1, p2));
    }


    public static void main(String[] args) {
        SpringApplication.run(DatabaseManagerApplication.class, args);
    }

}

enum Gender {
    MALE, FEMALE;
}