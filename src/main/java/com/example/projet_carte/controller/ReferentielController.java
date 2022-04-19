package com.example.projet_carte.controller;

import com.example.projet_carte.controller.api.ReferentielApi;
import com.example.projet_carte.dto.ReferentielDto;
import com.example.projet_carte.service.ReferentielService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ReferentielController implements ReferentielApi {
    ReferentielService referentielService;

    @Override
    public List<ReferentielDto> findAll() {
        return referentielService.findAll();
    }

    @Override
    public ReferentielDto save(ReferentielDto referentielDto) {
        return referentielService.save(referentielDto);
    }

    @Override
    public ReferentielDto putRef(ReferentielDto referentielDto, Long id) {
        return referentielService.putPromo(referentielDto, id);
    }
}
