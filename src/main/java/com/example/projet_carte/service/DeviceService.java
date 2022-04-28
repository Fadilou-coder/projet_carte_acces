package com.example.projet_carte.service;

import com.example.projet_carte.dto.DeviceDto;

import java.util.List;

public interface DeviceService {

    public DeviceDto save(DeviceDto deviceDto);

    public DeviceDto findDevice(String mac);

    public void deleteDevice(String mac);

    public List<DeviceDto> findAllDevice();
}
