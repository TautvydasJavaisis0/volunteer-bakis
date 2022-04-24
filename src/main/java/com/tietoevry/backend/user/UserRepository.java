package com.tietoevry.backend.user;

import com.tietoevry.backend.initiative.districts.DistrictsDto;
import com.tietoevry.backend.user.model.Role;
import com.tietoevry.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(
        "SELECT u from User u " +
        "LEFT JOIN Initiative i ON i.id = :initiative_id " +
        "JOIN i.candidate c ON c.id = u.id ")
    List<User> getCandidatesFromInitiative(@Param("initiative_id") long initiativeID);

}
