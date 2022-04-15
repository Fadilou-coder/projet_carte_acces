package com.example.projet_carte.repository;

import com.example.projet_carte.model.DeviceAutoriser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DevicesRepository extends JpaRepository<DeviceAutoriser, Long> {

    Optional<DeviceAutoriser> findByMacAdress(String macAdress);
}
