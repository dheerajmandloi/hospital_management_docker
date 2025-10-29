package com.example.hospital_management.Config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.hospital_management.Models.PatientModel;
import com.example.hospital_management.Repo.PatientRepo;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private Jwtutils jwtUtil;

    @Autowired
    private PatientRepo patientRepo;

    @Override 
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,FilterChain chain )
    throws ServletException ,IOException, java.io.IOException
    {
        String path = request.getServletPath();
        
       if (path.startsWith("/patient/login") || path.startsWith("/patient/register")) {
    chain.doFilter(request, response);
    return;
}


        String authHeader= request.getHeader("Authorization");
        String token = null;
        String email=null;

        if(authHeader != null && authHeader.startsWith("Bearer "))
        {
            token= authHeader.substring(7);
            
            email= jwtUtil.extractUserName(token);
        }

       
    if(email != null && SecurityContextHolder.getContext().getAuthentication() == null)
    {
       PatientModel user = patientRepo.findByEmail(email);
       

       if(jwtUtil.validateToken(token))
       {
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user , null,new ArrayList<>());
    SecurityContextHolder.getContext().setAuthentication(authToken);

     String role = user.getRole(); // doctor ya patient
       if(request.getRequestURI().startsWith("/appointment/add") && role.equals("doctor")) {
           throw new RuntimeException("Doctors cannot create appointments");
       }
       }
    }
    chain.doFilter(request, response);

    }
}
