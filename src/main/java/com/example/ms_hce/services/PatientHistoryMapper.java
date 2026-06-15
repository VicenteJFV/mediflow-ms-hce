package com.example.ms_hce.services;

import com.example.ms_hce.models.ClinicalHistory;
import com.example.ms_hce.models.Patient;
import com.example.ms_hce.models.PatientHistoryDto;
import org.springframework.stereotype.Component;

@Component
public class PatientHistoryMapper {

    public PatientHistoryDto toDto(Patient patient) {
        if (patient == null) {
            return null;
        }

        PatientHistoryDto.PatientHistoryDtoBuilder builder = PatientHistoryDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .email(patient.getEmail())
                .phoneNumber(patient.getPhoneNumber())
                .birthDate(patient.getBirthDate())
                .documentNumber(patient.getDocumentNumber());

        if (patient.getClinicalHistory() != null) {
            builder.allergies(patient.getClinicalHistory().getAllergies())
                   .chronicConditions(patient.getClinicalHistory().getChronicConditions());
        }

        return builder.build();
    }

    public Patient toEntity(PatientHistoryDto dto) {
        if (dto == null) {
            return null;
        }

        Patient patient = Patient.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .birthDate(dto.getBirthDate())
                .documentNumber(dto.getDocumentNumber())
                .build();

        ClinicalHistory clinicalHistory = ClinicalHistory.builder()
                .allergies(dto.getAllergies())
                .chronicConditions(dto.getChronicConditions())
                .patient(patient)
                .build();

        patient.setClinicalHistory(clinicalHistory);

        return patient;
    }
}
