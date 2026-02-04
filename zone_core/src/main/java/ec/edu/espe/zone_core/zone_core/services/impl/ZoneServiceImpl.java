package ec.edu.espe.zone_core.zone_core.services.impl;

import ec.edu.espe.zone_core.zone_core.dto.ZoneRequestDTO;
import ec.edu.espe.zone_core.zone_core.dto.ZoneResponseDTO;
import ec.edu.espe.zone_core.zone_core.models.Zone;
import ec.edu.espe.zone_core.zone_core.repositories.ZoneRepository;
import ec.edu.espe.zone_core.zone_core.services.ZoneService;
import ec.edu.espe.zone_core.zone_core.messaging.NotificationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private NotificationProducer notificationProducer;

    @Override
    public ZoneResponseDTO createZone(ZoneRequestDTO dto) {
        Zone zone = Zone.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .capacity(dto.getCapacity())
                .type(dto.getType())
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : Boolean.TRUE)
                .build();

        Zone savedZone = zoneRepository.save(zone);
        // Enviar notificación de creación de zona
        try {
            notificationProducer.sendZoneCreated(savedZone.getId(), savedZone.getName());
        } catch (Exception ex) {
            // Evitar que falle la operación principal por error de notificación
        }
        return convertToDTO(savedZone);
    }

    @Override
    public ZoneResponseDTO updateZone(UUID id, ZoneRequestDTO dto) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));

        zone.setName(dto.getName());
        zone.setDescription(dto.getDescription());
        zone.setCapacity(dto.getCapacity());
        zone.setType(dto.getType());
        zone.setIsActive(dto.getIsActive());
        Zone updatedZone = zoneRepository.save(zone);
        return convertToDTO(updatedZone);
    }

    @Override
    public List<ZoneResponseDTO> getZones() {
        return zoneRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ZoneResponseDTO getById(UUID id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));
        return convertToDTO(zone);
    }

    @Override
    public void deleteById(UUID id) {
        if (!zoneRepository.existsById(id)) {
            throw new RuntimeException("Zona no encontrado");
        }
        zoneRepository.deleteById(id);
    }

    private ZoneResponseDTO convertToDTO(Zone zone) {
        return ZoneResponseDTO.builder()
                .id(zone.getId())
                .name(zone.getName())
                .description(zone.getDescription())
                .capacity(zone.getCapacity())
                .type(zone.getType().name())
                .isActive(zone.getIsActive())
                .build();
    }
}
