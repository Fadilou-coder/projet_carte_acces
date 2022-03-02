package com.example.projet_carte.controller.api;

import com.example.projet_carte.dto.auth.AuthenticationRequest;
import com.example.projet_carte.dto.auth.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationApi {

  @PostMapping("/login")
  ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request);

}
