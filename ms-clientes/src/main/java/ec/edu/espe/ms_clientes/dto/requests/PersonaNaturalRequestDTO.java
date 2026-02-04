package ec.edu.espe.ms_clientes.dto.requests;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
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
public class PersonaNaturalRequestDTO extends PersonaRequestDTO {
    @NotBlank(message = "El apellido no puede ser vacío")
    private String apellido;

    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDateTime fechaNacimiento;

    @Pattern(regexp = "MASCULINO|FEMENINO|VILLA", message = "El género debe ser 'MASCULINO', 'FEMENINO' o 'VILLA'")
    private String genero;
}
