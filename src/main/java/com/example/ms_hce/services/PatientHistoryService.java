package com.example.ms_hce.services;

import com.example.ms_hce.exceptions.ResourceNotFoundException;
import com.example.ms_hce.models.Patient;
import com.example.ms_hce.models.PatientHistoryDto;
import com.example.ms_hce.repositories.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientHistoryService {

    private final PatientRepository patientRepository;
    private final PatientHistoryMapper patientHistoryMapper;

    public PatientHistoryService(PatientRepository patientRepository,
                                 PatientHistoryMapper patientHistoryMapper) {
        this.patientRepository = patientRepository;
        this.patientHistoryMapper = patientHistoryMapper;
    }

    @Transactional
    public PatientHistoryDto savePatientHistory(PatientHistoryDto dto) {
        Patient patient = patientHistoryMapper.toEntity(dto);

        // Check if there is an existing patient with same document number
        if (patient.getDocumentNumber() != null) {
            patientRepository.findByDocumentNumber(patient.getDocumentNumber()).ifPresent(existing -> {
                patient.setId(existing.getId());
                if (existing.getClinicalHistory() != null && patient.getClinicalHistory() != null) {
                    patient.getClinicalHistory().setId(existing.getClinicalHistory().getId());
                }
            });
        }

        Patient savedPatient = patientRepository.save(patient);
        return patientHistoryMapper.toDto(savedPatient);
    }

    @Transactional(readOnly = true)
    public PatientHistoryDto getPatientHistory(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
        return patientHistoryMapper.toDto(patient);
    }
}
