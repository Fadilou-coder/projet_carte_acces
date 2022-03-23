package com.example.projet_carte.service.impl;


import com.example.projet_carte.dto.PromoDto;
import com.example.projet_carte.repository.PromoRepository;
import com.example.projet_carte.service.PromoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PromoServiceImpl implements PromoService {

    PromoRepository promoRepository;

    @Override
    public List<PromoDto> findAll() {
        return promoRepository.findAllByArchiveFalse().stream().map(PromoDto::fromEntity).collect(Collectors.toList());
    }
}
