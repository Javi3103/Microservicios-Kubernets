import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { CreateNotificationDto } from './DTO/create-notification.dto';
import { Notification } from './entities/notification.entity';

@Injectable()
export class NotificationService{
    constructor(
        @InjectRepository(Notification)
        private readonly notificationRepository: Repository<Notification>,
    ){}

    async create(createNotificationDto: CreateNotificationDto): Promise<Notification> {
        const notification = this.notificationRepository.create({...createNotificationDto});
        return await this.notificationRepository.save(notification);
    }

    async findAll(): Promise<Notification[]> {
        return await this.notificationRepository.find();
    }

}