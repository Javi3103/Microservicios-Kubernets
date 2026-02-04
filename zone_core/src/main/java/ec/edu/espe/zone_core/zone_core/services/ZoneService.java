package ec.edu.espe.zone_core.zone_core.services;

import ec.edu.espe.zone_core.zone_core.dto.ZoneRequestDTO;
import ec.edu.espe.zone_core.zone_core.dto.ZoneResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ZoneService {
    ZoneResponseDTO createZone(ZoneRequestDTO dto);
    ZoneResponseDTO updateZone(UUID id, ZoneRequestDTO dto);
    List<ZoneResponseDTO> getZones();
    ZoneResponseDTO getById(UUID id);
    void deleteById(UUID id);
}
