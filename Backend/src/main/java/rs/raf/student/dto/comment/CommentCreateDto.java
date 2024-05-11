package rs.raf.student.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDto {

    @JsonProperty("author_id")
    @NotNull(message = "Comment has to have author!")
    private Long authorId;

    @JsonProperty("post_id")
    @NotNull(message = "Comment has to have post!")
    private Long postId;

    @NotBlank(message = "Content is required field and cannot be empty!")
    private String content;

}
