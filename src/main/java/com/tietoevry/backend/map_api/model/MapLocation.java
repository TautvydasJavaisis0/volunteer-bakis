package com.tietoevry.backend.map_api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MapLocation {
    double latitude;
    double longitude;
    String label;
    String name;
    String type;
    String number;
    String street;
    String postal_code;
    String confidence;
    String region;
    String region_code;
    String administrative_area;
    String neighbourhood;
    String country;
    String country_code;
    String map_url;
}
