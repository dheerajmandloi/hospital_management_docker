package com.example.hospital_management.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PatientModel {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    Long id;
    
    String fullname;
    String email;
    String number;
    String password;
    String role;

    public PatientModel(){}

    public PatientModel (String fullname,String email,String number,String password,String role)
    {
        this.fullname=fullname;
        this.email=email;
        this.number=number;
        this.password=password;
        this.role=role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
public String getNumber()
 { return number; }
public void setNumber(String number)
 { this.number = number; }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    
    
    

    
}
