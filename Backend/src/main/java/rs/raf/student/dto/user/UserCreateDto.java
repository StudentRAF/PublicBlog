package rs.raf.student.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.raf.student.utils.NotInUse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NotInUse(message = "The class is not currently in use, maybe in the future.")
public class UserCreateDto {

    @JsonProperty("first_name")
    @NotBlank(message = "First name is required and cannot be empty!")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "Last name is required and cannot be empty!")
    private String lastName;

    @NotBlank(message = "Username is required and cannot be empty!")
    private String username;

    @NotBlank(message = "Password is required and cannot be empty!")
    private String password;

}
