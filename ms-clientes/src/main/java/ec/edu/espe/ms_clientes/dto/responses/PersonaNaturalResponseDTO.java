package ec.edu.espe.ms_clientes.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonaNaturalResponseDTO extends PersonaResponseDTO {
    
    private String apellido;
    private LocalDateTime fechaNacimiento;
    private String genero;
    private Integer edad;
}