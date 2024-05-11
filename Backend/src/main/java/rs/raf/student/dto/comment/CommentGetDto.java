package rs.raf.student.dto.comment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.raf.student.adapter.time.LocalDateSerializer;
import rs.raf.student.dto.user.UserGetDto;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetDto {

    private UserGetDto author;

    private String content;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

}
