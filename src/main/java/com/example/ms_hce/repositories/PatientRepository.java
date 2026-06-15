package com.example.ms_hce.repositories;

import com.example.ms_hce.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByDocumentNumber(String documentNumber);
    Optional<Patient> findByEmail(String email);
}
