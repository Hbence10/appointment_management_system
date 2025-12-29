package com.Hbence.appointmentManagementAPI.configurations.security.JWTToken.RefresherToken;

import com.Hbence.appointmentManagementAPI.entity.Users;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "refresher_token")
public class RefreshToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "token")
    private String token;

    @Column(nullable = false, name = "expiry_date")
    private Instant expiryDate;

    //Kapcsolatok:
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;
}
