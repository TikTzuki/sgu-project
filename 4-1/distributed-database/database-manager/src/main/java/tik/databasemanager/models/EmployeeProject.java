package tik.databasemanager.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "EMPLOYEE_PROJECT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(EmployeeProject.class)
public class EmployeeProject implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    Employee employee;

    @Id
    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    Project project;

    Long period;
}
