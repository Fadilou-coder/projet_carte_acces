package com.example.projet_carte.service.impl;

import com.example.projet_carte.dto.DeviceDto;
import com.example.projet_carte.exception.EntityNotFoundException;
import com.example.projet_carte.exception.ErrorCodes;
import com.example.projet_carte.exception.InvalidEntityException;
import com.example.projet_carte.model.DeviceAutoriser;
import com.example.projet_carte.model.Promo;
import com.example.projet_carte.repository.DevicesRepository;
import com.example.projet_carte.service.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    DevicesRepository repository;

    @Override
    public DeviceDto save(DeviceDto deviceDto) {
        if (Objects.equals(deviceDto.getMacAdress(), ""))
            throw new InvalidEntityException("Veuillez renseignez l'adresse mac");

        return DeviceDto.fromEntity(repository.save(DeviceDto.toEntity(deviceDto)));
    }

    @Override
    public DeviceDto findDevice(String mac) {
        if (repository.findByMacAdress(mac).isPresent()){
            return DeviceDto.fromEntity(repository.findByMacAdress(mac).get());
        }
        return null;
    }

    @Override
    public void deleteDevice(String mac) {
        DeviceAutoriser device = repository.findByMacAdress(mac).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Appareil avec l'ID = " + mac + " ne se trouve dans la BDD", ErrorCodes.DEVICE_NOT_FOUND));
        repository.delete(device);
    }

    @Override
    public List<DeviceDto> findAllDevice() {
        return repository.findAll().stream().map(DeviceDto :: fromEntity).collect(Collectors.toList());
    }
}
