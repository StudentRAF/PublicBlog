package rs.raf.student.dto.post;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.raf.student.adapter.time.LocalDateSerializer;
import rs.raf.student.dto.comment.CommentGetDto;
import rs.raf.student.dto.user.UserGetDto;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostGetDto {

    private Long id;

    private UserGetDto author;

    private String title;

    private String content;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    private List<CommentGetDto> comments;

}
