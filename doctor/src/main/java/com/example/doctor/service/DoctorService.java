package com.example.doctor.service;

import com.example.doctor.controller.Doctor;
import com.example.doctor.controller.Speciality;
import com.example.doctor.controller.Symptom;
import com.example.doctor.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.example.doctor.controller.Symptom.*;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void removeDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public List<Doctor> findDoctorsBySymptomAndCity(Symptom symptom, String city) {
        Speciality speciality = getSpecialityForSymptom(symptom);
        if (speciality == null) {
            return Collections.emptyList();
        }
        return doctorRepository.findByCityAndSpeciality(city, speciality);
    }

    private Speciality getSpecialityForSymptom(Symptom symptom) {
        switch (symptom) {
            case ARTHRITIS:
            case BACK_PAIN:
            case TISSUE_INJURIES:
                return Speciality.ORTHOPEDIC;
            case DYSMENORRHEA:
                return Speciality.GYNECOLOGY;
            case SKIN_INFECTION:
            case SKIN_BURN:
                return Speciality.DERMATOLOGY;
            case EAR_PAIN:
                return Speciality.ENT;
            default:
                return null;
        }
    }
}
