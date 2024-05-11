package rs.raf.student.dto.post;

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
public class PostUpdateDto {

    private String title;

    private String content;

}
