package rs.raf.student.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserUpdateDto {

    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String username;

    private String password;

}
