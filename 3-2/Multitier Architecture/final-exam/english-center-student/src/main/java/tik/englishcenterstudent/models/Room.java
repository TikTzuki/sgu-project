package tik.englishcenterstudent.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "room")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue
    public Integer id;

    public String name;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    public Certificate certificate = new Certificate();

    @ManyToOne
    @JoinColumn(name = "examination_id")
    public Examination examination = new Examination();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @OrderBy("examineeId")
    public List<Examinee> examinees = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreatedDate
    public Date createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @LastModifiedDate
    public Date updatedDate;

    public Room(@NonNull Certificate certificate, @NonNull Examination examination) {
        this.certificate = certificate;
        this.examination = examination;
    }
}
