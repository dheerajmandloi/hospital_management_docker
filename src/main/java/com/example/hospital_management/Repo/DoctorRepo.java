package com.example.hospital_management.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hospital_management.Models.DoctorModel;

public interface DoctorRepo extends JpaRepository<DoctorModel, Long> {
    public DoctorModel findByEmail(String email);
}
