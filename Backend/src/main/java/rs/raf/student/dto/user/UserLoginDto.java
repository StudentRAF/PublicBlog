package rs.raf.student.dto.user;

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
public class UserLoginDto {

    @NotBlank(message = "Username is required and cannot be empty!")
    private String username;

    @NotBlank(message = "Password is required and cannot be empty!")
    private String password;

}
