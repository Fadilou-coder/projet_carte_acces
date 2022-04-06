package com.example.projet_carte.service.impl;


import com.example.projet_carte.dto.PromoDto;
import com.example.projet_carte.dto.VisiteurDto;
import com.example.projet_carte.exception.EntityNotFoundException;
import com.example.projet_carte.exception.ErrorCodes;
import com.example.projet_carte.exception.InvalidEntityException;
import com.example.projet_carte.model.Promo;
import com.example.projet_carte.model.Visiteur;
import com.example.projet_carte.repository.PromoRepository;
import com.example.projet_carte.service.PromoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
        System.out.println(promoDto.getDateDebut());

        return PromoDto.fromEntity(promoRepository.save(PromoDto.toEntity(promoDto)));
    }

    @Override
    public PromoDto putPromo(PromoDto promoDto, Long id) {
        if (id == null) {
            return null;
        }else {
            Promo promo = promoRepository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException(
                            "Aucun apprenant avec l'ID = " + id + " ne se trouve dans la BDD",
                            ErrorCodes.APPRENANT_NOT_FOUND));
            if (!Objects.equals(promoDto.getLibelle(), ""))
                promo.setLibelle(promoDto.getLibelle());
            if (promoDto.getDateDebut() != null)
                promo.setDateDebut(promoDto.getDateDebut());
            if (promoDto.getDateFin() != null)
                promo.setDateFin(promoDto.getDateFin());
            promoRepository.flush();
            return PromoDto.fromEntity(promo);
        }
    }
}
