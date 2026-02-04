package ec.edu.espe.ms_clientes.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorColumn(name = "tipo_persona", discriminatorType = DiscriminatorType.STRING)
public abstract class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String identificacion;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 15)
    private String telefono;

    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private Boolean activo;

    @Column
    private String direccion;

    public abstract boolean validarIdentificacion();

    @PrePersist
    protected void onCreate(){
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
    }
}