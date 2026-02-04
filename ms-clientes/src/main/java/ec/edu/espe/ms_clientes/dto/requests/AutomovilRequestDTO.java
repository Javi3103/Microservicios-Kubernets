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
public class AutomovilRequestDTO extends VehiculoRequestDTO {
    
    @NotBlank(message = "El tipo de automóvil no puede estar vacío")
    @Pattern(regexp = "SUV|CROSSOVER|SEDAN|HATCHBACK|DEPORTIVO|CAMIONETA|COUPE|CONVERTIBLE", 
             message = "El tipo debe ser uno de: SUV, CROSSOVER, SEDAN, HATCHBACK, DEPORTIVO, CAMIONETA, COUPE, CONVERTIBLE")
    private String tipoAutomovil;
    
    @NotBlank(message = "El tipo de combustible no puede estar vacío")
    @Pattern(regexp = "GASOLINA|DIESEL|HIBRIDO|ELECTRICO|GLP|GNV|HIDROGENO", 
             message = "El tipo de combustible debe ser: GASOLINA, DIESEL, HIBRIDO, ELECTRICO, GLP, GNV o HIDROGENO")
    private String tipoCombustible;
    
    @NotNull(message = "El cilindraje no puede estar vacío")
    @DecimalMin(value = "0.8", message = "El cilindraje debe ser mayor a 0.8 litros")
    @DecimalMax(value = "8.0", message = "El cilindraje debe ser menor a 8.0 litros")
    private Double cilindraje;
    
    @NotNull(message = "Debe especificar si tiene suspensión deportiva")
    private Boolean suspensionDeportiva;
    
    @NotBlank(message = "El tipo de tracción no puede estar vacío")
    @Pattern(regexp = "DELANTERA|TRASERA|INTEGRAL_4WD|AWD", 
             message = "La tracción debe ser: DELANTERA, TRASERA, INTEGRAL_4WD o AWD")
    private String traccion;
    
    @NotNull(message = "Debe especificar si tiene balde")
    private Boolean tieneBalde;
    
    @NotNull(message = "El número de puertas no puede estar vacío")
    @Min(value = 2, message = "Debe tener al menos 2 puertas")
    @Max(value = 5, message = "No puede tener más de 5 puertas")
    private Integer numeroPuertas;
    
    @NotNull(message = "El número de asientos no puede estar vacío")
    @Min(value = 2, message = "Debe tener al menos 2 asientos")
    @Max(value = 9, message = "No puede tener más de 9 asientos")
    private Integer numeroAsientos;
    
    @DecimalMin(value = "20.0", message = "La capacidad del tanque debe ser mayor a 20 litros")
    @DecimalMax(value = "150.0", message = "La capacidad del tanque debe ser menor a 150 litros")
    private Double capacidadTanque;
}