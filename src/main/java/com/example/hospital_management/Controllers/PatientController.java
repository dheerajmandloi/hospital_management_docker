package com.example.hospital_management.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital_management.Config.Jwtutils;
import com.example.hospital_management.Models.DoctorModel;
import com.example.hospital_management.Models.PatientModel;
import com.example.hospital_management.Repo.DoctorRepo;
import com.example.hospital_management.Repo.PatientRepo;

@RestController
@RequestMapping("/patient")
@CrossOrigin("*")
public class PatientController {

    @Autowired
    PatientRepo patientRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    Jwtutils jwtUtil;

    @Autowired
    DoctorRepo doctorRepo;

    @PostMapping("/register")
    public String register(@RequestBody PatientModel patient) {

    patient.setPassword(passwordEncoder.encode(patient.getPassword())); // encode karo

    patientRepo.save(patient);
    return "successfully registered";
}


    // PatientController.java
@PostMapping("/login")
public Map<String,Object> login(@RequestBody Map<String,String> loginData) {
    String email = loginData.get("email");
    String password = loginData.get("password");
    String role = loginData.get("role");

    Map<String,Object> response = new HashMap<>();

    if(role.equals("patient")){
        PatientModel user = patientRepo.findByEmail(email);
        if(user != null && passwordEncoder.matches(password, user.getPassword())){
            String token = jwtUtil.generateToken(email, user.getRole());
            response.put("userData", user);
            response.put("token", token);
            response.put("role", user.getRole());
            return response;
        }
    } else if(role.equals("doctor")){
        // Doctor login
        DoctorModel doctor = doctorRepo.findByEmail(email); // add email field in DoctorModel + repo
        if(doctor != null && passwordEncoder.matches(password, doctor.getPassword())){
            String token = jwtUtil.generateToken(email, "doctor");
            response.put("userData", doctor);
            response.put("token", token);
            response.put("role", "doctor");
            return response;
        }
    }

    throw new RuntimeException("Invalid username or password");
}   
}
