package com.tietoevry.backend.initiative;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tietoevry.backend.initiative.model.dto.InitiativeCreateDto;
import com.tietoevry.backend.initiative.model.dto.InitiativeDto;
import com.tietoevry.backend.map_api.MapApiService;
import com.tietoevry.backend.map_api.model.MapLocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/initiatives")
public class InitiativeController {

    private final InitiativeService initiativeService;
    private final MapApiService mapApiService;

    @GetMapping(path = "/{id}")
    public InitiativeDto getInitiative(@PathVariable Long id) {
        return initiativeService.getInitiativeDto(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Initiative not found by id " + id));
    }

    @GetMapping(path = "/has-ended")
    public boolean hasEnded(@RequestParam Long id) {
        return initiativeService.hasInitiativeEnded(id);
    }

    @GetMapping(path = "/get-coordinates")
    public List<MapLocationDto> getMap(@RequestParam String location, @RequestParam String address) {
        return mapApiService.getCoordinates(location, address);
    }

    @GetMapping
    public List<InitiativeDto> getInitiatives() {
        return initiativeService.getInitiatives();
    }

    @GetMapping(path="/filter")
    public List<InitiativeDto> getFilteredInitiatives(@RequestParam(required = false) String location,
                                                      @RequestParam(required = false) String startDate,
                                                      @RequestParam(required = false) String endDate,
                                                      @RequestParam(required = false) List<String> features) {
        return initiativeService.getFilteredInitiatives(location, startDate, endDate, features);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(path="/applied-active")
    public List<InitiativeDto> getAppliedActiveInitiatives() {
        return initiativeService.getAppliedActiveInitiatives();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(path="/applied-old")
    public List<InitiativeDto> getAppliedNotActiveInitiatives() {
        return initiativeService.getAppliedNotActiveInitiatives();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(path="/created-active")
    public List<InitiativeDto> getCreatedActiveInitiatives() {
        return initiativeService.getCreatedActiveInitiatives();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(path="/created-old")
    public List<InitiativeDto> getCreatedNotActiveInitiatives() {
        return initiativeService.getCreatedNotActiveInitiatives();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public InitiativeDto createInitiative(@RequestBody InitiativeCreateDto initiative) {
        return initiativeService.createInitiative(initiative);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping
    public InitiativeDto updateInitiative(@RequestBody InitiativeCreateDto initiative) {
        return initiativeService.updateInitiative(initiative);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public void deleteInitiative(@PathVariable Long id) {
        initiativeService.deleteInitiative(id);
    }

}
