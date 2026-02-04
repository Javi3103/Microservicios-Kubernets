package ec.edu.espe.zone_core.zone_core.repositories;

import ec.edu.espe.zone_core.zone_core.models.Space;
import ec.edu.espe.zone_core.zone_core.models.SpaceStatus;
import ec.edu.espe.zone_core.zone_core.models.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpaceRepository extends JpaRepository<Space, UUID> {
    List<Space> findByZone(Zone zone);
    List<Space> findByZoneId(UUID idZone);
    List<Space> findByStatus(SpaceStatus status);
    Optional<Space> findByCode(String code);
    boolean existsByCode(String code);
}
