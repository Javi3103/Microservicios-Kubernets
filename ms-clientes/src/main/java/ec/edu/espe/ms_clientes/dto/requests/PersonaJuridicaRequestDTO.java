package ec.edu.espe.ms_clientes.dto.requests;

import jakarta.validation.constraints.NotBlank;
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
public class PersonaJuridicaRequestDTO extends PersonaRequestDTO {
    
    @NotBlank(message = "La razón social no puede estar vacía")
    private String razonSocial;
    
    @NotBlank(message = "El representante legal no puede estar vacío")
    private String representanteLegal;
    
    private String actividadEconomica;
}
