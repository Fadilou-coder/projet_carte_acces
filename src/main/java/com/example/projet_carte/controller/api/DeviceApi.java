package com.example.projet_carte.controller.api;

import com.example.projet_carte.dto.DeviceDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("device authorised")
public interface DeviceApi {

    @PostMapping("/devices")
    DeviceDto save(@RequestBody DeviceDto deviceDto);

    @GetMapping("/devices")
    List<DeviceDto> findAllDevices();

    @GetMapping("/devices/{mac}")
    DeviceDto findDevice(@PathVariable("mac") String mac);


    @DeleteMapping("/devices/{mac}")
    void deleteDevice(@PathVariable("mac") String mac);

}
