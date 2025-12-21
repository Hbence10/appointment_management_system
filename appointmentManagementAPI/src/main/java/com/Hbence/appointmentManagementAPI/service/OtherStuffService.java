package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Details;
import com.Hbence.appointmentManagementAPI.entity.History;
import com.Hbence.appointmentManagementAPI.entity.OpeningDetails;
import com.Hbence.appointmentManagementAPI.entity.Rules;
import com.Hbence.appointmentManagementAPI.repository.DetailsRepository;
import com.Hbence.appointmentManagementAPI.repository.HistoryRepository;
import com.Hbence.appointmentManagementAPI.repository.OpeningDetailsRepository;
import com.Hbence.appointmentManagementAPI.repository.RuleRepository;
import com.Hbence.appointmentManagementAPI.service.other.ValidatorCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

@Transactional(noRollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, SQLException.class})
@Service
@RequiredArgsConstructor
public class OtherStuffService {

    private final RuleRepository ruleRepository;
    private final HistoryRepository historyRepository;
    private final DetailsRepository detailsRepository;
    private final OpeningDetailsRepository openingDetailsRepository;

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

            if (updatedRules.getId() > 1 || updatedRules.getId() < 1) {
                return ResponseEntity.notFound().build();
            } else {
                updatedRules.setLastEditAt(new Date());
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

    //    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Details> updateDetails(Details updatedDetails) {
        try {
            if (updatedDetails == null) {
                return ResponseEntity.status(422).build();
            }

            if (updatedDetails.getId() != 1) {
                return ResponseEntity.notFound().build();
            } else if (!ValidatorCollection.emailChecker(updatedDetails.getEmail())) {
                return ResponseEntity.status(415).build();
            } else {
                return ResponseEntity.ok().body(detailsRepository.save(updatedDetails));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<OpeningDetails>> getOpeningDetails() {
        return ResponseEntity.ok().body(openingDetailsRepository.findAll());
    }

    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<List<OpeningDetails>> updateOpeningDetails(List<OpeningDetails> openingDetails) {
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

            return ResponseEntity.ok().body(openingDetailsRepository.findAll());
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