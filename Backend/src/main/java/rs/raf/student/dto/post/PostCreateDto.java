package rs.raf.student.dto.post;

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
public class PostCreateDto {

    @JsonProperty("author_id")
    @NotNull(message = "Post must have an author!")
    private Long authorId;

    @NotBlank(message = "Post must have the title and cannot be empty!")
    private String title;

    @NotBlank(message = "Post must have the content and cannot be empty!")
    private String content;

}
