package ec.edu.espe.ms_clientes.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "automovil")
@EqualsAndHashCode(callSuper = false)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Automovil extends Vehiculo{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAutomovil tipoAutomovil;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCombustible tipoCombustible;

    @Column(nullable = false)
    private Double cilindraje; // En litros (ej: 1.6, 2.0)

    @Column(nullable = false)
    private Boolean suspensionDeportiva;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTraccion traccion;

    @Column(nullable = false)
    private Boolean tieneBalde; // Para camionetas

    @Column(nullable = false)
    private Integer numeroPuertas;

    @Column(nullable = false)
    private Integer numeroAsientos;

    @Column
    private Double capacidadTanque; // En litros
}
