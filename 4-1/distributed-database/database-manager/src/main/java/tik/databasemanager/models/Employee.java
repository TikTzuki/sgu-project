package tik.databasemanager.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "EMPLOYEE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue
    Integer id;

    String gender;

    String name;

    BigDecimal salary;

    String address;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    List<EmployeeRelationship> employeeRelationships;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    List<EmployeeProject> employeeProjects;
}
