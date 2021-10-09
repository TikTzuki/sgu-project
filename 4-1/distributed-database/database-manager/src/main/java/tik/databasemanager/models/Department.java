package tik.databasemanager.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "DEPARTMENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue
    Integer id;

    String name;

    String location;

    @OneToOne
    @JoinColumn(name = "LEADER_ID", nullable = false)
    Employee leader;

    Date promoteDate;
}
