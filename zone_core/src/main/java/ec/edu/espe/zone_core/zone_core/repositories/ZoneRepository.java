package ec.edu.espe.zone_core.zone_core.repositories;

import ec.edu.espe.zone_core.zone_core.models.Zone;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import java.util.UUID;

public interface ZoneRepository extends JpaRepositoryImplementation<Zone, UUID> {
}
