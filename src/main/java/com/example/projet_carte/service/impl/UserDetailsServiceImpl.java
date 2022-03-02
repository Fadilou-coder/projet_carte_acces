package com.example.projet_carte.service.impl;


import com.example.projet_carte.model.Admin;
import com.example.projet_carte.model.SuperAdmin;
import com.example.projet_carte.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private ApplicationService service;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = service.findUserByUsernameAdmin(username);
        SuperAdmin superAdmin = service.findUserByUsernameSuperAdmin(username);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (admin != null) {
            authorities.add(new SimpleGrantedAuthority(admin.getRole()));
            return new User(admin.getUsername(), admin.getPassword(), authorities);
        }else if (superAdmin != null){
            authorities.add(new SimpleGrantedAuthority(superAdmin.getRole()));
            return new User(superAdmin.getUsername(), superAdmin.getPassword(), authorities);
        }
        else
            throw new UsernameNotFoundException(username);
    }


}
