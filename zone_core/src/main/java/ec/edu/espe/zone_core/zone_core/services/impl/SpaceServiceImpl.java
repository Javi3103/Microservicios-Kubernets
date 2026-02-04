package ec.edu.espe.zone_core.zone_core.services.impl;

import ec.edu.espe.zone_core.zone_core.dto.SpaceResponseDTO;
import ec.edu.espe.zone_core.zone_core.messaging.NotificationProducer;
import ec.edu.espe.zone_core.zone_core.dto.SpaceRequestDTO;
import ec.edu.espe.zone_core.zone_core.models.Space;
import ec.edu.espe.zone_core.zone_core.models.Zone;
import ec.edu.espe.zone_core.zone_core.repositories.SpaceRepository;
import ec.edu.espe.zone_core.zone_core.repositories.ZoneRepository;
import ec.edu.espe.zone_core.zone_core.services.SpaceService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SpaceServiceImpl implements SpaceService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private NotificationProducer notificationProducer;

    @Override
    public SpaceResponseDTO createSpace(SpaceRequestDTO dto) {

        if (spaceRepository.existsByCode(dto.getCode())) {
            throw new EntityExistsException("El espacio con id " + dto.getCode() + " ya existe");
        }

        Zone zone = zoneRepository.findById(dto.getIdZone())
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));

        Space space = Space.builder()
                .code(dto.getCode())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .zone(zone)
                .build();

        Space savedSpace = spaceRepository.save(space);

        notificationProducer.sendSpaceCreated(savedSpace.getId(), savedSpace.getCode(), savedSpace.getZone().getId());

        return convertToDTO(savedSpace);
    }

    @Override
    public SpaceResponseDTO updateSpace(UUID id, SpaceRequestDTO dto) {
        Space space = spaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));

        Zone zone = zoneRepository.findById(dto.getIdZone())
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));

        space.setCode(dto.getCode());
        space.setStatus(dto.getStatus());
        space.setPriority(dto.getPriority());
        space.setZone(zone);

        Space updatedSpace = spaceRepository.save(space);
        return convertToDTO(updatedSpace);
    }

    @Override
    public List<SpaceResponseDTO> getSpaces() {
        return spaceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpaceResponseDTO> getAvailableSpacesByZone(UUID zoneId) {
        return spaceRepository.findByZoneId(zoneId).stream()
                .filter(space -> space.getStatus() != null && space.getStatus().name().equals("AVAILABLE"))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSpace(UUID id) {
        if (!spaceRepository.existsById(id)) {
            throw new RuntimeException("Espacio no encontrado");
        }
        spaceRepository.deleteById(id);

    }

    @Override
    public SpaceResponseDTO getSpace(UUID id) {
        Space space = spaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));
        return convertToDTO(space);
    }

    private SpaceResponseDTO convertToDTO(Space space) {
        return SpaceResponseDTO.builder()
                .id(space.getId())
                .code(space.getCode())
                .status(space.getStatus())
                .isReserved(Boolean.FALSE)
                .priority(space.getPriority())
                .idZone(space.getZone() != null ? space.getZone().getId() : null)
                .zoneName(space.getZone() != null ? space.getZone().getName() : null)
                .build();
    }
}
