package com.tietoevry.backend.initiative.comment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

@Builder
@Setter
@Getter
@Valid
@AllArgsConstructor
public class AnswerDto {
    Long id;
    String text;
    String date;
    String fullName;
}
