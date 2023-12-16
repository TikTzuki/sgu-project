package tik.englishcenter.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name = "certifficate")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
public class Certificate {
    //    @Id
//    @GeneratedValue
//    public Integer id;
    @Id
//    @NonNull
    public String id;
}
