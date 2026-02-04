package ec.edu.espe.zone_core.zone_core.controller;

import ec.edu.espe.zone_core.zone_core.dto.ZoneRequestDTO;
import ec.edu.espe.zone_core.zone_core.dto.ZoneResponseDTO;
import ec.edu.espe.zone_core.zone_core.services.ZoneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/zones")
public class ZoneController {
    @Autowired
    private ZoneService zoneService;

    @GetMapping
    public ResponseEntity<List<ZoneResponseDTO>> getAllZones() {
        return ResponseEntity.ok(zoneService.getZones());
    }

    @PostMapping("/")
    public ResponseEntity<ZoneResponseDTO> createZone(@RequestBody ZoneRequestDTO dto) {
        return ResponseEntity.ok(zoneService.createZone(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZoneResponseDTO> updateZone(@PathVariable UUID id, @RequestBody ZoneRequestDTO dto) {
        return ResponseEntity.ok(zoneService.updateZone(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZoneResponseDTO> getZoneById(@PathVariable UUID id) {
        return ResponseEntity.ok(zoneService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable UUID id) {
        zoneService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
