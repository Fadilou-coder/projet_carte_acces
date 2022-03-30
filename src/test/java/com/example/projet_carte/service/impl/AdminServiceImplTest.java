package com.example.projet_carte.service.impl;

import com.example.projet_carte.dto.AdminDto;
import com.example.projet_carte.dto.StructureDto;
import com.example.projet_carte.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceImplTest {

    @Autowired
    private AdminService service;

    @Test
    public void shouldSaveAdminWithSuccess() throws Exception{

        AdminDto expectedAdmin = AdminDto.builder()
                .prenom("Omar")
                .nom("Faye")
                .email("faye@gmail.com")
                .phone("771491580")
                .addresse("Thies")
                .cni("1234123412344")
                .password("password")
                .structure(new StructureDto(1L))
                .build();

        AdminDto saveAdmin = service.save(expectedAdmin);

        assertNotNull(saveAdmin);
        assertNotNull(saveAdmin.getId());
        assertNotNull(expectedAdmin.getPrenom(), saveAdmin.getPrenom());
        assertNotNull(expectedAdmin.getNom(), saveAdmin.getNom());
        assertNotNull(expectedAdmin.getEmail(), saveAdmin.getEmail());
        assertNotNull(expectedAdmin.getPhone(), saveAdmin.getPhone());
        assertNotNull(expectedAdmin.getAddresse(), saveAdmin.getAddresse());
        assertNotNull(expectedAdmin.getCni(), saveAdmin.getCni());
        assertNotNull(expectedAdmin.getPassword(), saveAdmin.getPassword());

    }
}
