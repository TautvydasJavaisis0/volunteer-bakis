package com.tietoevry.backend.initiative.districts;

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
public class DistrictsDto {
    Long id;
    String name;
    Long count;
}
