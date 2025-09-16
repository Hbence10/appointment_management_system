package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
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

    @Column(name = "is_anonymus")
    private boolean isAnonymus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    //Kapcsolatok
    @ManyToOne(cascade = {})
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(
            mappedBy = "likedReview",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE}
    )
    private List<ReviewLikeHistory> likeHistories;

    //Constructorok
    public Review(String reviewText, double rating) {
        this.reviewText = reviewText;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Review{" +
                "Id=" + Id +
                ", reviewText='" + reviewText + '\'' +
                ", rating=" + rating +
                ", isAnonymus=" + isAnonymus +
                ", createdAt=" + createdAt +
                ", author=" + author +
                ", likeHistories=" + likeHistories +
                '}';
    }
}
