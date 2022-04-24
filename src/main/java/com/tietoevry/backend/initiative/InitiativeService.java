package com.tietoevry.backend.initiative;

import com.tietoevry.backend.initiative.districts.Districts;
import com.tietoevry.backend.initiative.districts.DistrictsRepository;
import com.tietoevry.backend.initiative.districts.DistrictsService;
import com.tietoevry.backend.initiative.features.FeatureRepository;
import com.tietoevry.backend.initiative.features.Feature;
import com.tietoevry.backend.initiative.model.Initiative;
import com.tietoevry.backend.initiative.model.dto.InitiativeCreateDto;
import com.tietoevry.backend.initiative.model.dto.InitiativeDto;
import com.tietoevry.backend.session.SessionService;
import com.tietoevry.backend.user.UserRepository;
import com.tietoevry.backend.user.UserService;
import com.tietoevry.backend.user.model.User;
import com.tietoevry.backend.util.DateUtil;
import com.tietoevry.backend.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class  InitiativeService{

   private final InitiativeRepository initiativeRepository;
   private final FeatureRepository featureRepository;
   private final DistrictsService districtsService;
   private final UserService userService;

    public Optional<InitiativeDto> getInitiativeDto(long id) {
        return initiativeRepository
            .findById(id)
            .map(InitiativeMapper::toInitiativeDto);
    }

    public Initiative getInitiative(long id) {
        Optional<Initiative> initiative = initiativeRepository.findById(id);
        initiative.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Initiative not found by id " + id));
        return initiative.get();
    }

    public List<InitiativeDto> getInitiatives() {
        return initiativeRepository
            .getAllActiveNotFullInitiatives(new Date())
            .stream()
            .map(InitiativeMapper::toInitiativeDto)
            .collect(Collectors.toList());
    }

    public List<InitiativeDto> getFilteredInitiatives(String location, String start, String end, List<String> features) {

        // if no filters applied return all
        if (location == null && start == null && end == null & features == null) {
            return initiativeRepository
                .getAllActiveNotFullInitiatives(new Date())
                .stream()
                .map(InitiativeMapper::toInitiativeDto)
                .collect(Collectors.toList());
        }

        // if date is given converts from string to date format
        Date startDate = null;
        Date endDate = null;
        Districts district = null;
        long districtId = 0;
        if(start != null && end != null){
            startDate = DateUtil.StringToDate(start);
            endDate = DateUtil.StringToDate(end);
        }

        if(location != null){
            district = districtsService.getDistrict(location);
        }

        if(district != null)
            districtId = district.getId();


        return initiativeRepository
            .getFilteredList(0,districtId,startDate, endDate, new Date(), features)
            .stream()
            .map(InitiativeMapper::toInitiativeDto)
            .collect(Collectors.toList());
    }

    public List<InitiativeDto> getAppliedActiveInitiatives() {
        return initiativeRepository
            .getAppliedActiveInitiatives(userService.getLoggedInUser().getId(), new Date())
            .stream()
            .map(InitiativeMapper::toInitiativeDto)
            .collect(Collectors.toList());
    }

    public List<InitiativeDto> getAppliedNotActiveInitiatives() {
        return initiativeRepository
            .getAppliedNotActiveInitiatives(userService.getLoggedInUser().getId(), new Date())
            .stream()
            .map(InitiativeMapper::toInitiativeDto)
            .collect(Collectors.toList());
    }

    public List<InitiativeDto> getCreatedActiveInitiatives() {
        return initiativeRepository
            .getCreatedActiveInitiatives(userService.getLoggedInUser().getId(), new Date())
            .stream()
            .map(InitiativeMapper::toInitiativeDto)
            .collect(Collectors.toList());
    }

    public List<InitiativeDto> getCreatedNotActiveInitiatives() {
        return initiativeRepository
            .getCreatedNotActiveInitiatives(userService.getLoggedInUser().getId(), new Date())
            .stream()
            .map(InitiativeMapper::toInitiativeDto)
            .collect(Collectors.toList());
    }

    public InitiativeDto createInitiative(InitiativeCreateDto initiative) {
        return saveInitiative(initiative);
    }

    private InitiativeDto saveInitiative(InitiativeCreateDto initiative) {
        Map<String, Feature> featuresByName = featureRepository.findAll()
            .stream()
            .collect(Collectors.toMap(Feature::getName, Function.identity()));

        User user = userService.getUser(initiative.getUserID());
        Districts districts = districtsService.getDistrict(initiative.getLocation());

        Initiative savedInitiative = initiativeRepository
            .save(InitiativeMapper.toInitiative(initiative,featuresByName,user,districts));
        return InitiativeMapper.toInitiativeDto(savedInitiative);
    }

    public InitiativeDto updateInitiative(InitiativeCreateDto initiativeUpdated) {

        Districts districts = districtsService.getDistrict(initiativeUpdated.getLocation());
        Initiative initiative  = getInitiative(initiativeUpdated.getId());
        List<Feature> features = featureRepository.getFeaturesFromStringList(initiativeUpdated.getFeatures());

        if(!ValidationUtil.isOwner(initiative, userService.getLoggedInUser())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "you are not a owner");}

        if(initiativeUpdated.getTotalNumberOfVolunteers() < initiative.getCurrentNumberOfVolunteers()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Naujas savanorių skaičius viršija jau aplikuotų");
        }

        initiative = InitiativeMapper.toUpdatedInitiative(initiativeUpdated, initiative, districts, features);
        initiativeRepository
            .save(initiative);
        return InitiativeMapper.toInitiativeDto(initiative);
    }

    public void deleteInitiative(Long id) {
        Initiative initiative = initiativeRepository.getById(id);
        if(!ValidationUtil.isOwner(initiative, userService.getLoggedInUser())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "you are not a owner");
        }
        initiativeRepository.deleteById(id);
    }

    public boolean hasInitiativeEnded(Long id) {
        Initiative initiative = getInitiative(id);

        if(!ValidationUtil.isOwner(initiative, userService.getLoggedInUser())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "you are not a owner");
        }

        if(ValidationUtil.hasInitiativeEnded(initiative)){
            return true;
        }else {
            return false;
        }
    }
}
