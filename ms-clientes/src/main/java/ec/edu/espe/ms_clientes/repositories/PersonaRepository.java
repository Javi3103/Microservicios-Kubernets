package ec.edu.espe.ms_clientes.repositories;

import ec.edu.espe.ms_clientes.models.Persona;
import ec.edu.espe.ms_clientes.models.PersonaNatural;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, UUID> {
    
    Optional<Persona> findByIdentificacion(String identificacion);
    
    Optional<Persona> findByEmail(String email);
    
    Optional<Persona> findByTelefono(String telefono);

    List<Persona> findByActivoTrue();

    @Query("SELECT p FROM Persona p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND p.activo = true")
    List<Persona> findByNombreContainingIgnoringCase(@Param("nombre") String nombre);
    
    boolean existsByIdentificacion(String identificacion);
    
    boolean existsByEmail(String email);
    
    boolean existsByTelefono(String telefono);

    @Query("SELECT pn FROM PersonaNatural pn WHERE pn.activo = true")
    List<PersonaNatural> findPersonasNaturalesActivas();
}