package com.example.ms_hce.controllers;

import com.example.ms_hce.models.PatientHistoryDto;
import com.example.ms_hce.services.PatientHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientHistoryController {

    private final PatientHistoryService patientHistoryService;

    public PatientHistoryController(PatientHistoryService patientHistoryService) {
        this.patientHistoryService = patientHistoryService;
    }

    @PostMapping
    public ResponseEntity<PatientHistoryDto> createOrUpdatePatientHistory(@RequestBody PatientHistoryDto dto) {
        PatientHistoryDto savedDto = patientHistoryService.savePatientHistory(dto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientHistoryDto> getPatientHistory(@PathVariable Long id) {
        PatientHistoryDto dto = patientHistoryService.getPatientHistory(id);
        return ResponseEntity.ok(dto);
    }
}
