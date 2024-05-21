package rs.raf.student.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.raf.student.adapter.time.LocalDateSerializer;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Long id;

    private Long postId;

    private Long authorId;

    private String content;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;


}
