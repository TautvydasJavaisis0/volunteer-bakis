package com.tietoevry.backend.initiative;

import com.tietoevry.backend.initiative.model.Initiative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InitiativeRepository extends JpaRepository<Initiative, Long>{
    @Query(
        "SELECT DISTINCT i " +
        "FROM Initiative i " +
        "JOIN i.features f " +
        "WHERE (COALESCE(:list,'') IS NULL OR (f.name in :list)) AND "+
        "(:start IS NULL OR i.endDate > :start) AND " +
        "(:end IS NULL OR i.startDate < :end) AND " +
        "(:current < i.endDate) AND " +
        "(i.totalNumberOfVolunteers > i.currentNumberOfVolunteers) AND " +
        "(:district_id = :zero_id OR i.districts.id = :district_id) ")
    List<Initiative> getFilteredList(@Param("zero_id") long idOfZero,
                                  @Param("district_id") long districtID,
                                  @Param("start")Date start,
                                  @Param("end")Date end,
                                  @Param("current")Date current,
                                  @Param("list") List<String> featuresList);

    @Query(
        "SELECT DISTINCT i " +
        "FROM Initiative i " +
        "JOIN i.candidate c " +
        "WHERE c.id = :user_id AND " +
        "(i.endDate > :date) ")
    List<Initiative> getAppliedActiveInitiatives(@Param("user_id") long userID, @Param("date")Date date);

    @Query(
        "SELECT DISTINCT i " +
            "FROM Initiative i " +
            "JOIN i.candidate c " +
            "JOIN initiative_candidate i_c ON i_c.user.id = :user_id AND i_c.initiative.id = i.id " +
            "WHERE ((c.id = :user_id) AND (i.endDate < :date)) OR i_c.status = :true ")
    List<Initiative> getAttendedInitiatives(@Param("user_id") long userID, @Param("date")Date date, @Param("true")Boolean bool);

    @Query(
    "SELECT DISTINCT i " +
    "FROM Initiative i " +
    "JOIN i.candidate c " +
    "WHERE c.id = :user_id AND " +
    "(i.endDate < :date) ")
    List<Initiative> getAppliedNotActiveInitiatives(@Param("user_id") long userID, @Param("date")Date date);

    @Query(
        "SELECT DISTINCT i " +
        "FROM Initiative i " +
        "JOIN i.user u " +
        "WHERE u.id = :user_id AND " +
        "(i.endDate > :date) ")
    List<Initiative> getCreatedActiveInitiatives(@Param("user_id") long userID, @Param("date")Date date);

    @Query(
        "SELECT DISTINCT i " +
        "FROM Initiative i " +
        "JOIN i.user u " +
        "WHERE u.id = :user_id AND " +
        "(i.endDate < :date) ")
    List<Initiative> getCreatedNotActiveInitiatives(@Param("user_id") long userID, @Param("date")Date date);

    @Query(
        "SELECT DISTINCT i " +
        "FROM Initiative i " +
        "WHERE (i.endDate > :date) AND " +
        "(i.totalNumberOfVolunteers > i.currentNumberOfVolunteers) " )
    List<Initiative> getAllActiveNotFullInitiatives(@Param("date")Date date);
}
