package com.Hbence.appointmentManagementAPI.service.other;

import com.Hbence.appointmentManagementAPI.entity.Review;
import com.Hbence.appointmentManagementAPI.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ReviewHistoryWithReview {

    private Long id;
    private String likeType;
    private Date likeAt;
    private Review likedReview;
    private Users likerUser;

}
