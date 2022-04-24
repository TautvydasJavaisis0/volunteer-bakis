package com.tietoevry.backend.initiative.comment;

import com.tietoevry.backend.initiative.comment.model.*;
import com.tietoevry.backend.initiative.model.Initiative;
import com.tietoevry.backend.user.model.User;
import com.tietoevry.backend.util.DateUtil;

import java.util.Date;

public class CommentMapper {
    public static QuestionDto toQuestionDto(Question question) {
        return QuestionDto.builder()
            .id(question.getId())
            .text(question.getText())
            .date(DateUtil.DateToString(question.getCreationDate()))
            .fullName(question.getAuthor().getFullName())
            .answer(question.getAnswer() == null ? null : toAnswerDto(question.getAnswer()))
            .build();
    }

    public static AnswerDto toAnswerDto(Answer answer) {
        return AnswerDto.builder()
            .id(answer.getId())
            .text(answer.getText())
            .date(DateUtil.DateToString(answer.getCreationDate()))
            .fullName(answer.getAuthor().getFullName())
            .build();
    }

    public static Question toQuestion(QuestionCreationDto commentCreationDto, Answer answer, User author, Initiative initiative) {
        return new Question((long)0, commentCreationDto.getText(), new Date(), author, initiative, answer);
    }

    public static Answer toAnswer(AnswerCreationDto commentCreationDto, Question question, User author, Initiative initiative) {
        return new Answer((long)0, commentCreationDto.getText(), new Date(), author, initiative, question);
    }
}
