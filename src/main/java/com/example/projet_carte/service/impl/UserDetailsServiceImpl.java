package com.example.projet_carte.service.impl;


import com.example.projet_carte.model.Admin;
import com.example.projet_carte.model.Apprenant;
import com.example.projet_carte.model.SuperAdmin;
import com.example.projet_carte.model.Superviseur;
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
        Admin admin = service.findUserByEmailAdmin(username);
        SuperAdmin superAdmin = service.findUserByEmailSuperAdmin(username);
        Apprenant apprenant = service.findUserByEmailApprenant(username);
        Superviseur superviseur = service.findUserByEmailSuperviseur(username);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (admin != null) {
            authorities.add(new SimpleGrantedAuthority(admin.getRole()));
            return new User(admin.getEmail(), admin.getPassword(), authorities);
        }else if (superAdmin != null){
            authorities.add(new SimpleGrantedAuthority(superAdmin.getRole()));
            return new User(superAdmin.getEmail(), superAdmin.getPassword(), authorities);
        }else if (apprenant != null){
            authorities.add(new SimpleGrantedAuthority(apprenant.getRole()));
            return new User(apprenant.getEmail(), apprenant.getPassword(), authorities);
        }else if (superviseur != null){
            authorities.add(new SimpleGrantedAuthority(superviseur.getRole()));
            return new User(superviseur.getEmail(), superviseur.getPassword(), authorities);
        }
        else
            throw new UsernameNotFoundException(username);
    }
}
