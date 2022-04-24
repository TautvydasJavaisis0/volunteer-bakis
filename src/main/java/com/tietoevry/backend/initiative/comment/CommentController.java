package com.tietoevry.backend.initiative.comment;

import com.tietoevry.backend.initiative.comment.model.AnswerCreationDto;
import com.tietoevry.backend.initiative.comment.model.AnswerDto;
import com.tietoevry.backend.initiative.comment.model.QuestionCreationDto;
import com.tietoevry.backend.initiative.comment.model.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController{
    private final CommentService commentService;

    @GetMapping(path = "/{id}")
    public List<QuestionDto> getCommentsByInitiative(@PathVariable Long id) {
        return commentService.getCommentsByInitiative(id);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping(path="/question")
    public QuestionDto postQuestionToInitiative(@RequestBody QuestionCreationDto questionCreationDto) {
        return commentService.createQuestion(questionCreationDto);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping(path="/answer")
    public AnswerDto postAnswerToInitiative(@RequestBody AnswerCreationDto answerCreationDto) {
        return commentService.createAnswer(answerCreationDto);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping(path = "/question/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        commentService.deleteQuestion(id);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping(path = "/answer/{id}")
    public void deleteAnswer(@PathVariable Long id) {
        commentService.deleteAnswer(id);
    }
}
