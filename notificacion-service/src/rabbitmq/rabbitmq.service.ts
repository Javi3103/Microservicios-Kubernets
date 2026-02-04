import { Injectable, OnModuleDestroy, OnModuleInit, Logger } from "@nestjs/common";
import { Connection , connect, Channel, ConsumeMessage } from "amqplib";
import { NotificationService } from "src/notifications/notification.service";
import { ConfigService } from "@nestjs/config";
import { NotificationEvent } from "./interfaces/notification-event.interface";

@Injectable()
export class RabbitMQService implements OnModuleInit, OnModuleDestroy {
    private readonly logger = new Logger(RabbitMQService.name);

    private connection: Connection;
    private channel: Channel;
    private readonly exchangeName = 'notification.exchange';
    private readonly queueName = 'notification.queue';
    private readonly routingKey = 'notification.routingKey';

    constructor(
        private readonly configService: ConfigService,
        private readonly notificationService: NotificationService,
    ) { }

    async onModuleInit() {
        await this.connect();
        await this.setupQueue();
        await this.consumeMessages();
    }
    async onModuleDestroy() {
        this.closeConnection();
    }

    private async connect(): Promise<void> {
        try {
            const host = this.configService.get<string>('RABBITMQ_HOST');
            const port = this.configService.get<number>('RABBITMQ_PORT');
            const username = this.configService.get<string>('RABBITMQ_USERNAME');
            const password = this.configService.get<string>('RABBITMQ_PASSWORD');
            const connectionString = `amqp://${username}:${password}@${host}:${port}`;

            this.connection = await connect(connectionString);
            this.channel = await this.connection.createChannel();

            this.logger.log('Connected to RabbitMQ successfully');

        } catch (error) {
            this.logger.error('Failed to connect to RabbitMQ', error);
            throw error;
        }
    }

    private async closeConnection(): Promise<void> {
        try {
            if (!this.channel) {
                await this.channel.close();
            }
            if (!this.connection) {
                await this.connection.close();
            }
            this.logger.log('RabbitMQ connection closed successfully');
        } catch (error) {
            this.logger.error('Failed to close RabbitMQ connection', error);
        }
    }

    private async setupQueue(): Promise<void> {
    try {
      // Declarar exchange
      await this.channel.assertExchange(this.exchangeName, 'topic', {
        durable: true,
      });

      // Declarar cola
      await this.channel.assertQueue(this.queueName, {
        durable: true,
      });

      // Enlazar cola al exchange
      await this.channel.bindQueue(
        this.queueName,
        this.exchangeName,
        this.routingKey,
      );

      this.logger.log(`Cola ${this.queueName} configurada correctamente`);
    } catch (error) {
      this.logger.error('Error configurando la cola:', error);
      throw error;
    }
  }

  private async consumeMessages(): Promise<void> {
    try {
      await this.channel.consume(
        this.queueName,
        async (message: ConsumeMessage | null) => {
          if (message) {
            try {
              const contentString = message.content.toString();
              this.logger.debug(`Mensaje recibido: ${contentString}`);

              const content = JSON.parse(contentString) as NotificationEvent;

              this.logger.log(
                `Nueva notificación recibida: ${content.action} - ${content.entityType}`,
              );
              this.logger.debug(
                `Timestamp recibido: ${content.timestamp}, tipo: ${typeof content.timestamp}`,
              );

              // Parsear el timestamp correctamente
              let eventTimestamp: Date;
              if (content.timestamp) {
                try {
                  // Intentar parsear como ISO string
                  eventTimestamp = new Date(content.timestamp);

                  // Si es inválido, usar fecha actual
                  if (isNaN(eventTimestamp.getTime())) {
                    this.logger.warn(
                      `Timestamp inválido: ${content.timestamp}, usando fecha actual`,
                    );
                    eventTimestamp = new Date();
                  }
                } catch (error) {
                  this.logger.warn(
                    `Error parseando timestamp: ${error}, usando fecha actual`,
                  );
                  eventTimestamp = new Date();
                }
              } else {
                this.logger.warn(
                  `Timestamp no proporcionado, usando fecha actual`,
                );
                eventTimestamp = new Date();
              }

              // Guardar en base de datos
              await this.notificationService.create({
                eventId: content.id,
                microservice: content.microservice,
                action: content.action,
                entityType: content.entityType,
                entityId: content.entityId,
                message: content.message,
                eventTimestamp: eventTimestamp.toISOString(), // Enviar como ISO string
                data: content.data || {},
                severity: content.severity || 'INFO',
              });

              // Confirmar recepción del mensaje
              this.channel.ack(message);
            } catch (error) {
              this.logger.error('Error procesando mensaje:', error);
              this.logger.error(
                'Contenido del mensaje:',
                message.content.toString(),
              );

              // Rechazar mensaje (no lo reintenta)
              this.channel.nack(message, false, false);
            }
          }
        },
        {
          noAck: false, // Confirmación manual
        },
      );

      this.logger.log(`Consumiendo mensajes de la cola: ${this.queueName}`);
    } catch (error) {
      this.logger.error('Error iniciando consumo de mensajes:', error);
      throw error;
    }
  }
}