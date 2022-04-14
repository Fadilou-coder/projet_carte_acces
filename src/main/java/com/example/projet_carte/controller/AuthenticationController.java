package com.example.projet_carte.controller;


import com.example.projet_carte.controller.api.AuthenticationApi;
import com.example.projet_carte.dto.auth.AuthenticationRequest;
import com.example.projet_carte.dto.auth.AuthenticationResponse;
import com.example.projet_carte.repository.AdminRepository;
import com.example.projet_carte.repository.ApprenantRepository;
import com.example.projet_carte.repository.SuperAdminRepository;
import com.example.projet_carte.repository.SuperviseurRepository;
import com.example.projet_carte.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AuthenticationController implements AuthenticationApi {
  private AuthenticationManager authenticationManager;
  private UserDetailsService userDetailsService;
  private JwtUtil jwtUtil;
  AdminRepository adminRepository;
  SuperAdminRepository superAdminRepository;
  ApprenantRepository apprenantRepository;
  SuperviseurRepository superviseurRepository;

  @Override
  public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

    final String jwt = jwtUtil.generateToken((User) userDetails);
    List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    Long id;
    if (adminRepository.findByEmailAndArchiveFalse(userDetails.getUsername()).isPresent())
      id = adminRepository.findByEmailAndArchiveFalse(userDetails.getUsername()).get().getId();
    else if (superAdminRepository.findByEmailAndArchiveFalse(userDetails.getUsername()).isPresent())
      id = superAdminRepository.findByEmailAndArchiveFalse(userDetails.getUsername()).get().getId();
    else if (superviseurRepository.findByEmailAndArchiveFalse(userDetails.getUsername()).isPresent())
      id = superviseurRepository.findByEmailAndArchiveFalse(userDetails.getUsername()).get().getId();
    else if (apprenantRepository.findByEmailAndArchiveFalse(userDetails.getUsername()).isPresent())
      id = apprenantRepository.findByEmailAndArchiveFalse(userDetails.getUsername()).get().getId();
    else
      id = null;

    return ResponseEntity.ok(
            new AuthenticationResponse(jwt,roles, id)
    );
  }

}
