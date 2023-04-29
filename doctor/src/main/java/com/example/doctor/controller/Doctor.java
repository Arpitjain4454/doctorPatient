package com.example.doctor.controller;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Speciality is mandatory")
    private Speciality speciality;

    @NotBlank(message = "City is mandatory")
    @Size(max = 20, message = "City name must be at most 20 characters")
    private String city;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;
}
