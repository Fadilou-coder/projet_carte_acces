package com.example.projet_carte.controller;

import com.example.projet_carte.controller.api.DeviceApi;
import com.example.projet_carte.dto.DeviceDto;
import com.example.projet_carte.service.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class DeviceController implements DeviceApi {

    DeviceService service;

    @Override
    public DeviceDto save(DeviceDto deviceDto) {
        return service.save(deviceDto);
    }

    @Override
    public List<DeviceDto> findAllDevices() {
        return service.findAllDevice();
    }

    @Override
    public DeviceDto findDevice(String mac) {
        return service.findDevice(mac);
    }

    @Override
    public void deleteDevice(String mac) {
        service.deleteDevice(mac);
    }
}
