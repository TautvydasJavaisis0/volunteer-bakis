package com.tietoevry.backend.initiative.districts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictsRepository extends JpaRepository<Districts, Long>{
    @Query(
        "SELECT new com.tietoevry.backend.initiative.districts.DistrictsDto(d.id, d.name, count(d.id)) from Districts d " +
            "JOIN Initiative i ON d.id = i.districts.id " +
            "GROUP BY d.id, d.name " +
            "order by count(d.id) desc, d.name")
    List<DistrictsDto> getDistrictsWithInitiatives();

    Optional<Districts> findByName(String name);
}
