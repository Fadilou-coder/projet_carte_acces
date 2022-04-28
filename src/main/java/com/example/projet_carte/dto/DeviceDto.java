package com.example.projet_carte.dto;

import com.example.projet_carte.model.DeviceAutoriser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class DeviceDto {

    private Long id;
    private String macAdress;

    public static DeviceDto fromEntity(DeviceAutoriser device){
        if (device == null) return null;
        return DeviceDto.builder()
                .id(device.getId())
                .macAdress(device.getMacAdress())
                .build();
    }

    public static DeviceAutoriser toEntity(DeviceDto deviceDto){
        if (deviceDto == null) return null;
        return new DeviceAutoriser(deviceDto.getId(), deviceDto.getMacAdress());
    }
}
