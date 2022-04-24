package com.tietoevry.backend.initiative.comment;

import com.tietoevry.backend.initiative.comment.model.Answer;
import com.tietoevry.backend.initiative.model.Initiative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long>{
    @Override
    Optional<Answer> findById(Long id);
}
