package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "review_text")
    @NotNull
    private String reviewText;

    @Column(name = "rating")
    @NotNull
    private double rating;

    @Column(name = "is_anonymous")
    private boolean isAnonymous;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    //Kapcsolatok
    @ManyToOne(cascade = {})
    @JoinColumn(name = "author_id")
    private Users author;

    @OneToMany(
            mappedBy = "likedReview",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    private List<ReviewLikeHistory> likeHistories;

    //Constructorok
    public Review(String reviewText, double rating, boolean isAnonymous) {
        this.reviewText = reviewText;
        this.rating = rating;
        this.isAnonymous = isAnonymous;
    }
}
