package com.example.hospital_management.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital_management.Models.DoctorModel;
import com.example.hospital_management.Repo.AppointmentRepo;
import com.example.hospital_management.Repo.DoctorRepo;
import com.example.hospital_management.Utils.AskAi;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class RecommandationController {

    @Autowired
    private AskAi askAi;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private AppointmentRepo appointmentRepo;

    // ✅ AI Recommendation based on symptoms
    @GetMapping("/ai")
    public DoctorModel getRecommendation(@RequestParam(required = false) String symptoms) {
        String querySymptoms = symptoms;

        // Agar frontend se symptoms nahi bheje, to latest appointment se le lo
        if (querySymptoms == null || querySymptoms.isEmpty()) {
            if (appointmentRepo.count() == 0) {
                return null; // No appointments exist
            }
            querySymptoms = appointmentRepo.findAll()
                    .get(appointmentRepo.findAll().size() - 1)
                    .getSymptoms();
        }

        // Ask AI for doctor recommendation
        Long doctorId = (long) askAi.getRecommendation(querySymptoms);

        if (doctorId != -1) {
            return doctorRepo.findById(doctorId).orElse(null);
        }

        return null;
    }
}
