package com.tietoevry.backend.user.model;

import com.tietoevry.backend.initiative.comment.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String organisation;
    private String fullName;
    @Column(unique=true)
    private String email;
    @Column
    private String password;
    @Column
    private String phone;
    private Long ratingCount;
    private Long totalApplications;
    @ManyToMany
    private List<Role> role;
    @OneToMany
    private List<Comment> comments;
}
