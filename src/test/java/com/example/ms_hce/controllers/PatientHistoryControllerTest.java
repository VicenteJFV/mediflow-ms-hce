package com.example.ms_hce.controllers;

import com.example.ms_hce.exceptions.ResourceNotFoundException;
import com.example.ms_hce.models.PatientHistoryDto;
import com.example.ms_hce.services.PatientHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientHistoryController.class)
class PatientHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientHistoryService patientHistoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private PatientHistoryDto patientHistoryDto;

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
    }

    @Test
    void createOrUpdatePatientHistory_Success() throws Exception {
        // Arrange
        when(patientHistoryService.savePatientHistory(any(PatientHistoryDto.class))).thenReturn(patientHistoryDto);

        // Act & Assert
        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientHistoryDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.allergies").value("Penicillin"));
    }

    @Test
    void getPatientHistory_Success() throws Exception {
        // Arrange
        when(patientHistoryService.getPatientHistory(1L)).thenReturn(patientHistoryDto);

        // Act & Assert
        mockMvc.perform(get("/api/patients/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.allergies").value("Penicillin"));
    }

    @Test
    void getPatientHistory_NotFound() throws Exception {
        // Arrange
        when(patientHistoryService.getPatientHistory(1L)).thenThrow(new ResourceNotFoundException("Patient not found with id: 1"));

        // Act & Assert
        mockMvc.perform(get("/api/patients/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
