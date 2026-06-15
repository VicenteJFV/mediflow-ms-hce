package com.example.ms_hce.repositories;

import com.example.ms_hce.models.ClinicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClinicalHistoryRepository extends JpaRepository<ClinicalHistory, Long> {
    Optional<ClinicalHistory> findByPatientId(Long patientId);
}
