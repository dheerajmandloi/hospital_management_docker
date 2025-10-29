package com.example.hospital_management.Controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital_management.Models.DoctorModel;
import com.example.hospital_management.Repo.DoctorRepo;



@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class DoctorController {


 @Autowired
 private DoctorRepo doctorRepo;   

 @Autowired
PasswordEncoder passwordEncoder;

@GetMapping("/list")
public List doctor()
{
    return doctorRepo.findAll();
}


@PostMapping("/add")
public String addDoctor(@RequestBody DoctorModel doctor1){
    doctor1.setPassword(passwordEncoder.encode(doctor1.getPassword())); // ✅ encode password
    doctorRepo.save(doctor1);
    return "Successfully added a doctor";
}

}



