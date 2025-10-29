package com.example.hospital_management.Utils;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.hospital_management.Models.DoctorModel;
import com.example.hospital_management.Repo.DoctorRepo;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import jakarta.annotation.PostConstruct;

@Component
public class AskAi {

    private final DoctorRepo doctorRepo;
    private String doctorDetails = "";

    public AskAi(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    // Initialize doctor info once
    @PostConstruct
    private void init() {
        List<DoctorModel> doctors = doctorRepo.findAll();

        for (DoctorModel doctor : doctors) {
            doctorDetails += "ID:" + doctor.getId() + "\n";
            doctorDetails += "Specialization:" + doctor.getSpecialization() + "\n";
            doctorDetails += "Experience:" + doctor.getExperience() + "\n";
            doctorDetails += "Description:" + doctor.getDescription() + "\n";
        }
    }

    // Get recommended doctor ID from Gemini AI
    public int getRecommendation(String symptoms) {
        if (symptoms == null || symptoms.isEmpty()) return -1;

        try {
            Client client = Client.builder()
                    .apiKey("AIzaSyBd_X3SqK9e-TyDVDhPzI1ObJ5IIOqrjKM")
                    .build();

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash",
                    "You are a smart healthcare recommender. Analyze the symptoms and suggest the best doctor.\n" +
                            "Doctors: " + doctorDetails + "\n" +
                            "Symptoms: " + symptoms + "\n" +
                            "Use only the IDs provided in Doctors section and suggest the best doctor.\n" +
                            "ONLY RETURN THE ID, NO TEXT OR SYMBOLS.",
                    null
            );

            String output = response.text();

            if (output != null) {
                try {
                    return Integer.parseInt(output.trim());
                } catch (NumberFormatException e) {
                    System.err.println("AI returned invalid ID: " + output);
                    return -1;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1; // fallback if AI fails
    }
}
