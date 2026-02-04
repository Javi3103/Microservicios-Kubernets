package ec.edu.espe.zone_core.zone_core.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "spaces")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Space {
    @Id
    @GeneratedValue(generator = "UUID")
    @org.hibernate.annotations.GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(length = 20, nullable = false, unique = true)
    private String code; //G304

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpaceStatus status;

    @Column(nullable = false)
    private Integer priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;
}
