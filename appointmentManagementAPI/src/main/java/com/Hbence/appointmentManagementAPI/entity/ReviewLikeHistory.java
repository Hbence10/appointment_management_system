package com.Hbence.appointmentManagementAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "review_like_history")
@Getter
@Setter
@NoArgsConstructor
@ToString
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
    @JsonIgnore
    private Review likedReview;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "user_id")
    private User likerUser;

    //Constructorok
    public ReviewLikeHistory(String likeType) {
        this.likeType = likeType;
    }

    public ReviewLikeHistory(String likeType, Review likedReview, User likerUser) {
        this.likeType = likeType;
        this.likedReview = likedReview;
        this.likerUser = likerUser;
    }
}
