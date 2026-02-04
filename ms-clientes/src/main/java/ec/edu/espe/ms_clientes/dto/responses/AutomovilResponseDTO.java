package ec.edu.espe.ms_clientes.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AutomovilResponseDTO extends VehiculoResponseDTO {
    
    private String tipoAutomovil;
    private String tipoCombustible;
    private Double cilindraje;
    private Boolean suspensionDeportiva;
    private String traccion;
    private Boolean tieneBalde;
    private Integer numeroPuertas;
    private Integer numeroAsientos;
    private Double capacidadTanque;
}