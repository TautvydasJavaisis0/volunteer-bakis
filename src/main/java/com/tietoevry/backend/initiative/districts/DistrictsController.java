package com.tietoevry.backend.initiative.districts;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/districts")
public class DistrictsController {
    private final DistrictsService districtsService;
    @GetMapping
    public List<DistrictsDto> getDistrictsWithInitiatives() {
        return districtsService.getDistrictsWithInitiatives();
    }
}



