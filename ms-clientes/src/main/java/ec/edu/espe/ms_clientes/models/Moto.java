package ec.edu.espe.ms_clientes.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "moto")
@EqualsAndHashCode(callSuper = false)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Moto extends Vehiculo{
    
    @Column(nullable = false)
    private Boolean tieneCasco;

    @Column(nullable = false)
    private Integer cilindraje; // En centímetros cúbicos (cc)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMoto tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCombustible tipoCombustible;

    @Column(nullable = false)
    private Boolean tieneABS;

    @Column(nullable = false)
    private Integer numeroRuedas; // Generalmente 2, pero puede ser 3 para triciclos

    @Column
    private Double capacidadTanque; // En litros

    @Column
    private String tipoFrenos; // Disco, tambor, mixto
}
