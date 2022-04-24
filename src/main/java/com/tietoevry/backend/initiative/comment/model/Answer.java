package com.tietoevry.backend.initiative.comment.model;

import com.tietoevry.backend.initiative.model.Initiative;
import com.tietoevry.backend.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@Getter
@Setter
@DiscriminatorValue("2")
public class Answer extends Comment{

    public Answer(Long id, String text, Date creationDate, User author, Initiative initiative, Question question) {
        super(id, text, creationDate, author, initiative);
    }
}
