package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "review_text")
    @NotNull
    private String reviewText;

    @Column(name = "rating")
    @NotNull
    private double rating;

    @Column(name = "like_count")
    @NotNull
    @Size(max = 4)
    private int likeCount;

    @Column(name = "dislike_count")
    @NotNull
    @Size(max = 4)
    private int dislikeCount;

    @Column(name = "is_anonymus")
    private boolean isAnonymus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "author_id")
    @NotNull
    private User author;

    public Review() {
    }

    public Review(String reviewText, double rating, int likeCount, int dislikeCount) {
        this.reviewText = reviewText;
        this.rating = rating;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
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

    public String getAuthor() {
        return author.getUsername();
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
