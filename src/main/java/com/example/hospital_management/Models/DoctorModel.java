package com.example.hospital_management.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DoctorModel {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    Long Id;

    String fullname;
    String specialization;
    int experience;
    float fee;
    String description;

    // NEW FIELDS
    String email;
    String password;
    String role = "doctor";

    public DoctorModel(){}

    public DoctorModel(String fullname, String specialization, int experience, float fee, String description, String email, String password) {
        this.fullname = fullname;
        this.specialization = specialization;
        this.experience = experience;
        this.fee = fee;
        this.description = description;
        this.email = email;
        this.password = password;
    }

    // GETTERS & SETTERS
    public Long getId() { return Id; }
    public void setId(Long id) { Id = id; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }

    public float getFee() { return fee; }
    public void setFee(float fee) { this.fee = fee; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
