package com.mazindere.university_reviews_app.entity;

import com.mazindere.university_reviews_app.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Column(name = "university_name", nullable = true)
    private String universityName;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;



}
