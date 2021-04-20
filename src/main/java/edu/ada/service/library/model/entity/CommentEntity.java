package edu.ada.service.library.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;
@NoArgsConstructor
@Entity
@Data



@Builder
@AllArgsConstructor

public class CommentEntity {
    @Id
    private String id;
//    private String book_ext_id;
    private String comment_author_name;
    private String comment_content;

    @OneToOne
    private BookEntity book;

    @OneToMany
    private List<CommentEntity> replies;

//    public static Object builder() {
//    }

}
