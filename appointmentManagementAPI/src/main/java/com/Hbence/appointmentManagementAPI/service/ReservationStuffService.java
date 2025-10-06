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
        if (newReservationType.getId() != null) {
            return ResponseEntity.notFound().build();
        } else {
            newReservationType.setName(newReservationType.getName().trim());
            return ResponseEntity.ok(reservationTypeRepository.save(newReservationType));
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<String> deleteReservationType(Long id) {
        ReservationType searchedType = reservationTypeRepository.findById(id).get();

        if (searchedType == null || searchedType.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            searchedType.setIsDeleted(true);
            searchedType.setDeletedAt(new Date());
            return ResponseEntity.ok().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<ReservationType> updateReservationType(ReservationType updatedReservationType) {
        if (updatedReservationType.getId() == null || updatedReservationType.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            updatedReservationType.setName(updatedReservationType.getName().trim());
            return ResponseEntity.ok(reservationTypeRepository.save(updatedReservationType));
        }
    }

    //Fizetesi modszerek
    public ResponseEntity<List<PaymentMethods>> getAllPaymentMethod() {
        return ResponseEntity.ok(paymentMethodRepository.findAll());
    }

    //Telefonszam
    public ResponseEntity<List<PhoneCountryCode>> getAllPhoneCode() {
        return ResponseEntity.ok(phoneCountryCodeRepository.findAll());
    }
}
