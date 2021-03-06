package com.example.projet_carte.controller;

import com.example.projet_carte.controller.api.PromoApi;
import com.example.projet_carte.dto.PromoDto;
import com.example.projet_carte.service.PromoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PromoController implements PromoApi {

    PromoService service;

    @Override
    public List<PromoDto> findAll() {
        return service.findAll();
    }

    @Override
    public PromoDto save(PromoDto promoDto) {
        return service.save(promoDto);
    }

    @Override
    public PromoDto putPromo(PromoDto promoDto, Long id) {
        return service.putPromo(promoDto, id);
    }
}
