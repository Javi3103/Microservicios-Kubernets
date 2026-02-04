package ec.edu.espe.zone_core.zone_core.dto;

import ec.edu.espe.zone_core.zone_core.models.SpaceStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
public class SpaceResponseDTO {
    private UUID id;
    private String code;
    private SpaceStatus status;
    private Boolean isReserved;
    @JsonProperty("zoneId")
    private UUID idZone;
    private Integer priority;
    private String zoneName;
}
