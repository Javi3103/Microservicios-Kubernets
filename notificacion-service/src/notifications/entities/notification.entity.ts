import { Entity, PrimaryGeneratedColumn, Column, Index, CreateDateColumn } from 'typeorm';

@Entity('notification')
export class Notification {
    @PrimaryGeneratedColumn('uuid')
    id: string;

    @Column({ name: 'event_id', nullable: false })
    @Index()
    eventId: string;

    @Column({ name: 'microservice', nullable: false })
    microservice: string;

    @Column({ name: 'entity_type', nullable: false })
    entityType: string;

    @CreateDateColumn({ name: 'created_at' })
    createdAt: Date;

    @Column({ name: 'severity', length: 10, default: 'INFO' })
    severity: string;

    @Column({ name: 'read', type: 'boolean', default: false })
    read: boolean;

    @Column({ name: 'processed', type: 'boolean', default: false })
    processed: boolean;

    @Column({ name: 'data', type: 'jsonb', nullable: false })
    data: Record<string, any>;

    @Column({ name: 'entity_id', nullable: false })
    @Index()
    entityId: string;

    @Column({name: 'event_timestamp', type: 'timestamp'})
    eventTimestamp: Date;
}