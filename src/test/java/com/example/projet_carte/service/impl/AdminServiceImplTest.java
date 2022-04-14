/*
package com.example.projet_carte.service.impl;

import com.example.projet_carte.dto.AdminDto;
import com.example.projet_carte.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
                .email("fye@gmail.com")
                .phone("772291531")
                .addresse("Thies")
                .numPiece("1234123441126")
                .password("password")
                .build();

        AdminDto saveAdmin = service.save(expectedAdmin);

        assertNotNull(saveAdmin);
        assertNotNull(saveAdmin.getId());
        assertNotNull(expectedAdmin.getPrenom(), saveAdmin.getPrenom());
        assertNotNull(expectedAdmin.getNom(), saveAdmin.getNom());
        assertNotNull(expectedAdmin.getEmail(), saveAdmin.getEmail());
        assertNotNull(expectedAdmin.getPhone(), saveAdmin.getPhone());
        assertNotNull(expectedAdmin.getAddresse(), saveAdmin.getAddresse());
        assertNotNull(expectedAdmin.getNumPiece(), saveAdmin.getNumPiece());
        assertNotNull(expectedAdmin.getPassword(), saveAdmin.getPassword());

    }

   */
/* @Test
    public void shouldUpdateAdminWithSuccess() throws Exception {

        AdminDto expectedAdmin = AdminDto.builder()
                .prenom("Oumar")
                .nom("Faya")
                .email("zaza@gmail.com")
                .phone("774004041")
                .addresse("Thies")
                .numPiece("1234023442324")
                .password("passeraa1")
               // .isbloqued(true)
                .build();

        AdminDto saveAdmin = service.save(expectedAdmin);

        AdminDto adminToUpdate = saveAdmin;
        adminToUpdate.setPrenom("Al faroukh");
       *//*
*/
/* adminToUpdate.setNom("Diop");
        adminToUpdate.setEmail("dalleee@gmail.com");
        adminToUpdate.setPhone("770000089");
        adminToUpdate.setAddresse("Dakar");
        adminToUpdate.setCni("1112223333033");
        adminToUpdate.setPassword("passsssss");
        adminToUpdate.setStructure(new StructureDto(2L, "SAAAA", true, new ArrayList<>()));
*//*
*/
/*
        saveAdmin = service.save(adminToUpdate);

        assertNotNull(adminToUpdate);
        assertNotNull(adminToUpdate.getId());
        assertNotNull(adminToUpdate.getPrenom(), saveAdmin.getPrenom());
        assertNotNull(adminToUpdate.getNom(), saveAdmin.getNom());
        assertNotNull(adminToUpdate.getEmail(), saveAdmin.getEmail());
        assertNotNull(adminToUpdate.getPhone(), saveAdmin.getPhone());
        assertNotNull(adminToUpdate.getAddresse(), saveAdmin.getAddresse());
        assertNotNull(adminToUpdate.getNumPiece(), saveAdmin.getNumPiece());
        assertNotNull(adminToUpdate.getPassword(), saveAdmin.getPassword());

    }*//*

}
*/
