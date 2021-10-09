package tik.databasemanager.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PROJECT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue
    Integer id;

    String name;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    Department department;

    String location;

    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST)
    List<EmployeeProject> employeeProjecs;
}
