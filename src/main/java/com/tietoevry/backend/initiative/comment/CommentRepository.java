package com.tietoevry.backend.initiative.comment;

import com.tietoevry.backend.initiative.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
