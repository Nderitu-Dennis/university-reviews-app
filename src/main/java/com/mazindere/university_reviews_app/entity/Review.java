package com.mazindere.university_reviews_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Review {

    @Id
    @Column(name="review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @Column(name = "reviewed_university")
    private String reviewedUniversity;//  name of the university's profile the review came from

    private String title;
    private Integer rating;

    @Column(name = "review_text", columnDefinition = "TEXT")
    private String reviewText;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.DATE)
 private java.util.Date createdAt = new java.util.Date();

    @ManyToOne(fetch = FetchType.LAZY) //many reviews can be linked to one user
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @Column(name="reviewer_name", nullable = false)
    private String reviewerName;


}
