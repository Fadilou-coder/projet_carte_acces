package com.example.projet_carte.controller.api;

import com.example.projet_carte.dto.PromoDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("promo")
public interface PromoApi {
    @GetMapping("/promos")
    List<PromoDto> findAll();

    @PostMapping("/promos/create")
    PromoDto save(@RequestBody PromoDto promoDto);

    @PutMapping("/promos/{id}")
    PromoDto putPromo(@RequestBody PromoDto promoDto, @PathVariable Long id);
}
