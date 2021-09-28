package tik.englishcenter.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.*;

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
