package com.example.doctor.repository;

import com.example.doctor.controller.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // additional methods can be defined here
}
