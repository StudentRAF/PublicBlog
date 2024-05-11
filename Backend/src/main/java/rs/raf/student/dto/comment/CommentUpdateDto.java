package rs.raf.student.dto.comment;

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
public class CommentUpdateDto {

    private String content;

}
