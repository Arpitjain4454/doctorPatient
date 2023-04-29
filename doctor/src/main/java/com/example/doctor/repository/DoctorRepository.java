package com.example.doctor.repository;

import com.example.doctor.controller.Doctor;
import com.example.doctor.controller.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findByCityAndSpeciality(String city, Speciality speciality);
}
