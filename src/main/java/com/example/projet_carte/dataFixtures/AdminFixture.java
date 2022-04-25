package com.example.projet_carte.dataFixtures;

import com.example.projet_carte.model.Admin;
import com.example.projet_carte.model.SuperAdmin;
import com.example.projet_carte.model.Superviseur;
import com.example.projet_carte.repository.AdminRepository;
import com.example.projet_carte.repository.SuperAdminRepository;
import com.example.projet_carte.repository.SuperviseurRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@AllArgsConstructor
@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
@Order(2)
public class AdminFixture implements CommandLineRunner {

    private AdminRepository adminRepository;
    private SuperAdminRepository superAdminRepository;
    private SuperviseurRepository superviseurRepository;

    @Override
    public void run(String... args) {
        String password = "password";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        adminRepository.saveAll(Arrays.asList(
                new Admin("Omar", "Faye", "omzo@gmail.com", "777777777", "Scat Urbain", "CNI", "1113123401234", "M", encodedPassword),
                new Admin("Mamadou", "Sylla", "sylla@gmail.com", "707777777", "Dakar", "CNI", "1113123401235", "M", encodedPassword)
        ));

        superAdminRepository.save(new SuperAdmin("Babacar", "Goudiaby", "cbag@gmail.com", "787777777", "Guédiawaye", "CNI", "1113123401245", "M", encodedPassword));

        superviseurRepository.save(new Superviseur("Asna Khadim", "Gueye", "asnakhadim@gmail.com", "788777777", "HLM", "CNI", "1113123400645", "M", encodedPassword));


    }
}
