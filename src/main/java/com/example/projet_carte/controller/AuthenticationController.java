package com.example.projet_carte.controller;


import com.example.projet_carte.controller.api.AuthenticationApi;
import com.example.projet_carte.dto.auth.AuthenticationRequest;
import com.example.projet_carte.dto.auth.AuthenticationResponse;
import com.example.projet_carte.repository.AdminRepository;
import com.example.projet_carte.repository.SuperAdminRepository;
import com.example.projet_carte.service.ApplicationService;
import com.example.projet_carte.service.impl.UserDetailsServiceImpl;
import com.example.projet_carte.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AuthenticationController implements AuthenticationApi {
  private AuthenticationManager authenticationManager;
  private UserDetailsServiceImpl userDetailsService;
  private ApplicationService applicationService;
  private JwtUtil jwtUtil;
  AdminRepository adminRepository;
  SuperAdminRepository superAdminRepository;

  @Override
  public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        )
    );
    final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

    final String jwt = jwtUtil.generateToken((User) userDetails);
    List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    Long id;
    if (adminRepository.findByUsernameAndArchiveFalse(userDetails.getUsername()).isPresent())
      id = adminRepository.findByUsernameAndArchiveFalse(userDetails.getUsername()).get().getId();
    else
      id = superAdminRepository.findByUsernameAndArchiveFalse(userDetails.getUsername()).get().getId();

    return ResponseEntity.ok(
            new AuthenticationResponse(jwt,roles, id)
    );
  }

}
