package com.tietoevry.backend.initiative.application;


import com.tietoevry.backend.initiative.model.dto.InitiativeDto;
import com.tietoevry.backend.user.model.dto.RateUserDto;
import com.tietoevry.backend.user.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/initiatives/applicants")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(path = "/is-applied")
    public boolean isApplied(@RequestParam Long initiativeID) {
        return applicationService.isApplied(initiativeID);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(path = "/is-rated")
    public boolean isRated(@RequestParam Long initiativeID, @RequestParam Long userID) {
        return applicationService.isRated(initiativeID, userID);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping()
    public InitiativeDto postApplicant(@RequestParam Long initiativeID) {
        return applicationService.addApplicant(initiativeID);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping()
    public void deleteApplicant(@RequestParam Long initiativeID) {
        applicationService.deleteApplicant(initiativeID);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping(path = "/delete-by")
    public void deleteApplicantById(@RequestParam Long initiativeID, @RequestParam Long userID) {
        applicationService.deleteApplicantById(initiativeID, userID);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping(path = "/rate")
    public UserDto rateApplicant(@RequestBody RateUserDto rating) {
        return applicationService.rateApplicant(rating);
    }

}
