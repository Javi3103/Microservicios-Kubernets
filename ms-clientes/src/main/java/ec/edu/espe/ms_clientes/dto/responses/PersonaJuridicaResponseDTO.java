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
public class PersonaJuridicaResponseDTO extends PersonaResponseDTO {
    
    private String razonSocial;
    private String representanteLegal;
    private String actividadEconomica;
}