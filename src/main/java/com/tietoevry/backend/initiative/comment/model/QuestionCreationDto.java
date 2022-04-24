package com.tietoevry.backend.initiative.comment.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

@Builder
@Setter
@Getter
@Valid
public class QuestionCreationDto {
    String text;
    Long initiativeID;
}
