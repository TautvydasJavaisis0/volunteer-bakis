package com.tietoevry.backend.initiative.model;

import com.tietoevry.backend.initiative.comment.model.Comment;
import com.tietoevry.backend.initiative.districts.Districts;
import com.tietoevry.backend.initiative.features.Feature;
import com.tietoevry.backend.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class Initiative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private String title;
    private String description;
    private String organisation;
    private String address;
    private Double latitude;
    private Double longitude;
    @OneToOne
    private Districts districts;
    private Long totalNumberOfVolunteers;
    private Long currentNumberOfVolunteers;
    private Date startDate;
    private Date endDate;
    @ManyToMany
    private List<Feature> features;
    @OneToMany
    private List<User> candidate;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "initiative", orphanRemoval=true)
    private List<Comment> comments;
}
