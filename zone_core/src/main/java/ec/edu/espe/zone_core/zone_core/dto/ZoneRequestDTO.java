package ec.edu.espe.zone_core.zone_core.dto;

import ec.edu.espe.zone_core.zone_core.models.TypeZone;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ZoneRequestDTO {
    private String name;
    private String description;
    private Integer capacity;
    private TypeZone type;
    private Boolean isActive;
}
