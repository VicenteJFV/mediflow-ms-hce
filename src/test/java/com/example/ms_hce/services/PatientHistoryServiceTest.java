package com.example.ms_hce.services;

import com.example.ms_hce.exceptions.ResourceNotFoundException;
import com.example.ms_hce.models.ClinicalHistory;
import com.example.ms_hce.models.Patient;
import com.example.ms_hce.models.PatientHistoryDto;
import com.example.ms_hce.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientHistoryServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientHistoryMapper patientHistoryMapper;

    @InjectMocks
    private PatientHistoryService patientHistoryService;

    private PatientHistoryDto patientHistoryDto;
    private Patient patient;
    private ClinicalHistory clinicalHistory;

    @BeforeEach
    void setUp() {
        patientHistoryDto = PatientHistoryDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123456789")
                .birthDate(LocalDate.of(1990, 1, 1))
                .documentNumber("12345678A")
                .allergies("Penicillin")
                .chronicConditions("Hypertension")
                .build();

        patient = Patient.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123456789")
                .birthDate(LocalDate.of(1990, 1, 1))
                .documentNumber("12345678A")
                .build();

        clinicalHistory = ClinicalHistory.builder()
                .id(1L)
                .allergies("Penicillin")
                .chronicConditions("Hypertension")
                .patient(patient)
                .build();

        patient.setClinicalHistory(clinicalHistory);
    }

    @Test
    void savePatientHistory_Success_NewPatient() {
        // Arrange
        when(patientHistoryMapper.toEntity(any(PatientHistoryDto.class))).thenReturn(patient);
        when(patientRepository.findByDocumentNumber(patient.getDocumentNumber())).thenReturn(Optional.empty());
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
        when(patientHistoryMapper.toDto(any(Patient.class))).thenReturn(patientHistoryDto);

        // Act
        PatientHistoryDto result = patientHistoryService.savePatientHistory(patientHistoryDto);

        // Assert
        assertNotNull(result);
        assertEquals(patientHistoryDto.getFirstName(), result.getFirstName());
        assertEquals(patientHistoryDto.getEmail(), result.getEmail());
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void savePatientHistory_Success_UpdateExistingPatient() {
        // Arrange
        Patient existingPatient = Patient.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("987654321")
                .birthDate(LocalDate.of(1990, 1, 1))
                .documentNumber("12345678A")
                .build();
        ClinicalHistory existingHistory = ClinicalHistory.builder()
                .id(1L)
                .allergies("None")
                .chronicConditions("None")
                .patient(existingPatient)
                .build();
        existingPatient.setClinicalHistory(existingHistory);

        when(patientHistoryMapper.toEntity(any(PatientHistoryDto.class))).thenReturn(patient);
        when(patientRepository.findByDocumentNumber(patient.getDocumentNumber())).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
        when(patientHistoryMapper.toDto(any(Patient.class))).thenReturn(patientHistoryDto);

        // Act
        PatientHistoryDto result = patientHistoryService.savePatientHistory(patientHistoryDto);

        // Assert
        assertNotNull(result);
        verify(patientRepository, times(1)).save(patient);
        assertEquals(1L, patient.getId());
        assertEquals(1L, patient.getClinicalHistory().getId());
    }

    @Test
    void getPatientHistory_Success() {
        // Arrange
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientHistoryMapper.toDto(patient)).thenReturn(patientHistoryDto);

        // Act
        PatientHistoryDto result = patientHistoryService.getPatientHistory(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
    }

    @Test
    void getPatientHistory_NotFound() {
        // Arrange
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            patientHistoryService.getPatientHistory(1L);
        });
    }
}
