package com.Hbence.appointmentManagementAPI.dao;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NewsDAO {
    public EntityManager entityManager;

    @Autowired
    public NewsDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
