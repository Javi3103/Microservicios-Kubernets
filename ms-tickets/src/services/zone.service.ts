import { get } from "node:http";
import { FetchClient } from "../utils/fetchClient";

interface ZoneResponse {
    id: string;
    name: string;
    description: string;
    capacity: number;
    type: string;
    isActive: boolean;
}

interface SpaceResponse {
    id: string;
    code: string;
    status: string;
    isReserved: boolean;
    zoneId: string;
    zoneName: string;
    priority: number;
}

export class ZoneService {
    private readonly baseUrl: string;

    constructor() {
        this.baseUrl = process.env.ZONE_SERVICE_URL || "http://localhost:8080/api";
        if (!this.baseUrl) {
            throw new Error("No se pudo establecer la conexión con el servicio de zonas");
        }
    };

    getAllZones = async (): Promise<ZoneResponse[]> => {
        const url = `${this.baseUrl}/zones`;
        return await FetchClient.get<ZoneResponse[]>(url);
    };

    getAvailableSpacesByZone = async (zoneId: string): Promise<SpaceResponse[]> => {
        const url = `${this.baseUrl}/spaces/availablesbyzone/${zoneId}`; //falta este endpoint
        return await FetchClient.get<SpaceResponse[]>(url);
    };

    getAvailableSpaces = async (): Promise<SpaceResponse[]> => {
        const url = `${this.baseUrl}/spaces/availables`;
        return await FetchClient.get<SpaceResponse[]>(url);
    }

    /**
   * Obtiene una zona específica por su ID.
   * @param zoneId - ID de la zona a buscar
   * @returns Promise con la zona encontrada
   */
    getZoneById = async (zoneId: string): Promise<ZoneResponse> => {
        const url = `${this.baseUrl}/api/zones/${zoneId}`;
        return await FetchClient.get<ZoneResponse>(url);
    };

    /**
     * Actualiza el estado de un espacio (por ejemplo, marcarlo como ocupado).
     * @param spaceId - ID del espacio a actualizar
     * @param status - Nuevo estado (AVAILABLE, OCCUPIED, MAINTENANCE)
     * @returns Promise con el espacio actualizado
     */
    updateSpaceStatus = async (spaceId: string, status: string): Promise<SpaceResponse> => {
        const url = `${this.baseUrl}/api/spaces/${spaceId}/status?status=${status}`;
        return await FetchClient.patch<SpaceResponse>(url, {});
    };


    /**
   * Obtiene un espacio específico por su código.
   * @param code - Código del espacio a buscar
   * @returns Promise con el espacio encontrado
   */
  getSpaceByCode = async (code: string): Promise<SpaceResponse> => {
    const url = `${this.baseUrl}/api/spaces`;
    const allSpaces = await FetchClient.get<SpaceResponse[]>(url);
    
    const space = allSpaces.find(s => s.code === code);
    if (!space) {
      throw new Error(`Espacio con código ${code} no encontrado`);
    }
    
    return space;
  };
}
