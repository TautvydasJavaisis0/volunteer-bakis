package com.tietoevry.backend.initiative.application;

import com.tietoevry.backend.initiative.InitiativeMapper;
import com.tietoevry.backend.initiative.InitiativeRepository;
import com.tietoevry.backend.initiative.InitiativeService;
import com.tietoevry.backend.initiative.model.Initiative;
import com.tietoevry.backend.initiative.model.dto.InitiativeDto;
import com.tietoevry.backend.session.SessionService;
import com.tietoevry.backend.user.UserMapper;
import com.tietoevry.backend.user.UserRepository;
import com.tietoevry.backend.user.UserService;
import com.tietoevry.backend.user.model.User;
import com.tietoevry.backend.user.model.dto.RateUserDto;
import com.tietoevry.backend.user.model.dto.UserDto;
import com.tietoevry.backend.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final InitiativeRepository initiativeRepository;
    private final ApplicationRepository applicationRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final InitiativeService initiativeService;

    public boolean isApplied(Long initiativeID){
        if(ValidationUtil.isApplied(initiativeService.getInitiative(initiativeID),userService.getLoggedInUser()))
            return true;
        else
            return false;
    }

    public Application getApplication(Long initiativeID, Long userID) {
        Optional<Application> application = applicationRepository.findByUserAndInitiative(userService.getUser(userID), initiativeService.getInitiative(initiativeID));
        application.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "application not found by user id " + userID + " and initiative id " + initiativeID));
        return application.get();
    }

    public InitiativeDto addApplicant(Long initiativeID) {

        Initiative initiative = initiativeService.getInitiative(initiativeID);
        User user = userService.getLoggedInUser();

        if(ValidationUtil.isApplied(initiative,userService.getLoggedInUser())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "already applied");
        }

        if(!ValidationUtil.isInitiativeFull(initiative)){
            initiative.getCandidate().add(user);
            increaseCurrentVolunteersCounter(initiative);
            increaseUserTotalAppliedHistory(user);
            initiativeRepository.save(initiative);
            userRepository.save(user);
        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "application is full");


        return InitiativeMapper.toInitiativeDto(initiative);
    }

    public void deleteApplicant(Long initiativeID) {

        Initiative initiative = initiativeService.getInitiative(initiativeID);
        User user = userService.getLoggedInUser();

        if(!ValidationUtil.isApplied(initiative,user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you are not applied");
        }

        initiative.getCandidate().remove(user);
        decreaseCurrenVolunteersCounter(initiative);
        initiativeRepository.save(initiative);
    }

    public void deleteApplicantById(Long initiativeID, Long userID) {

        Initiative initiative = initiativeService.getInitiative(initiativeID);
        User applicant = userService.getUser(userID);

        if(ValidationUtil.isOwner(initiative, userService.getLoggedInUser())){
            initiative.getCandidate().remove(applicant);
            decreaseCurrenVolunteersCounter(initiative);
            initiativeRepository.save(initiative);
        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "you are not a owner");
    }

    public UserDto rateApplicant(RateUserDto rateUserDto) {

        User user = userService.getUser(rateUserDto.getUserId());
        Initiative initiative = initiativeService.getInitiative(rateUserDto.getInitiativeId());
        Application application = getApplication(rateUserDto.getInitiativeId(), rateUserDto.getUserId());

        if(isRated(rateUserDto.getInitiativeId(), rateUserDto.getUserId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "this user was already rated");
        }

        if(!ValidationUtil.isOwner(initiative, userService.getLoggedInUser())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you are not owner of initiative");
        }

        if(rateUserDto.isRated() == true){
            user.setRatingCount(user.getRatingCount()+1);
            userRepository.save(user);
        }

        if(rateUserDto.isAttended() == false) {
            deleteApplicantById(rateUserDto.getInitiativeId(), rateUserDto.getUserId());
            return UserMapper.toUserDto(user);
        }else{
            application.setStatus(true);
        }

        application.setApprovedByOwner(true);
        applicationRepository.save(application);

        return UserMapper.toUserDto(user);
    }

    public boolean isRated(long initiativeID, long userID) {
        Application application = getApplication(initiativeID, userID);
        if(application.isApprovedByOwner() == true) {
            return true;
        }else{
            return false;
        }
    }

    public void increaseCurrentVolunteersCounter(Initiative initiative){
        if(initiative.getCurrentNumberOfVolunteers() < initiative.getTotalNumberOfVolunteers())
            initiative.setCurrentNumberOfVolunteers(initiative.getCurrentNumberOfVolunteers()+1);
    }

    public void increaseUserTotalAppliedHistory(User user){
            user.setTotalApplications(user.getTotalApplications() + 1);
    }

    public void decreaseCurrenVolunteersCounter(Initiative initiative){
        if(initiative.getCurrentNumberOfVolunteers() > 0) {
            initiative.setCurrentNumberOfVolunteers(initiative.getCurrentNumberOfVolunteers() - 1);
        }
    }

    public void decreaseUserTotalAppliedHistory(User user){
        if(user.getTotalApplications() > 0) {
            user.setTotalApplications(user.getTotalApplications() - 1);
        }
    }
}
