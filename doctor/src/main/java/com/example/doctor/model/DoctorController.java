package com.example.doctor.model;

import com.example.doctor.controller.Doctor;
import com.example.doctor.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public Doctor addDoctor(@RequestBody Doctor doctor) {
        return doctorService.addDoctor(doctor);
    }

    @DeleteMapping("/{id}")
    public void removeDoctor(@PathVariable Long id) {
        doctorService.removeDoctor(id);
    }
}
