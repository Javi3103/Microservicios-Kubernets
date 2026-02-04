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
public class MotoResponseDTO extends VehiculoResponseDTO {
    
    private Boolean tieneCasco;
    private Integer cilindraje;
    private String tipo;
    private String tipoCombustible;
    private Boolean tieneABS;
    private Integer numeroRuedas;
    private Double capacidadTanque;
    private String tipoFrenos;
}