package tik.databasemanager.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "EMPLOYEE_RELATIONSHIP")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRelationship {
    @Id
    @GeneratedValue
    Integer id;

    String name;
    String gender;
    String relationship;
    Date dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    Employee employee;
}
