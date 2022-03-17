package com.example.projet_carte.dataFixtures;

import com.example.projet_carte.model.Admin;
import com.example.projet_carte.model.SuperAdmin;
import com.example.projet_carte.repository.AdminRepository;
import com.example.projet_carte.repository.StructureRepository;
import com.example.projet_carte.repository.SuperAdminRepository;
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
    private StructureRepository structureRepository;

    @Override
    public void run(String... args) {
        String password = "password";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        adminRepository.saveAll(Arrays.asList(
                new Admin("Omar", "Faye", "omzo@gmail.com", "777777777", "Scat Urbain", "1123123401234", encodedPassword, structureRepository.findAll().get(0)),
                new Admin("Mamadou", "Sylla", "sylla@gmail.com", "707777777", "Dakar", "1123123401235", encodedPassword, structureRepository.findAll().get(0))
        ));

        superAdminRepository.save(new SuperAdmin("Babacar", "Goudiaby", "cbag@gmail.com", "787777777", "Guédiawaye", "1123123401245", encodedPassword));

    }
}
