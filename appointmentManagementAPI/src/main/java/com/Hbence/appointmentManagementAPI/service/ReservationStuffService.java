package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.PaymentMethods;
import com.Hbence.appointmentManagementAPI.entity.PhoneCountryCode;
import com.Hbence.appointmentManagementAPI.entity.ReservationType;
import com.Hbence.appointmentManagementAPI.repository.PaymentMethodRepository;
import com.Hbence.appointmentManagementAPI.repository.PhoneCountryCodeRepository;
import com.Hbence.appointmentManagementAPI.repository.ReservationTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ReservationStuffService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final ReservationTypeRepository reservationTypeRepository;
    private final PhoneCountryCodeRepository phoneCountryCodeRepository;

    public ResponseEntity<List<ReservationType>> getAllReservationType() {
        return ResponseEntity.ok(reservationTypeRepository.findAll().stream().filter(reservationType -> !reservationType.getIsDeleted()).toList());
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<ReservationType> addNewReservationType(ReservationType newReservationType) {
        try {
           if (newReservationType == null) {
               return ResponseEntity.status(422).build();
           }

            if (newReservationType.getId() != null) {
                return ResponseEntity.notFound().build();
            } else {
                newReservationType.setName(newReservationType.getName().trim());
                return ResponseEntity.ok(reservationTypeRepository.save(newReservationType));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<String> deleteReservationType(Long id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            ReservationType searchedType = reservationTypeRepository.findById(id).get();

            if (searchedType == null || searchedType.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                searchedType.setIsDeleted(true);
                searchedType.setDeletedAt(new Date());
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<ReservationType> updateReservationType(ReservationType updatedReservationType) {
        try {
            if (updatedReservationType == null) {
                return ResponseEntity.status(422).build();
            }

            if (updatedReservationType.getId() == null || updatedReservationType.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                updatedReservationType.setName(updatedReservationType.getName().trim());
                return ResponseEntity.ok(reservationTypeRepository.save(updatedReservationType));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //Fizetesi modszerek
    public ResponseEntity<List<PaymentMethods>> getAllPaymentMethod() {
        try {
            return ResponseEntity.ok(paymentMethodRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //Telefonszam
    public ResponseEntity<List<PhoneCountryCode>> getAllPhoneCode() {
        try {
            return ResponseEntity.ok(phoneCountryCodeRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
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