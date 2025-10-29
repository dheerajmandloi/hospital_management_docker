package com.example.hospital_management.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital_management.Models.AppointmentModel;
import com.example.hospital_management.Models.DoctorModel;
import com.example.hospital_management.Models.PatientModel;
import com.example.hospital_management.Repo.AppointmentRepo;
import com.example.hospital_management.Repo.DoctorRepo;
import com.example.hospital_management.Repo.PatientRepo;

@RestController
@CrossOrigin("*")
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    // ✅ All appointments
    @GetMapping("/all")
    public List<AppointmentModel> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    // ✅ Appointments for specific patient
    @GetMapping("/patient/{patientId}")
    public List<AppointmentModel> getAppointmentsByPatient(@PathVariable Long patientId) {
        return appointmentRepo.findByPatient_Id(patientId);
    }

    // ✅ Appointments for specific doctor
    @GetMapping("/doctor/{doctorId}")
    public List<AppointmentModel> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        return appointmentRepo.findByDoctor_Id(doctorId);
    }

    // ✅ Book appointment
    @PostMapping("/add/{patientId}")
    public String addAppointment(@RequestBody AppointmentModel appointment, @PathVariable Long patientId) {
        PatientModel patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Long doctorId;
        try {
            doctorId = Long.parseLong(appointment.getDoctorId());
        } catch (Exception e) {
            return "Invalid Doctor ID format!";
        }

        DoctorModel doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointmentRepo.save(appointment);

        return "Appointment booked for " + patient.getFullname() +
               " with Dr. " + doctor.getFullname();
    }

    // ✅ Confirm appointment
    @PutMapping("/book/{id}")
    public String bookAppointment(@PathVariable Long id, @RequestBody AppointmentModel updated) {
        AppointmentModel app = appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        app.setMeetingTime(updated.getMeetingTime());
        app.setStatus("Confirmed");
        appointmentRepo.save(app);

        return "Appointment confirmed successfully!";
    }

    // ✅ Delete appointment (NEW)
    @DeleteMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        if (appointmentRepo.existsById(id)) {
            appointmentRepo.deleteById(id);
            return "Appointment deleted successfully!";
        } else {
            return "Appointment not found!";
        }
    }
}
