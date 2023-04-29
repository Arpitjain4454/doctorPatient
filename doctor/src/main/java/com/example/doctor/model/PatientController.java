package com.example.doctor.model;

import com.example.doctor.controller.Doctor;
import com.example.doctor.controller.Patient;
import com.example.doctor.controller.Speciality;
import com.example.doctor.service.DoctorService;
import com.example.doctor.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;
    private final DoctorService doctorService;

    public PatientController(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @PostMapping("")
    public ResponseEntity<?> addPatient(@Valid @RequestBody Patient patientDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Patient patient = new Patient();
        patient.setName(patientDto.getName());
        patient.setCity(patientDto.getCity());
        patient.setEmail(patientDto.getEmail());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setSymptom(patientDto.getSymptom());

        List<Doctor> doctors = doctorService.findDoctorsBySpecialityAndCity(patient.getSymptom(), patient.getCity());
        if (doctors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There isn't any doctor present at your location for your symptom");
        }

        Patient savedPatient = patientService.addPatient(patient);
        return ResponseEntity.created(URI.create("/patients/" + savedPatient.getId())).body(savedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {
        boolean isDeleted = patientService.deletePatientById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientService.findPatientById(id);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllPatients() {
        List<Patient> patients = patientService.findAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}/suggest-doctors")
    public ResponseEntity<?> suggestDoctors(@PathVariable Long id) {
        Optional<Patient> patient = patientService.findPatientById(id);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }

        List<Doctor> doctors = doctorService.findDoctorsBySpecialityAndCity(patient.isEmpty(), patient.get());
        if (doctors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There isn't any doctor present at your location for your symptom");
        } else {
            return ResponseEntity.ok(doctors);
        }
    }
}
