package com.Hbence.appointmentManagementAPI.repository;

import com.Hbence.appointmentManagementAPI.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {

    @Procedure(name = "getGalleryImages", procedureName = "getGalleryImages")
    List<Gallery> getGalleryImages();
}
