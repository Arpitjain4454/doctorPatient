package com.example.doctor.service;

import com.example.doctor.controller.Doctor;
import com.example.doctor.controller.Patient;
import com.example.doctor.controller.Speciality;
import com.example.doctor.repository.DoctorRepository;
import com.example.doctor.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public Patient addPatient(Patient patientDto) {
        // Create a new patient entity and populate its fields
        Patient patient = new Patient();
        patient.setName(patientDto.getName());
        patient.setCity(patientDto.getCity());
        patient.setEmail(patientDto.getEmail());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setSymptom(patientDto.getSymptom());

        // Save the patient entity to the database
        return patientRepository.save(patient);
    }

    public List<Doctor> suggestDoctors(Long patientId) throws PatientNotFoundException, DoctorNotFoundException, LocationNotFoundException {
        // Retrieve the patient entity from the database
        Optional<Patient> patientOptional = patientRepository.findById(patientId);
        if (!patientOptional.isPresent()) {
            throw new PatientNotFoundException("Patient with id " + patientId + " not found");
        }
        Patient patient = patientOptional.get();

        // Retrieve the doctors from the database based on the patient's location and symptom
        List<Doctor> doctors = doctorRepository.findByCityAndSpeciality(patient.getCity(), Speciality.valueOf(getSpeciality(String.valueOf(patient.getSymptom()))));

        // Handle the edge cases
        if (doctors.isEmpty()) {
            throw new DoctorNotFoundException("There isn't any doctor present at your location for your symptom");
        } else if (!isValidLocation(patient.getCity())) {
            throw new LocationNotFoundException("We are still waiting to expand to your location");
        }

        return doctors;
    }

    // Helper method to map a patient's symptom to a doctor's speciality
    private String getSpeciality(String symptom) {
        switch (symptom.toLowerCase()) {
            case "arthritis":
            case "back pain":
            case "tissue injuries":
                return "Orthopedic";
            case "dysmenorrhea":
                return "Gynecology";
            case "skin infection":
            case "skin burn":
                return "Dermatology";
            case "ear pain":
                return "ENT specialist";
            default:
                return "";
        }
    }

    // Helper method to check if a location is valid
    private boolean isValidLocation(String location) {
        return location.equalsIgnoreCase("Delhi") ||
                location.equalsIgnoreCase("Noida") ||
                location.equalsIgnoreCase("Faridabad");
    }

    public boolean deletePatientById(Long id) {
        patientRepository.deleteById(id);
        return false;
    }

    public Optional<Patient> findPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }
}
