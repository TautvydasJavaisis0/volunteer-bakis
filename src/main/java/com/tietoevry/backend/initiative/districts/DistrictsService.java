package com.tietoevry.backend.initiative.districts;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class  DistrictsService{

    private final DistrictsRepository districtsRepository;

    public List<DistrictsDto> getDistrictsWithInitiatives(){
        return districtsRepository.getDistrictsWithInitiatives();
    }

    public Districts getDistrict(String name){
        Optional<Districts> districts = districtsRepository.findByName(name);
        districts.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "no distric found by name " + name));
        return districts.get();
    }
}
