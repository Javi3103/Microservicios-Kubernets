import { Column, Entity, PrimaryGeneratedColumn } from 'typeorm';

@Entity('tickets')
export class Ticket {
    @PrimaryGeneratedColumn('uuid')
    id!: string;

    @Column({ name: 'codigo_ticket', unique: true })
    codigoTicket!: string;

    @Column({ name: 'persona_identificacion', nullable: false })
    personaIdentificacion!: string;

    @Column({ name: 'persona_nombres', nullable: false })
    personaNombres!: string;

    @Column({ name: 'vehiculo_placa', nullable: false })
    vehiculoPlaca!: string;

    @Column({ name: 'vehiculo_marca', nullable: false })
    vehiculoMarca!: string;

    @Column({ name: 'vehiculo_modelo', nullable: false })
    vehiculoModelo!: string;

    @Column({ name: 'zona_nombre', nullable: false })
    zonaNombre!: string;

    @Column({ name: 'espacio_codigo', nullable: false })
    espacioCodigo!: string;

    @Column({ name: 'fecha_ingreso', type: 'timestamp', nullable: false })
    fechaIngreso!: Date;

    @Column({ name: 'fecha_salida', type: 'timestamp', nullable: true })
    fechaSalida!: Date | null;

    @Column({ name: 'estado', default: 'ACTIVO' })
    //Transformar a enum
    estado!: string;

    @Column({ name: 'tiempo_estacionado', type: 'int', nullable: false })
    tiempoEstacionado!: number;

    @Column({ name: 'created_at', type: 'timestamp', default: () => 'CURRENT_TIMESTAMP' })
    createdAt!: Date;

    @Column({ name: 'updated_at', type: 'timestamp', default: () => 'CURRENT_TIMESTAMP', onUpdate: 'CURRENT_TIMESTAMP' })
    updatedAt!: Date;

    constructor(){} 
}