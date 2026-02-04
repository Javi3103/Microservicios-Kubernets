package ec.edu.espe.zone_core.zone_core.messaging;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ec.edu.espe.zone_core.zone_core.config.RabbitMQConfig;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;

    private static final String ACTION_CREATE = "create";
    private static final String ACTION_UPDATE = "update";
    private static final String ACTION_DELETE = "delete";

    private static final String ENTITY_ZONE = "ZONE";
    private static final String ENTITY_SPACE = "SPACE";

    private static final String SEVERITY_INFO = "INFO";
    private static final String SEVERITY_WARN = "WARN";

    private static final String IP_CLIENT = "IP";

    public void sendNotification(String action, String entityType, UUID entityId, String message,
            Map<String, Object> data) {

        NotificationEvent event = NotificationEvent.builder()
                .id(UUID.randomUUID())
                .microservice("microservice-zones")
                .entityType(entityType)
                .entityId(entityId)
                .message(message)
                .action(action)
                .timestamp(LocalDateTime.now())
                .data(data != null ? data : new HashMap<>())
                .severity(SEVERITY_INFO)
                .build();

        try {
            log.debug("Enviando notificacion: {}", event);
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, event);
            log.info("Notificacion enviada: {}", event, action);

        } catch (Exception e) {
            log.error("Error al enviar la notificacion: {}", event, action, e.getMessage());
        }
    }

    public void sendSpaceCreated(UUID spaceId, String space, UUID zoneId) {
        Map<String, Object> data = new HashMap<>();
        data.put("spaceId", spaceId);
        data.put("zoneID", zoneId.toString());
        sendNotification(ACTION_CREATE, ENTITY_SPACE, spaceId, "Espacio creado", data);
    }

    public void sendZoneDeleted(UUID zoneId, String zone) {
        Map<String, Object> data = new HashMap<>();
        data.put("zone", zone);
        sendNotification(ACTION_DELETE, ENTITY_ZONE, zoneId, "Zona eliminada", data);
    }

    public void sendZoneUpdated(UUID zoneId, String zone) {
        Map<String, Object> data = new HashMap<>();
        data.put("zone", zone);
        sendNotification(ACTION_UPDATE, ENTITY_ZONE, zoneId, "Zona actualizada", data);
    }

    public void sendZoneCreated(UUID zoneId, String zone) {
        Map<String, Object> data = new HashMap<>();
        data.put("zone", zone);
        sendNotification(ACTION_CREATE, ENTITY_ZONE, zoneId, "Zona creada", data);
    }

    public void sendSpaceDeleted(UUID spaceId, String space, UUID zoneId) {
        Map<String, Object> data = new HashMap<>();
        data.put("spaceId", spaceId);
        data.put("zoneID", zoneId.toString());
        sendNotification(ACTION_DELETE, ENTITY_SPACE, spaceId, "Espacio eliminado", data);
    }

    public void sendSpaceUpdated(UUID spaceId, String space, UUID zoneId) {
        Map<String, Object> data = new HashMap<>();
        data.put("spaceId", spaceId);
        data.put("zoneID", zoneId.toString());
        sendNotification(ACTION_UPDATE, ENTITY_SPACE, spaceId, "Espacio actualizado", data);
    }

}
