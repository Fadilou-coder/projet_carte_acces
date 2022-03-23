package com.example.projet_carte.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {

  private String email;
  private String password;

}
