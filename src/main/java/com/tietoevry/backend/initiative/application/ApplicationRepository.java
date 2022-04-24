package com.tietoevry.backend.initiative.application;

import com.tietoevry.backend.initiative.model.Initiative;
import com.tietoevry.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>{
    /*
    @Query(
        "SELECT DISTINCT a " +
        "FROM initiative_candidate a " +
        "WHERE a.initiative = :initiative_id AND a.c = :user_id ")
    Optional<Application> findByInitiativeAndUser(@Param("user_id") long userID, @Param("initiative_id")long initiativeID);

     */

    Optional<Application> findByUserAndInitiative(User user, Initiative initiative);


}
