package com.tietoevry.backend.initiative.model.dto;

import com.tietoevry.backend.user.model.dto.UserDto;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Value
public class InitiativeCreateDto {
    @NotNull(message = "user id is mandatory")
    Long userID;
    Long id;
    @NotNull(message = "title is mandatory")
    String title;
    @NotNull(message = "description is mandatory")
    String description;
    String organisation;
    @NotNull(message = "district is mandatory")
    String location;
    @NotNull(message = "address is mandatory")
    String address;
    @NotNull(message = "latitude is mandatory")
    Double latitude;
    @NotNull(message = "longitude is mandatory")
    Double longitude;
    @NotNull(message = "totalNumberOfVolunteers is mandatory")
    Long totalNumberOfVolunteers;
    @NotNull(message = "currentNumberOfVolunteers is mandatory")
    Long currentNumberOfVolunteers;
    @NotNull(message = "startDate is mandatory")
    String startDate;
    @NotNull(message = "endDate is mandatory")
    String endDate;
    @NotNull(message = "features is mandatory")
    List<String> features;
}
