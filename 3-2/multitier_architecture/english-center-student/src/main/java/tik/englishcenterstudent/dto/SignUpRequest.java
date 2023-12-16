package tik.englishcenterstudent.dto;

import lombok.Data;
import tik.englishcenterstudent.services.OneOf;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@OneOf({"password", "rePassword"})
public class SignUpRequest {

    @NotBlank(message = "username is required")
    public String username;

    @NotBlank(message = "name is required")
    public String name;

    @NotBlank(message = "phone is required")
    public String phone;

    @Size(min = 9, max = 13, message = "identity size 9 -13")
    public String identity;

    public String password;

    public String rePassword;
}
