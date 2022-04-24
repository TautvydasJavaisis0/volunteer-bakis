package com.tietoevry.backend.map_api.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class MapLocationDto {
    Double latitude;
    Double longitude;
    String street;
    String number;
    String city;
}
