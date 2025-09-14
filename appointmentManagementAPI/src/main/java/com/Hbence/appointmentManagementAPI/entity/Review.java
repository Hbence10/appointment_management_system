package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "review")
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
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(Integer dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public boolean isAnonymus() {
        return isAnonymus;
    }

    public void setAnonymus(boolean anonymus) {
        isAnonymus = anonymus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<ReviewLikeHistory> getLikeHistories() {
        return likeHistories;
    }

    public void setLikeHistories(List<ReviewLikeHistory> likeHistories) {
        this.likeHistories = likeHistories;
    }

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
