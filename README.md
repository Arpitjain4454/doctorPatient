Patient Management System
Introduction
This is a Patient Management System that allows doctors to register their patients through a mobile or web portal. It includes backend APIs to achieve tasks like adding a doctor, adding a patient & their symptom, and suggesting the doctor based on the patient's symptom. The system is built using the Spring Boot framework for core functionalities and Hibernate for carrying out database operations.

Features
Add a doctor to the platform
Remove a doctor from the platform
Add a patient to the platform with their symptom
Remove a patient from the platform
Suggest doctors based on the patient's location and symptom
Validations for name, city, email, and phone number fields
Edge cases handled for suggesting doctors in case of no doctors in the location or for the patient's symptom
APIs
POST /doctors/add: Add a doctor to the platform
DELETE /doctors/delete/{id}: Remove a doctor from the platform by ID
POST /patients/add: Add a patient to the platform with their symptom
DELETE /patients/delete/{id}: Remove a patient from the platform by ID
GET /patients/suggest-doctors/{patientId}: Suggest doctors based on the patient's location and symptom
Tech Stack
Java
Spring Boot
Hibernate
MySQL
Summary
This Patient Management System provides an efficient way for doctors to manage their patients through a mobile or web portal. The system is built using the Spring Boot framework for core functionalities and Hibernate for carrying out database operations. It includes APIs for adding and removing doctors and patients, suggesting doctors based on the patient's location and symptom, and validations for the fields.
