package ec.edu.espe.ms_clientes.repositories;

import ec.edu.espe.ms_clientes.models.Vehiculo;
import ec.edu.espe.ms_clientes.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, UUID> {
    
    Optional<Vehiculo> findByPlaca(String placa);
    
    List<Vehiculo> findByPropietario(Persona propietario);
    
    List<Vehiculo> findByPropietarioId(UUID propietarioId);
    
    List<Vehiculo> findByActivoTrue();
    
    @Query("SELECT v FROM Vehiculo v WHERE v.marca LIKE %:marca%")
    List<Vehiculo> findByMarcaContaining(@Param("marca") String marca);
    
    @Query("SELECT v FROM Vehiculo v WHERE v.modelo LIKE %:modelo%")
    List<Vehiculo> findByModeloContaining(@Param("modelo") String modelo);
    
    List<Vehiculo> findByAnioFabricacion(Integer anio);
    
    List<Vehiculo> findByColor(String color);
    
    @Query("SELECT v FROM Vehiculo v WHERE v.anioFabricacion BETWEEN :anioInicio AND :anioFin")
    List<Vehiculo> findByAnioFabricacionBetween(@Param("anioInicio") Integer anioInicio, @Param("anioFin") Integer anioFin);
    
    boolean existsByPlaca(String placa);
}