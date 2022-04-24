package com.tietoevry.backend.initiative.comment;

import com.tietoevry.backend.initiative.InitiativeRepository;
import com.tietoevry.backend.initiative.InitiativeService;
import com.tietoevry.backend.initiative.comment.model.*;
import com.tietoevry.backend.initiative.model.Initiative;
import com.tietoevry.backend.session.SessionService;
import com.tietoevry.backend.user.UserRepository;
import com.tietoevry.backend.user.UserService;
import com.tietoevry.backend.user.model.User;
import com.tietoevry.backend.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final InitiativeService initiativeService;

    public List<QuestionDto> getCommentsByInitiative(Long initiativeID){
        Initiative initiative = initiativeService.getInitiative(initiativeID);
        List<Question> questions = questionRepository.getAllByInitiativeOrderByCreationDateDesc(initiative);
        List<QuestionDto> commentDtoList = new ArrayList<>();

        for(Question question: questions){
            commentDtoList.add(CommentMapper.toQuestionDto(question));
        }

        return commentDtoList;
    }
    public AnswerDto createAnswer(AnswerCreationDto commentCreationDto){

        Initiative initiative = initiativeService.getInitiative(commentCreationDto.getInitiativeID());
        User user = userService.getLoggedInUser();

        Optional<Question> question = questionRepository.findById(commentCreationDto.getLinkedCommentID());
        question.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "question comment not found by id " + commentCreationDto.getLinkedCommentID()));

        if(ValidationUtil.doesAnswerExists(question.get())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "answer already exists");}

        if(!ValidationUtil.isOwner(initiative, user)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "you are not a owner");}

        if(!ValidationUtil.isSameInitiative(initiative, question.get().getInitiative())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "questions are not in same initiative");}

        Answer savedAnswer =  answerRepository
            .save(CommentMapper.toAnswer(commentCreationDto, question.get(), user, initiative));
        question.get().setAnswer(savedAnswer);
        questionRepository.save(question.get());
        return CommentMapper.toAnswerDto(savedAnswer);
    }

    public QuestionDto createQuestion(QuestionCreationDto commentCreationDto){

        Initiative initiative = initiativeService.getInitiative(commentCreationDto.getInitiativeID());
        User user = userService.getLoggedInUser();

        Question savedQuestion =  questionRepository
            .save(CommentMapper.toQuestion(commentCreationDto, null, user, initiative));
        return CommentMapper.toQuestionDto(savedQuestion);
    }

    public void deleteQuestion(Long id) {
        Comment comment = commentRepository.getById(id);
        User owner = userService.getLoggedInUser();

        if(ValidationUtil.isOwner(comment.getInitiative(), owner)){
            commentRepository.deleteById(id);
        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "you are not a owner");
    }

    public void deleteAnswer(Long id) {
        Optional<Answer> answer = answerRepository.findById(id);
        answer.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "answer not found by id " + answer.get().getId()));

        User owner = userService.getLoggedInUser();

        Optional<Question> question = questionRepository.getByAnswer(answer.get());
        question.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "question not found by id " + question.get().getAnswer().getId()));


        if(ValidationUtil.isOwner(answer.get().getInitiative(), owner)){
            question.get().setAnswer(null);
            questionRepository.save(question.get());
            answerRepository.deleteById(id);
        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "you are not a owner");
    }
}
