"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ZoneService = void 0;
const fetchClient_1 = require("../utils/fetchClient");
class ZoneService {
    constructor() {
        this.getAllZones = async () => {
            const url = `${this.baseUrl}/zones`;
            return await fetchClient_1.FetchClient.get(url);
        };
        this.getAvailableSpacesByZone = async (zoneId) => {
            const url = `${this.baseUrl}/spaces/availablesbyzone/${zoneId}`; //falta este endpoint
            return await fetchClient_1.FetchClient.get(url);
        };
        this.getAvailableSpaces = async () => {
            const url = `${this.baseUrl}/spaces/availables`;
            return await fetchClient_1.FetchClient.get(url);
        };
        /**
       * Obtiene una zona específica por su ID.
       * @param zoneId - ID de la zona a buscar
       * @returns Promise con la zona encontrada
       */
        this.getZoneById = async (zoneId) => {
            const url = `${this.baseUrl}/api/zones/${zoneId}`;
            return await fetchClient_1.FetchClient.get(url);
        };
        /**
         * Actualiza el estado de un espacio (por ejemplo, marcarlo como ocupado).
         * @param spaceId - ID del espacio a actualizar
         * @param status - Nuevo estado (AVAILABLE, OCCUPIED, MAINTENANCE)
         * @returns Promise con el espacio actualizado
         */
        this.updateSpaceStatus = async (spaceId, status) => {
            const url = `${this.baseUrl}/api/spaces/${spaceId}/status?status=${status}`;
            return await fetchClient_1.FetchClient.patch(url, {});
        };
        /**
       * Obtiene un espacio específico por su código.
       * @param code - Código del espacio a buscar
       * @returns Promise con el espacio encontrado
       */
        this.getSpaceByCode = async (code) => {
            const url = `${this.baseUrl}/api/spaces`;
            const allSpaces = await fetchClient_1.FetchClient.get(url);
            const space = allSpaces.find(s => s.code === code);
            if (!space) {
                throw new Error(`Espacio con código ${code} no encontrado`);
            }
            return space;
        };
        this.baseUrl = process.env.ZONE_SERVICE_URL || "http://localhost:8080/api";
        if (!this.baseUrl) {
            throw new Error("No se pudo establecer la conexión con el servicio de zonas");
        }
    }
    ;
}
exports.ZoneService = ZoneService;
