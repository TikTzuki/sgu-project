package tik.englishcenterstudent.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "manager")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Manager {
    @Id
    @GeneratedValue
    public Integer id;

    @NonNull
    public String username;

    @NonNull
    public String phone;

    @NonNull
    public String gender;

    @NonNull
    public String identity;

    @NonNull
    public String password;

    @OneToMany(mappedBy = "manager")
    public List<Examinee> examinees;
}
