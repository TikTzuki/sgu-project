package tik.englishcenter.models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "examination")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Examination {
    @Id
    @GeneratedValue
    public Integer id;

    @NonNull
    public String name;

    @OneToMany(mappedBy = "examination", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<CertificateSequence> certificateSequences;

    @OneToMany(mappedBy = "examination")
    public List<Room> rooms;

    @OneToMany(mappedBy = "examination")
    public List<Examinee> examinees;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NonNull
    public Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NonNull
    public Date endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    public Date createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @UpdateTimestamp
    public Date updatedDate;

}
