package com.Hbence.appointmentManagementAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "phone_country_code")
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
    private String countryName;

    //Kapcsolatok:
    @JsonIgnore
    @OneToMany(
            mappedBy = "phoneCountryCode",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    private List<Reservations> reservationsList;

    public PhoneCountryCode(int countryCode, String country) {
        this.countryCode = countryCode;
        this.countryName = country;
    }

    public PhoneCountryCode(Long id, int countryCode, String countryName) {
        this.id = id;
        this.countryCode = countryCode;
        this.countryName = countryName;
    }
}
