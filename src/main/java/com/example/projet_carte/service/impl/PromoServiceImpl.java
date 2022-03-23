package com.example.projet_carte.service.impl;


import com.example.projet_carte.dto.PromoDto;
import com.example.projet_carte.exception.ErrorCodes;
import com.example.projet_carte.exception.InvalidEntityException;
import com.example.projet_carte.repository.PromoRepository;
import com.example.projet_carte.service.PromoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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

    @Override
    public PromoDto save(PromoDto promoDto) {
        if (Objects.equals(promoDto.getLibelle(), ""))
            throw new InvalidEntityException("Veuillez renseignez le libelle de la promo");
        LocalDate date = LocalDate.now();
        promoDto.setAnnee(date.toString().substring(0, 4));

        return PromoDto.fromEntity(promoRepository.save(PromoDto.toEntity(promoDto)));
    }
}
