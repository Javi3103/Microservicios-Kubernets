package ec.edu.espe.zone_core.zone_core.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ZoneResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private Integer capacity;
    private String type;
    private Boolean isActive;
}