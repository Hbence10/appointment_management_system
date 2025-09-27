package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "phone_country_codes")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PhoneCountryCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "country_code")
    @NotNull
    @Size(max = 3)
    private int countryCode;

    @Column(name = "country_name")
    @NotNull
    @Size(max = 150)
    private String country;

    public PhoneCountryCode(int countryCode, String country) {
        this.countryCode = countryCode;
        this.country = country;
    }
}
