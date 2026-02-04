package ec.edu.espe.ms_clientes.dto.mappers;

import ec.edu.espe.ms_clientes.dto.requests.AutomovilRequestDTO;
import ec.edu.espe.ms_clientes.dto.requests.MotoRequestDTO;
import ec.edu.espe.ms_clientes.dto.responses.AutomovilResponseDTO;
import ec.edu.espe.ms_clientes.dto.responses.MotoResponseDTO;
import ec.edu.espe.ms_clientes.dto.responses.VehiculoResponseDTO;
import ec.edu.espe.ms_clientes.models.*;
import ec.edu.espe.ms_clientes.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehiculoMapper {

    @Autowired
    private PersonaRepository personaRepository;

    /*
        CONVERTIR AUTOMOVIL REQUEST A ENTIDAD AUTOMOVIL
     */
    public Automovil toEntity(AutomovilRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Persona propietario = personaRepository.findById(dto.getIdPropietario())
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado"));

        return Automovil.builder()
                .placa(dto.getPlaca())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .color(dto.getColor())
                .anioFabricacion(dto.getAnioFabricacion())
                .propietario(propietario)
                .tipoAutomovil(TipoAutomovil.valueOf(dto.getTipoAutomovil()))
                .tipoCombustible(TipoCombustible.valueOf(dto.getTipoCombustible()))
                .cilindraje(dto.getCilindraje())
                .suspensionDeportiva(dto.getSuspensionDeportiva())
                .traccion(TipoTraccion.valueOf(dto.getTraccion()))
                .tieneBalde(dto.getTieneBalde())
                .numeroPuertas(dto.getNumeroPuertas())
                .numeroAsientos(dto.getNumeroAsientos())
                .capacidadTanque(dto.getCapacidadTanque())
                .build();
    }

    /*
        CONVERTIR MOTO REQUEST A ENTIDAD MOTO
     */
    public Moto toEntity(MotoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Persona propietario = personaRepository.findById(dto.getIdPropietario())
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado"));

        return Moto.builder()
                .placa(dto.getPlaca())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .color(dto.getColor())
                .anioFabricacion(dto.getAnioFabricacion())
                .propietario(propietario)
                .tieneCasco(dto.getTieneCasco())
                .cilindraje(dto.getCilindraje())
                .tipo(TipoMoto.valueOf(dto.getTipo()))
                .tipoCombustible(TipoCombustible.valueOf(dto.getTipoCombustible()))
                .tieneABS(dto.getTieneABS())
                .numeroRuedas(dto.getNumeroRuedas())
                .capacidadTanque(dto.getCapacidadTanque())
                .tipoFrenos(dto.getTipoFrenos())
                .build();
    }

    /*
        CONVERTIR VEHICULO A RESPONSE DTO
     */
    public VehiculoResponseDTO toDTO(Vehiculo vehiculo) {
        if (vehiculo == null) {
            return null;
        }

        return VehiculoResponseDTO.builder()
                .id(vehiculo.getId())
                .placa(vehiculo.getPlaca())
                .marca(vehiculo.getMarca())
                .modelo(vehiculo.getModelo())
                .color(vehiculo.getColor())
                .anioFabricacion(vehiculo.getAnioFabricacion())
                .idPropietario(vehiculo.getPropietario().getId())
                .nombrePropietario(vehiculo.getPropietario().getNombre())
                .tipoVehiculo(determinarTipoVehiculo(vehiculo))
                .activo(vehiculo.getActivo())
                .fechaCreacion(vehiculo.getFechaCreacion())
                .build();
    }

    /*
        CONVERTIR AUTOMOVIL A RESPONSE DTO
     */
    public AutomovilResponseDTO toDTO(Automovil automovil) {
        if (automovil == null) {
            return null;
        }

        return AutomovilResponseDTO.builder()
                .id(automovil.getId())
                .placa(automovil.getPlaca())
                .marca(automovil.getMarca())
                .modelo(automovil.getModelo())
                .color(automovil.getColor())
                .anioFabricacion(automovil.getAnioFabricacion())
                .idPropietario(automovil.getPropietario().getId())
                .nombrePropietario(automovil.getPropietario().getNombre())
                .tipoVehiculo("AUTOMOVIL")
                .activo(automovil.getActivo())
                .fechaCreacion(automovil.getFechaCreacion())
                .tipoAutomovil(automovil.getTipoAutomovil().name())
                .tipoCombustible(automovil.getTipoCombustible().name())
                .cilindraje(automovil.getCilindraje())
                .suspensionDeportiva(automovil.getSuspensionDeportiva())
                .traccion(automovil.getTraccion().name())
                .tieneBalde(automovil.getTieneBalde())
                .numeroPuertas(automovil.getNumeroPuertas())
                .numeroAsientos(automovil.getNumeroAsientos())
                .capacidadTanque(automovil.getCapacidadTanque())
                .build();
    }

    /*
        CONVERTIR MOTO A RESPONSE DTO
     */
    public MotoResponseDTO toDTO(Moto moto) {
        if (moto == null) {
            return null;
        }

        return MotoResponseDTO.builder()
                .id(moto.getId())
                .placa(moto.getPlaca())
                .marca(moto.getMarca())
                .modelo(moto.getModelo())
                .color(moto.getColor())
                .anioFabricacion(moto.getAnioFabricacion())
                .idPropietario(moto.getPropietario().getId())
                .nombrePropietario(moto.getPropietario().getNombre())
                .tipoVehiculo("MOTO")
                .activo(moto.getActivo())
                .fechaCreacion(moto.getFechaCreacion())
                .tieneCasco(moto.getTieneCasco())
                .cilindraje(moto.getCilindraje())
                .tipo(moto.getTipo().name())
                .tipoCombustible(moto.getTipoCombustible().name())
                .tieneABS(moto.getTieneABS())
                .numeroRuedas(moto.getNumeroRuedas())
                .capacidadTanque(moto.getCapacidadTanque())
                .tipoFrenos(moto.getTipoFrenos())
                .build();
    }

    private String determinarTipoVehiculo(Vehiculo vehiculo) {
        if (vehiculo instanceof Automovil) {
            return "AUTOMOVIL";
        } else if (vehiculo instanceof Moto) {
            return "MOTO";
        } else {
            return "DESCONOCIDO";
        }
    }
}