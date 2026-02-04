package ec.edu.espe.ms_clientes.dto.requests;

import jakarta.validation.constraints.*;
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
public class MotoRequestDTO extends VehiculoRequestDTO {
    
    @NotNull(message = "Debe especificar si tiene casco")
    private Boolean tieneCasco;
    
    @NotNull(message = "El cilindraje no puede estar vacío")
    @Min(value = 50, message = "El cilindraje debe ser mayor a 50cc")
    @Max(value = 2000, message = "El cilindraje debe ser menor a 2000cc")
    private Integer cilindraje;
    
    @NotBlank(message = "El tipo de moto no puede estar vacío")
    @Pattern(regexp = "SCOOTER|DEPORTIVA|CRUISER|TOURING|NAKED|ENDURO|MOTOCROSS|CHOPPER|TRAIL", 
             message = "El tipo debe ser: SCOOTER, DEPORTIVA, CRUISER, TOURING, NAKED, ENDURO, MOTOCROSS, CHOPPER o TRAIL")
    private String tipo;
    
    @NotBlank(message = "El tipo de combustible no puede estar vacío")
    @Pattern(regexp = "GASOLINA|DIESEL|HIBRIDO|ELECTRICO|GLP|GNV|HIDROGENO", 
             message = "El tipo de combustible debe ser: GASOLINA, DIESEL, HIBRIDO, ELECTRICO, GLP, GNV o HIDROGENO")
    private String tipoCombustible;
    
    @NotNull(message = "Debe especificar si tiene ABS")
    private Boolean tieneABS;
    
    @NotNull(message = "El número de ruedas no puede estar vacío")
    @Min(value = 2, message = "Debe tener al menos 2 ruedas")
    @Max(value = 3, message = "No puede tener más de 3 ruedas")
    private Integer numeroRuedas;
    
    @DecimalMin(value = "5.0", message = "La capacidad del tanque debe ser mayor a 5 litros")
    @DecimalMax(value = "30.0", message = "La capacidad del tanque debe ser menor a 30 litros")
    private Double capacidadTanque;
    
    private String tipoFrenos;
}