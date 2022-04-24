package com.tietoevry.backend.initiative.application;

import com.tietoevry.backend.initiative.model.Initiative;
import com.tietoevry.backend.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.Valid;

@Entity(name = "initiative_candidate")
@Table(name = "initiative_candidate")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "initiative_id")
    private Initiative initiative;

    @OneToOne
    @JoinColumn(name = "candidate_id")
    private User user;

    @Column(name = "status")
    private boolean status;

    @Column(name = "approved_by_owner")
    private boolean approvedByOwner;



}
