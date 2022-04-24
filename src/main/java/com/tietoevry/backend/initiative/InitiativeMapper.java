package com.tietoevry.backend.initiative;

import com.tietoevry.backend.initiative.districts.Districts;
import com.tietoevry.backend.initiative.features.Feature;
import com.tietoevry.backend.initiative.model.Initiative;
import com.tietoevry.backend.initiative.model.dto.InitiativeCreateDto;
import com.tietoevry.backend.initiative.model.dto.InitiativeDto;
import com.tietoevry.backend.user.UserMapper;
import com.tietoevry.backend.user.model.User;
import com.tietoevry.backend.util.DateUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tietoevry.backend.util.DateUtil.DateToString;
import static com.tietoevry.backend.util.DateUtil.StringToDate;

public class InitiativeMapper {

    private InitiativeMapper() {
    }

    public static InitiativeDto toInitiativeDto(Initiative initiative) {
        return InitiativeDto.builder()
            .id(initiative.getId())
            .user(UserMapper.toUserDto(initiative.getUser()))
            .title(initiative.getTitle())
            .description(initiative.getDescription())
            .organisation(initiative.getOrganisation())
            .location(initiative.getDistricts().getName())
            .address(initiative.getAddress())
            .latitude(initiative.getLatitude())
            .longitude(initiative.getLongitude())
            .totalNumberOfVolunteers(initiative.getTotalNumberOfVolunteers())
            .currentNumberOfVolunteers(initiative.getCurrentNumberOfVolunteers())
            .startDate(DateToString(initiative.getStartDate()))
            .endDate(DateToString(initiative.getEndDate()))
            .features(
                initiative.getFeatures()
                .stream()
                .map(Feature::getName)
                .collect(Collectors.toList())
            )
            .build();
    }

    public static Initiative toInitiative(InitiativeCreateDto initiativeDto, Map<String, Feature> featuresByName, User user, Districts districts){
        return  Initiative.builder()
            .id(initiativeDto.getId())
            .user(user)
            .title(initiativeDto.getTitle())
            .description(initiativeDto.getDescription())
            .organisation(initiativeDto.getOrganisation())
            .districts(districts)
            .address(initiativeDto.getAddress())
            .latitude(initiativeDto.getLatitude())
            .longitude(initiativeDto.getLongitude())
            .totalNumberOfVolunteers(initiativeDto.getTotalNumberOfVolunteers())
            .currentNumberOfVolunteers(initiativeDto.getCurrentNumberOfVolunteers())
            .startDate(StringToDate(initiativeDto.getStartDate()))
            .endDate(StringToDate(initiativeDto.getEndDate()))
            .features(
                initiativeDto.getFeatures()!= null
                    ?initiativeDto.getFeatures().stream()
                    .map(featuresByName::get)
                    .collect(Collectors.toList())
                    : Collections.emptyList()
            )
            .build();
    }
    public static Initiative toUpdatedInitiative(InitiativeCreateDto updatedInitiative, Initiative initiative, Districts districts, List<Feature> featureList){
        initiative.setTitle(updatedInitiative.getTitle());
        initiative.setDescription(updatedInitiative.getDescription());
        initiative.setOrganisation(updatedInitiative.getOrganisation());
        initiative.setAddress(updatedInitiative.getAddress());
        initiative.setDistricts(districts);
        initiative.setTotalNumberOfVolunteers(updatedInitiative.getTotalNumberOfVolunteers());
        initiative.setStartDate(DateUtil.StringToDate(updatedInitiative.getStartDate()));
        initiative.setEndDate(DateUtil.StringToDate(updatedInitiative.getEndDate()));
        initiative.setFeatures(featureList);
        return  initiative;
    }
}
