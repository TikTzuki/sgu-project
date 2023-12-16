package tik.englishcenterstudent.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "certificate_sequence")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class CertificateSequence implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -1591517318818781805L;

    @Id
    @GeneratedValue
    Integer id;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    @NonNull
    public Certificate certificate;

    @ManyToOne
    @JoinColumn(name = "examination_id")
    @NonNull
    public Examination examination;

    public Integer roomSequence = 0;

    public Integer examineeSequence = 0;
}
