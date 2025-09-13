package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "table_name")
    @NotNull
    @Size(max = 100)
    private String tableName;

    @Column(name = "column_name")
    @NotNull
    @Size(max = 100)
    private String columnName;

    @Column(name = "row_id")
    @NotNull
    @Size(max = 11)
    private int rowId;

    @Column(name = "old_value")
    @NotNull
    @Size(max = 100)
    private String oldValue;

    @Column(name = "new_value")
    @NotNull
    @Size(max = 100)
    private String newValue;

    @Column(name = "edited_at")
    @NotNull
    private LocalDateTime editedAt;

    @Column(name = "is_active")
    @NotNull
    private Boolean isActive;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "user_id")
    private User editedBy;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "event_type_id")
    private EventType eventType;
}
