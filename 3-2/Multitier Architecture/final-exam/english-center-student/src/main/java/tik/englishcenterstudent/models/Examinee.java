package tik.englishcenterstudent.models;

import lombok.*;
import tik.englishcenterstudent.constants.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
    @Size(min = 9, max = 13, message = "Identity length 9-13")
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
