package com.example.projet_carte.service;

import com.example.projet_carte.dto.PromoDto;

import java.util.List;

public interface PromoService {

    List<PromoDto> findAll();
    PromoDto save(PromoDto promoDto);
    PromoDto putPromo(PromoDto promoDto, Long id);
}
