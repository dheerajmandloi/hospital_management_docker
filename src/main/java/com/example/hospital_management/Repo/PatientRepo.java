package com.example.hospital_management.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hospital_management.Models.PatientModel;

public interface  PatientRepo extends JpaRepository<PatientModel, Long>{
    
public PatientModel findByEmail(String email);
    
}