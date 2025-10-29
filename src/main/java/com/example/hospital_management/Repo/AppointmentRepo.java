package com.example.hospital_management.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hospital_management.Models.AppointmentModel;

public interface AppointmentRepo extends JpaRepository<AppointmentModel, Long> {

    // Patient ke appointments
    List<AppointmentModel> findByPatient_Id(Long patientId);

    // Doctor ke appointments (fixed type issue)
    List<AppointmentModel> findByDoctor_Id(Long doctorId);
}
