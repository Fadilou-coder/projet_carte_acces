package com.example.projet_carte.controller.api;

import com.example.projet_carte.dto.VisiteDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Visite")
public interface VisiteApi {

    @PostMapping("/visites/create/visiteur")
    VisiteDto saveVisiteur(@RequestBody VisiteDto visiteDto);

    @PostMapping("/visites/create/apprenant")
    VisiteDto saveApprenant(@RequestBody VisiteDto visiteDto);

    @GetMapping("/visites")
    List<VisiteDto> findAll();

    @GetMapping("/visites/{date}")
    List<VisiteDto> findByDate(@PathVariable String date);

    @GetMapping("/visites/{date}/{vst}")
    List<VisiteDto> findByDateByVisiteur(@PathVariable("date") String date, @PathVariable("vst") String vst);

    @GetMapping("visites/apprenant")
    List<VisiteDto> findVisitesApp();

    @GetMapping("visites/visiteur")
    List<VisiteDto> findVisitesVisiteur();

    @PostMapping("visites/sortieApprenant")
    VisiteDto SortieApprenant(@RequestBody VisiteDto visiteDto);

    @PostMapping("visites/sortieVisiteur")
    VisiteDto SortieVisiteur(@RequestBody VisiteDto visiteDto);
}
