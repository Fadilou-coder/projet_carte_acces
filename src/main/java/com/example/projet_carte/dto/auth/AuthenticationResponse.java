package com.example.projet_carte.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthenticationResponse {

  private String accessToken;
  private List<String> role;
  private Long id;
}
