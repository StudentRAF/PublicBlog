package rs.raf.student.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.raf.student.adapter.time.LocalDateSerializer;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private Long id;

    private Long authorId;

    private String title;

    private String content;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @Setter(AccessLevel.NONE)
    private List<Comment> comments = new CopyOnWriteArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
    }

}
