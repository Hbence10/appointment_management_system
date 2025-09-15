package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "login", procedureName = "login", parameters = {
                @StoredProcedureParameter(name = "usernameIN", type = String.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "passwordIN", type = String.class, mode = ParameterMode.IN)
        }, resultClasses = {User.class}),

        @NamedStoredProcedureQuery(name = "register", procedureName = "register", parameters = {
                @StoredProcedureParameter(name = "usernameIN", type = String.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "emailIN", type = String.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "passwordIN", type = String.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "result", type = String.class, mode = ParameterMode.OUT)

        }, resultClasses = {String.class})
})

@Table(name = "user")
public class User {

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
    @Size(max = 64)
    private String password;

    @Column(name = "pfp_path")
    @NotNull
    private String pfpPath = "asd";

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "last_login")
    @Null
    private Date lastLogin;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Null
    private Date deletedAt;

    //Kapcsolatok:
    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "role_id")
    private Role role = new Role(Long.valueOf(1), "user");

    @OneToMany(
            mappedBy = "writer",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    private List<News> news;

    @OneToMany(
            mappedBy = "author",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH}
    )
    private List<Review> reviews;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    private List<Reservations> reservations;

    @OneToOne(mappedBy = "canceledBy", cascade = {})
    private Reservations canceledReservation;

    @OneToMany(
            mappedBy = "editedBy",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    private List<History> historyList;

    @OneToMany(
            mappedBy = "likerUser",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    private List<ReviewLikeHistory> reviewLikeHistories;

    //Constructorok
    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    //Getter & Setter:
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPfpPath() {
        return pfpPath;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public Role getRole() {
        return role;
    }

    public Long getId() {
        return id;
    }

    //ToString

    @Override
    public String toString() {
        return "User{" +
                "role=" + role +
                ", deletedAt=" + deletedAt +
                ", isDeleted=" + isDeleted +
                ", lastLogin=" + lastLogin +
                ", createdAt=" + createdAt +
                ", pfpPath='" + pfpPath + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", id=" + id +
                '}';
    }
}
