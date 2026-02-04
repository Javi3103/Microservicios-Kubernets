package ec.edu.espe.zone_core.zone_core.dto;

import java.util.UUID;

import ec.edu.espe.zone_core.zone_core.models.SpaceStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpaceRequestDTO {
    private String code;
    private SpaceStatus status;
    private Boolean isReserved;
    private UUID idZone;
    private Integer priority;
    
}
