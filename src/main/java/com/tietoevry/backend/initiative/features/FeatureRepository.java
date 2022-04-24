package com.tietoevry.backend.initiative.features;

import com.tietoevry.backend.initiative.model.Initiative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    Feature findByNameContains(String name);

    @Query(
        "SELECT DISTINCT f " +
            "FROM Feature f " +
            "WHERE  f.name in (:list) ")
    List<Feature> getFeaturesFromStringList(@Param("list") List<String> featuresList);
}
