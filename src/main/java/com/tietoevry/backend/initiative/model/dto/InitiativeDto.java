package com.tietoevry.backend.initiative.model.dto;

import com.tietoevry.backend.user.model.User;
import com.tietoevry.backend.user.model.dto.UserDto;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class InitiativeDto {
    UserDto user;
    Long id;
    String title;
    String description;
    String organisation;
    String location;
    String address;
    Double latitude;
    Double longitude;
    Long totalNumberOfVolunteers;
    Long currentNumberOfVolunteers;
    String startDate;
    String endDate;
    List<String> features;
}
