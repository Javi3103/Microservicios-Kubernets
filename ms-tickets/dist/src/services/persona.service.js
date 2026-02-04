"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.PersonaService = void 0;
const fetchClient_1 = require("../utils/fetchClient");
class PersonaService {
    constructor() {
        this.getPersonaByIdentificacion = async (identificacion) => {
            const url = `${this.baseUrl}/personas/identificacion/${identificacion}`; //revisar si el endpoint esta creado
            return await fetchClient_1.FetchClient.get(url);
        };
        this.getVehiculoByPlaca = async (placa) => {
            const url = `${this.baseUrl}/vehiculos/placa/${placa}`;
            return await fetchClient_1.FetchClient.get(url);
        };
        /**
       * Verifica si un vehículo pertenece a una persona específica.
       * @param personaId - ID de la persona
       * @param vehiculoId - ID del vehículo
       * @returns Promise con booleano indicando si pertenece
       */
        this.validateVehiculoBelongsToPersona = async (personaId, vehiculoId) => {
            const url = `${this.baseUrl}/api/vehiculos/propietario/${personaId}`;
            const vehiculos = await fetchClient_1.FetchClient.get(url);
            return vehiculos.some(v => v.id === vehiculoId);
        };
        this.baseUrl = process.env.PERSONA_SERVICE_URL || process.env.USER_SERVICE_URL || "http://localhost:8081/api";
        if (!this.baseUrl) {
            throw new Error("No se pudo establecer la conexión con el servicio de usuarios");
        }
    }
    ;
}
exports.PersonaService = PersonaService;
