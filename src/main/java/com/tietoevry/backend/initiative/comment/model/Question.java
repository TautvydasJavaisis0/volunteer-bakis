package com.tietoevry.backend.initiative.comment.model;

import com.tietoevry.backend.initiative.model.Initiative;
import com.tietoevry.backend.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("1")
public class Question extends Comment{

    @OneToOne(optional = true, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "answer_id")
    private Answer answer;

    public Question(Long id, String text, Date creationDate, User author, Initiative initiative, Answer answer) {
        super(id, text, creationDate, author, initiative);
        this.answer = answer;
    }
}
