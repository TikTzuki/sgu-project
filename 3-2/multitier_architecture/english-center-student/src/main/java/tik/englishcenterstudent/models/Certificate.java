package tik.englishcenterstudent.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "certifficate")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Certificate {
    @Id
    @NonNull
    public String id;

    @OneToMany(mappedBy = "certificate")
    public List<CertificateSequence> certificateSequences;
}
