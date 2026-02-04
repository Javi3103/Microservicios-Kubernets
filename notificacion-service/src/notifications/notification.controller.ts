import { Controller, Get } from "@nestjs/common";
import { ApiOperation, ApiResponse, ApiTags } from "@nestjs/swagger";
import { NotificationService } from "./notification.service";
import { NotificationResponseDto } from "./DTO/notification.response.dto";

@ApiTags('notifications')
@Controller('notifications')
export class NotificationController {
    constructor(private readonly notificationService: NotificationService,) {}

    @Get()
    @ApiOperation({ 
        summary: 'Obtener todas las notificaciones',
        description: 'Retorna todas las notificaciones almacenadas en el sistema.' 
    })

    @ApiResponse({
        status: 200,
        description: 'Lista de notificaciones obtenida exitosamente.',
        type: [NotificationResponseDto],
    })
    
    async findAll() {
        return await this.notificationService.findAll();
    }

}