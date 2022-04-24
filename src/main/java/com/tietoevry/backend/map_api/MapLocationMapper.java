package com.tietoevry.backend.map_api;

import com.tietoevry.backend.map_api.model.MapLocation;
import com.tietoevry.backend.map_api.model.MapLocationDto;


public class MapLocationMapper {

    public static MapLocationDto ToMapLocationDto(MapLocation MapLocation) {
        return MapLocationDto.builder()
            .latitude(MapLocation.getLatitude())
            .longitude(MapLocation.getLongitude())
            .street(MapLocation.getStreet())
            .number(MapLocation.getNumber())
            .city(MapLocation.getRegion())
            .build();
    }
}
