package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "devices")
public class Devices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private DevicesCategory categoryId;

    @Column(name = "amount")
    @NotNull
    @Size(max = 2)
    private int amount;

    public Devices() {
    }

    public Devices(String name, DevicesCategory categoryId, int amount) {
        this.name = name;
        this.categoryId = categoryId;
        this.amount = amount;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DevicesCategory getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(DevicesCategory categoryId) {
        this.categoryId = categoryId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Devices{" + "Id=" + Id + ", name='" + name + '\'' + ", categoryId=" + categoryId + ", amount=" + amount + '}';
    }
}
