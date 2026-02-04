package ec.edu.espe.zone_core.zone_core.controller;

import ec.edu.espe.zone_core.zone_core.dto.SpaceRequestDTO;
import ec.edu.espe.zone_core.zone_core.dto.SpaceResponseDTO;
import ec.edu.espe.zone_core.zone_core.services.SpaceService;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/spaces")
public class SpaceController {
    @Autowired
    private SpaceService spaceService;

    @GetMapping("/")
    public ResponseEntity<List<SpaceResponseDTO>> getAllSpaces() {
        return ResponseEntity.ok(spaceService.getSpaces());
    }

    @GetMapping("/availablesbyzone/{zoneId}")
    public ResponseEntity<List<SpaceResponseDTO>> getAvailableSpacesByZone(@PathVariable UUID zoneId) {
        return ResponseEntity.ok(spaceService.getAvailableSpacesByZone(zoneId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaceResponseDTO> getSpaceById(@PathVariable UUID id) {
        return ResponseEntity.ok(spaceService.getSpace(id));
    }
    @PostMapping("/")
    public ResponseEntity<SpaceResponseDTO> createSpace(@RequestBody SpaceRequestDTO request) {
        return ResponseEntity.status(Response.SC_CREATED).body(spaceService.createSpace(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpaceResponseDTO> updateSpace(@PathVariable UUID id, @RequestBody SpaceRequestDTO request) {
        return ResponseEntity.ok(spaceService.updateSpace(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpace(@PathVariable UUID id) {
        spaceService.deleteSpace(id);
        return ResponseEntity.noContent().build();
    }
    
}
