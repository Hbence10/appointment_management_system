package com.Hbence.appointmentManagementAPI.entity;
    
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getUserByUsername", procedureName = "getUserByUsername", parameters = {
                @StoredProcedureParameter(name = "usernameIN", type = String.class, mode = ParameterMode.IN)},
                resultClasses = {Users.class}),

        @NamedStoredProcedureQuery(name = "getUserByEmail", procedureName = "getUserByEmail", parameters = {
                @StoredProcedureParameter(name = "emailIN", type = String.class, mode = ParameterMode.IN)},
                resultClasses = {Users.class}),

        @NamedStoredProcedureQuery(name = "getAllEmail", procedureName = "getAllEmail", resultClasses = String.class),
        @NamedStoredProcedureQuery(name = "getAllAdmin", procedureName = "getAllAdmin", resultClasses = Users.class),

        @NamedStoredProcedureQuery(name = "getUserIdByToken", procedureName = "getUserIdByToken", parameters = {
                @StoredProcedureParameter(name = "tokenIN", type = String.class, mode = ParameterMode.IN)
        }, resultClasses = Long.class)
})
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    @NotNull
    @Size(max = 100)
    private String username;

    @Column(name = "email")
    @NotNull
    @Size(max = 100)
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "pfp_path")
    @NotNull
    private String pfpPath = "assets/placeholder.png";

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date createdAt;

    @Column(name = "last_login")
    @Null
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date lastLogin;

    @Column(name = "is_deleted")
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Null
    @JsonIgnore
    private Date deletedAt;

    @Column(name = "v_code")
    @Temporal(TemporalType.TIMESTAMP)
    @Null
    @JsonIgnore
    private String vCode;

    //Kapcsolatok:
    @ManyToOne(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role = new Role(1, "user");

    @OneToMany(mappedBy = "writer", cascade = {})
    @JsonIgnore
    private List<News> news;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private List<Review> reviews;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {})
    @JsonIgnore
    private List<Reservations> reservations;

    @OneToMany(mappedBy = "canceledBy", cascade = {})
    @JsonIgnore
    private List<Reservations> canceledReservation;

    @OneToMany(mappedBy = "editedBy", fetch = FetchType.LAZY, cascade = {})
    @JsonIgnore
    private List<History> historyList;

    @OneToMany(mappedBy = "likerUser", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private List<ReviewLikeHistory> reviewLikeHistories;

    @OneToOne(mappedBy = "adminUser", cascade = {})
    @Null
    @JsonIgnoreProperties({"adminUser", "isDeleted", "deletedAt"})
    private AdminDetails adminDetails;

    @OneToMany(mappedBy = "creatorUser", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private List<CloseReason> closeReasons;
}
