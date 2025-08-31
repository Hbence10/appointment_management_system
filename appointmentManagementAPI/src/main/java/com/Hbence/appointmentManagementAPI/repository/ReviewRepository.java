package com.Hbence.appointmentManagementAPI.repository;

import com.Hbence.appointmentManagementAPI.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Procedure(name = "addReview", procedureName = "addReview")
    Long addedReview(@Param("userIdIN") Object userId, @Param("reviewTextIN") Object reviewText, @Param("ratingIN") Object rating, @Param("isAnonymusIN") Object isAnonymus);
}
