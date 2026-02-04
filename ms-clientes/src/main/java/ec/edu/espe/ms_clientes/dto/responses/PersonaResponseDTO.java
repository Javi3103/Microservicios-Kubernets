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
public class PersonaResponseDTO {
    private UUID id;
    private String identificacion;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    @JsonProperty("tipoPersona")
    private String tipo;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
}
