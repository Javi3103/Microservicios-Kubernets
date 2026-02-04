package ec.edu.espe.ms_clientes.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PersonaRequestDTO {
    @NotBlank(message = "La identificación no puede estar vacía")
    @Size(min = 10, max = 13, message = "La identificación debe tener entre 10(CI) y 13(RUC) dígitos")
    @Pattern(regexp = "\\d+", message = "La identificación solo debe contener números")
    private String identificacion;
    
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    
    @NotBlank(message = "El email no puede estar vacío")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "El email debe tener un formato válido")
    private String email;
    
    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "[0-9+\\-]+", message = "El teléfono debe contener solo números, + y -")
    private String telefono;
    
    private String direccion;
}
