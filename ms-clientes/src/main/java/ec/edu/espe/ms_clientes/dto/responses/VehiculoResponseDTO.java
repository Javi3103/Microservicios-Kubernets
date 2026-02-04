package ec.edu.espe.ms_clientes.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoResponseDTO {
    
    private UUID id;
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    private Integer anioFabricacion;
    @JsonProperty("propietarioId")
    private UUID idPropietario;
    private String nombrePropietario;
    private String tipoVehiculo;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
}