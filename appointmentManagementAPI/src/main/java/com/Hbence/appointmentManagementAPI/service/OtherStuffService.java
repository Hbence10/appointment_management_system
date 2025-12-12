package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.repository.*;
import com.Hbence.appointmentManagementAPI.service.other.ValidatorCollection;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class OtherStuffService {

    private final RuleRepository ruleRepository;
    private final GalleryRepository galleryRepository;
    private final HistoryRepository historyRepository;
    private final DetailsRepository detailsRepository;
    private final OpeningDetailsRepository openingDetailsRepository;

    //Galleria:
    public ResponseEntity<List<Gallery>> getGalleryImages() {
        try {
            return ResponseEntity.ok(galleryRepository.findAll().stream().filter(image -> !image.getIsDeleted()).toList());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Gallery> updateGalleryImage(Gallery updatedGalleryImage) {
        try {
            if (updatedGalleryImage == null) {
                return ResponseEntity.status(422).build();
            }

            return ResponseEntity.ok(galleryRepository.save(updatedGalleryImage));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //Szabalyzat:
    public ResponseEntity<Rules> getRule() {
        try {
            return ResponseEntity.ok(ruleRepository.findById(Long.valueOf(1)).get());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Rules> updateRules(Rules updatedRules) {
        try {
            if (updatedRules == null) {
                return ResponseEntity.status(422).build();
            }

            if (updatedRules.getId() > 1) {
                return ResponseEntity.notFound().build();
            } else {
                updatedRules.setLastEditAt(LocalDateTime.now());
                return ResponseEntity.ok(ruleRepository.save(updatedRules));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //History
    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<List<History>> getHistory() {
        try {
            return ResponseEntity.ok().body(historyRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //Adatok
    public ResponseEntity<Details> getDetails() {
        return ResponseEntity.ok().body(detailsRepository.findById(1).orElse(null));
    }

    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Details> updateDetails(Details updatedDetails) {
        try {
            if (updatedDetails == null) {
                return ResponseEntity.status(422).build();
            }
            if (updatedDetails.getId() != 1) {
                return ResponseEntity.status(415).build();
            } else if (!ValidatorCollection.emailChecker(updatedDetails.getEmail())) {
                return ResponseEntity.status(415).build();
            } else {
                detailsRepository.save(updatedDetails);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<OpeningDetails>> getOpeningDetails() {
        return ResponseEntity.ok().body(openingDetailsRepository.findAll());
    }

    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<OpeningDetails> updateOpeningDetails(List<OpeningDetails> openingDetails) {
        try {
            if (openingDetails == null) {
                return ResponseEntity.status(422).build();
            }

            for (OpeningDetails openingDetail : openingDetails) {
                if (openingDetail.getId() == null || openingDetail.getId() > 7 || openingDetail.getId() < 1) {
                    return ResponseEntity.notFound().build();
                } else if (!checkStartAndEnd(openingDetail.getStartTime(), openingDetail.getEndTime())) {
                    return ResponseEntity.status(415).build();
                } else {
                    openingDetailsRepository.save(openingDetail);
                }
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //
    public Boolean checkStartAndEnd(Date start, Date end) {
        int startMin = (start.getHours() * 60) + start.getMinutes();
        int endMin = (end.getHours() * 60) + end.getMinutes();

        return startMin < endMin;
    }
}

/*
 * HTTP STATUS KODOK:
 *   - 200: Sikeres muvelet
 *   - 404: Not Found
 *   - 409: Mar foglalt nev
 *   - 415: Unsupported Media Type --> Ha az adott adat invalid
 *   - 422: Hianyzo parameter/response body
 *   - 500: Internal Server Error
 * */