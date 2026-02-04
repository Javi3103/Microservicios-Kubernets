package ec.edu.espe.ms_clientes.repositories;

import ec.edu.espe.ms_clientes.models.Moto;
import ec.edu.espe.ms_clientes.models.TipoMoto;
import ec.edu.espe.ms_clientes.models.TipoCombustible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MotoRepository extends JpaRepository<Moto, UUID> {
    
    List<Moto> findByTipo(TipoMoto tipo);
    
    List<Moto> findByTipoCombustible(TipoCombustible combustible);
    
    List<Moto> findByTieneCasco(Boolean casco);
    
    List<Moto> findByTieneABS(Boolean abs);
    
    List<Moto> findByNumeroRuedas(Integer ruedas);
    
    List<Moto> findByTipoFrenos(String frenos);
    
    @Query("SELECT m FROM Moto m WHERE m.cilindraje BETWEEN :min AND :max")
    List<Moto> findByCilindrajeBetween(@Param("min") Integer min, @Param("max") Integer max);
    
    @Query("SELECT m FROM Moto m WHERE m.capacidadTanque BETWEEN :min AND :max")
    List<Moto> findByCapacidadTanqueBetween(@Param("min") Double min, @Param("max") Double max);
}