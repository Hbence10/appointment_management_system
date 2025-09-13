package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "review_like_history")
public class ReviewLikeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "like_type")
    @Size(max = 10)
    @NotNull
    private String likeType;

    @Column(name = "like_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Null
    private Date likeAt;

    //Kapcsolatok
    @ManyToOne(cascade = {})
    @JoinColumn(name = "review_id")
    private Review likedReview;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "user_id")
    private User likerUser;

    //Constructorok
    public ReviewLikeHistory() {
    }

    public ReviewLikeHistory(String likeType, Date likeAt) {
        this.likeType = likeType;
        this.likeAt = likeAt;
    }

    //Getterek & Setterek:
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLikeType() {
        return likeType;
    }

    public void setLikeType(String likeType) {
        this.likeType = likeType;
    }

    public Date getLikeAt() {
        return likeAt;
    }

    public void setLikeAt(Date likeAt) {
        this.likeAt = likeAt;
    }

    public User getLikerUser() {
        return likerUser;
    }
}
