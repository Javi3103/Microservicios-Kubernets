package ec.edu.espe.ms_clientes.repositories;

import ec.edu.espe.ms_clientes.models.Automovil;
import ec.edu.espe.ms_clientes.models.TipoAutomovil;
import ec.edu.espe.ms_clientes.models.TipoCombustible;
import ec.edu.espe.ms_clientes.models.TipoTraccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AutomovilRepository extends JpaRepository<Automovil, UUID> {
    
    List<Automovil> findByTipoAutomovil(TipoAutomovil tipo);
    
    List<Automovil> findByTipoCombustible(TipoCombustible combustible);
    
    List<Automovil> findByTraccion(TipoTraccion traccion);
    
    List<Automovil> findByNumeroPuertas(Integer puertas);
    
    List<Automovil> findByNumeroAsientos(Integer asientos);
    
    List<Automovil> findBySuspensionDeportiva(Boolean suspension);
    
    List<Automovil> findByTieneBalde(Boolean balde);
    
    @Query("SELECT a FROM Automovil a WHERE a.cilindraje BETWEEN :min AND :max")
    List<Automovil> findByCilindrajeBetween(@Param("min") Double min, @Param("max") Double max);
    
    @Query("SELECT a FROM Automovil a WHERE a.capacidadTanque BETWEEN :min AND :max")
    List<Automovil> findByCapacidadTanqueBetween(@Param("min") Double min, @Param("max") Double max);
}