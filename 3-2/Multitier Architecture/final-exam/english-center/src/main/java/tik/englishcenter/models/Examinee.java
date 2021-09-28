package tik.englishcenter.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.*;
import tik.englishcenter.constants.Constants;

@Entity
@Table(name = "examinee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Examinee {
    @Id
    @GeneratedValue
    public Integer id;

    public String examineeId;

    public Float listenning = Float.valueOf("0");

    public Float writing = Float.valueOf("0");

    public Float speaking = Float.valueOf("0");

    public Float reading = Float.valueOf("0");

    @NonNull
    @NotBlank(message = "name is required")
    @Pattern(regexp = Constants.ONLY_WORD, message = "name must contraint words")
    public String name;

    @NonNull
    public String phone;

    @NonNull
    @NotBlank(message = "gender is required")
    public String gender;

    @NonNull
    @NotBlank(message = "identity is required")
    public String identity;

    @ManyToOne
    @JoinColumn(name = "examination_id")
    @NonNull
    @NotNull(message = "examination is requried")
    public Examination examination;

    @NonNull
    @NotBlank(message = "certificate is requried")
    public String certificateId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    public Room room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    public Manager manager;
}
