package com.tietoevry.backend.util;

import com.tietoevry.backend.initiative.features.Feature;
import com.tietoevry.backend.initiative.model.Initiative;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UserStatsUtil {

    public static int getTotalDays(List<Initiative> initiatives) {
        int days = 0;

        for(Initiative initiative: initiatives){
            long diff = initiative.getEndDate().getTime() - initiative.getStartDate().getTime();
            days += TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        }

        return days;
    }

    public static HashMap<String, Integer> countFeatures(List<Initiative> initiatives, List<Feature> features) {
        HashMap<String, Integer> featuresMap = new HashMap<>();
        int count;
        for(Feature feature: features){
            count = countOneFeatureFromInitiative(initiatives, feature);
            featuresMap.put(feature.getName(), count);
        }

        return featuresMap;
    }

    public static int countOneFeatureFromInitiative(List<Initiative> initiatives, Feature feature) {
        int count = 0;
        for(Initiative initiative: initiatives){
            if(initiative.getFeatures().contains(feature)) {
                count++;
            }
        }
        return count;
    }
}
