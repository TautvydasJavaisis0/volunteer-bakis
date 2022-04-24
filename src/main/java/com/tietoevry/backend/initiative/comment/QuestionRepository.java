package com.tietoevry.backend.initiative.comment;

import com.tietoevry.backend.initiative.comment.model.Answer;
import com.tietoevry.backend.initiative.comment.model.Question;
import com.tietoevry.backend.initiative.model.Initiative;
import liquibase.pro.packaged.A;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{

    List<Question> getAllByInitiativeOrderByCreationDateDesc(Initiative initiative);
    Optional<Question> getByAnswer(Answer answer);

}
