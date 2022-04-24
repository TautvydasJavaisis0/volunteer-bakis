package com.tietoevry.backend.initiative.comment.model;

import com.tietoevry.backend.initiative.model.Initiative;
import com.tietoevry.backend.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name="comment")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="comment_type",
    discriminatorType = DiscriminatorType.INTEGER)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String text;
    protected Date creationDate;

    @ManyToOne
    @JoinColumn(name = "author_id")
    protected User author;

    @ManyToOne
    @JoinColumn(name = "initiative_id")
    protected Initiative initiative;
}
