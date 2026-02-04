package ec.edu.espe.ms_clientes.dto.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoRequestDTO {
    
    @NotBlank(message = "La placa no puede estar vacía")
    @Pattern(regexp = "^[A-Z]{3}-[0-9]{3,4}$|^[A-Z]{2}-[0-9]{4}$", 
             message = "La placa debe tener el formato AAA-000 o AA-0000")
    private String placa;
    
    @NotBlank(message = "La marca no puede estar vacía")
    private String marca;
    
    @NotBlank(message = "El modelo no puede estar vacío")
    private String modelo;
    
    @NotBlank(message = "El color no puede estar vacío")
    private String color;
    
    @NotNull(message = "El año de fabricación no puede estar vacío")
    @Min(value = 1900, message = "El año de fabricación debe ser mayor a 1900")
    @Max(value = 2025, message = "El año de fabricación no puede ser futuro")
    private Integer anioFabricacion;
    
    @NotNull(message = "El ID del propietario no puede estar vacío")
    private UUID idPropietario;
}