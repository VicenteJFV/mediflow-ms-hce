package com.example.ms_hce.config;

import com.example.ms_hce.models.ClinicalHistory;
import com.example.ms_hce.models.Patient;
import com.example.ms_hce.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final PatientRepository patientRepository;

    public DatabaseSeeder(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (patientRepository.count() == 0) {
            Patient patient = Patient.builder()
                    .firstName("Juan Carlos")
                    .lastName("Gómez")
                    .email("juan.gomez@mediflow.cl")
                    .phoneNumber("+56 9 7463 8291")
                    .birthDate(LocalDate.of(1988, 8, 24))
                    .documentNumber("18.765.432-1")
                    .build();

            ClinicalHistory history = ClinicalHistory.builder()
                    .allergies("Polen, Amlodipino")
                    .chronicConditions("Hipertensión controlada")
                    .patient(patient)
                    .build();

            patient.setClinicalHistory(history);

            patientRepository.save(patient);
            System.out.println(">> DatabaseSeeder HCE: Default patient seeded successfully.");
        }
    }
}
