package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "review")
@Getter
@Setter
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

    @Column(name = "like_count")
    @NotNull
    @Size(max = 4)
    private Integer likeCount;

    @Column(name = "dislike_count")
    @NotNull
    @Size(max = 4)
    private Integer dislikeCount;

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
    public Review() {
    }

    public Review(String reviewText, double rating, Integer likeCount, Integer dislikeCount) {
        this.reviewText = reviewText;
        this.rating = rating;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }

    //Getterek & Setterek


    @Override
    public String toString() {
        return "Review{" +
                "Id=" + Id +
                ", reviewText='" + reviewText + '\'' +
                ", rating=" + rating +
                ", likeCount=" + likeCount +
                ", dislikeCount=" + dislikeCount +
                ", isAnonymus=" + isAnonymus +
                ", createdAt=" + createdAt +
                ", author=" + author +
                ", likeHistories=" + likeHistories +
                '}';
    }

    //
    public void addHistory(ReviewLikeHistory newHistory){
        this.likeHistories.add(newHistory);
    }
}
