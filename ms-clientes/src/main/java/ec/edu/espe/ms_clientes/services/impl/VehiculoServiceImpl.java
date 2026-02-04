package ec.edu.espe.ms_clientes.services.impl;

import ec.edu.espe.ms_clientes.dto.mappers.VehiculoMapper;
import ec.edu.espe.ms_clientes.dto.requests.AutomovilRequestDTO;
import ec.edu.espe.ms_clientes.dto.requests.MotoRequestDTO;
import ec.edu.espe.ms_clientes.dto.responses.VehiculoResponseDTO;
import ec.edu.espe.ms_clientes.models.Automovil;
import ec.edu.espe.ms_clientes.models.Moto;
import ec.edu.espe.ms_clientes.models.TipoAutomovil;
import ec.edu.espe.ms_clientes.models.TipoCombustible;
import ec.edu.espe.ms_clientes.models.TipoMoto;
import ec.edu.espe.ms_clientes.models.TipoTraccion;
import ec.edu.espe.ms_clientes.repositories.AutomovilRepository;
import ec.edu.espe.ms_clientes.repositories.MotoRepository;
import ec.edu.espe.ms_clientes.repositories.VehiculoRepository;
import ec.edu.espe.ms_clientes.services.VehiculoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final AutomovilRepository automovilRepository;
    private final MotoRepository motoRepository;
    private final VehiculoMapper vehiculoMapper;

    @Override
    @Transactional
    public VehiculoResponseDTO crearMoto(MotoRequestDTO dto) {
        // Validar que la placa no exista
        if (vehiculoRepository.existsByPlaca(dto.getPlaca())) {
            log.error("La moto con placa {} ya existe.", dto.getPlaca());
            throw new RuntimeException("La moto con placa " + dto.getPlaca() + " ya existe.");
        }

        // Validaciones específicas de moto
        if (dto.getCilindraje() == null || dto.getCilindraje() <= 0) {
            log.error("El cilindraje debe ser mayor a 0.");
            throw new RuntimeException("El cilindraje debe ser mayor a 0.");
        }

        if (dto.getNumeroRuedas() == null || dto.getNumeroRuedas() < 2 || dto.getNumeroRuedas() > 3) {
            log.error("El número de ruedas debe ser 2 o 3.");
            throw new RuntimeException("El número de ruedas debe ser 2 o 3.");
        }

        var moto = vehiculoMapper.toEntity(dto);
        var motoGuardada = motoRepository.save(moto);
        log.info("Moto creada con id: {}", motoGuardada.getId());
        return vehiculoMapper.toDTO(motoGuardada);
    }

    @Override
    @Transactional
    public VehiculoResponseDTO crearAutomovil(AutomovilRequestDTO dto) {
        // Validar que la placa no exista
        if (vehiculoRepository.existsByPlaca(dto.getPlaca())) {
            log.error("El automóvil con placa {} ya existe.", dto.getPlaca());
            throw new RuntimeException("El automóvil con placa " + dto.getPlaca() + " ya existe.");
        }

        // Validaciones específicas de automóvil
        if (dto.getCilindraje() == null || dto.getCilindraje() <= 0) {
            log.error("El cilindraje debe ser mayor a 0.");
            throw new RuntimeException("El cilindraje debe ser mayor a 0.");
        }

        if (dto.getNumeroPuertas() == null || dto.getNumeroPuertas() < 2 || dto.getNumeroPuertas() > 5) {
            log.error("El número de puertas debe estar entre 2 y 5.");
            throw new RuntimeException("El número de puertas debe estar entre 2 y 5.");
        }

        if (dto.getNumeroAsientos() == null || dto.getNumeroAsientos() < 2 || dto.getNumeroAsientos() > 9) {
            log.error("El número de asientos debe estar entre 2 y 9.");
            throw new RuntimeException("El número de asientos debe estar entre 2 y 9.");
        }

        var automovil = vehiculoMapper.toEntity(dto);
        var automovilGuardado = automovilRepository.save(automovil);
        log.info("Automóvil creado con id: {}", automovilGuardado.getId());
        return vehiculoMapper.toDTO(automovilGuardado);
    }

    @Override
    @Transactional
    public VehiculoResponseDTO actualizarMoto(UUID id, MotoRequestDTO dto) {
        var vehiculo = vehiculoRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Vehículo con id {} no encontrado.", id);
                return new RuntimeException("Vehículo con id " + id + " no encontrado.");
            });

        if (!(vehiculo instanceof Moto)) {
            log.error("El vehículo con id {} no es una moto.", id);
            throw new RuntimeException("El vehículo con id " + id + " no es una moto.");
        }

        var moto = (Moto) vehiculo;

        // Validar si la placa cambió y ya existe
        if (!moto.getPlaca().equals(dto.getPlaca()) &&
            vehiculoRepository.existsByPlaca(dto.getPlaca())) {
            log.error("La placa {} ya existe.", dto.getPlaca());
            throw new RuntimeException("La placa " + dto.getPlaca() + " ya existe.");
        }

        // Validaciones específicas de moto
        if (dto.getCilindraje() == null || dto.getCilindraje() <= 0) {
            log.error("El cilindraje debe ser mayor a 0.");
            throw new RuntimeException("El cilindraje debe ser mayor a 0.");
        }

        if (dto.getNumeroRuedas() == null || dto.getNumeroRuedas() < 2 || dto.getNumeroRuedas() > 3) {
            log.error("El número de ruedas debe ser 2 o 3.");
            throw new RuntimeException("El número de ruedas debe ser 2 o 3.");
        }

        // Actualizar campos comunes
        moto.setPlaca(dto.getPlaca());
        moto.setMarca(dto.getMarca());
        moto.setModelo(dto.getModelo());
        moto.setColor(dto.getColor());
        moto.setAnioFabricacion(dto.getAnioFabricacion());

        // Actualizar campos específicos de moto
        moto.setTieneCasco(dto.getTieneCasco());
        moto.setCilindraje(dto.getCilindraje());
        moto.setTipo(TipoMoto.valueOf(dto.getTipo()));
        moto.setTipoCombustible(TipoCombustible.valueOf(dto.getTipoCombustible()));
        moto.setTieneABS(dto.getTieneABS());
        moto.setNumeroRuedas(dto.getNumeroRuedas());
        moto.setCapacidadTanque(dto.getCapacidadTanque());
        moto.setTipoFrenos(dto.getTipoFrenos());

        var motoActualizada = motoRepository.save(moto);
        log.info("Moto con id {} actualizada.", id);
        return vehiculoMapper.toDTO(motoActualizada);
    }

    @Override
    @Transactional
    public VehiculoResponseDTO actualizarAutomovil(UUID id, AutomovilRequestDTO dto) {
        var vehiculo = vehiculoRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Vehículo con id {} no encontrado.", id);
                return new RuntimeException("Vehículo con id " + id + " no encontrado.");
            });

        if (!(vehiculo instanceof Automovil)) {
            log.error("El vehículo con id {} no es un automóvil.", id);
            throw new RuntimeException("El vehículo con id " + id + " no es un automóvil.");
        }

        var automovil = (Automovil) vehiculo;

        // Validar si la placa cambió y ya existe
        if (!automovil.getPlaca().equals(dto.getPlaca()) &&
            vehiculoRepository.existsByPlaca(dto.getPlaca())) {
            log.error("La placa {} ya existe.", dto.getPlaca());
            throw new RuntimeException("La placa " + dto.getPlaca() + " ya existe.");
        }

        // Validaciones específicas de automóvil
        if (dto.getCilindraje() == null || dto.getCilindraje() <= 0) {
            log.error("El cilindraje debe ser mayor a 0.");
            throw new RuntimeException("El cilindraje debe ser mayor a 0.");
        }

        if (dto.getNumeroPuertas() == null || dto.getNumeroPuertas() < 2 || dto.getNumeroPuertas() > 5) {
            log.error("El número de puertas debe estar entre 2 y 5.");
            throw new RuntimeException("El número de puertas debe estar entre 2 y 5.");
        }

        if (dto.getNumeroAsientos() == null || dto.getNumeroAsientos() < 2 || dto.getNumeroAsientos() > 9) {
            log.error("El número de asientos debe estar entre 2 y 9.");
            throw new RuntimeException("El número de asientos debe estar entre 2 y 9.");
        }

        // Actualizar campos comunes
        automovil.setPlaca(dto.getPlaca());
        automovil.setMarca(dto.getMarca());
        automovil.setModelo(dto.getModelo());
        automovil.setColor(dto.getColor());
        automovil.setAnioFabricacion(dto.getAnioFabricacion());

        // Actualizar campos específicos de automóvil
        automovil.setTipoAutomovil(TipoAutomovil.valueOf(dto.getTipoAutomovil()));
        automovil.setTipoCombustible(TipoCombustible.valueOf(dto.getTipoCombustible()));
        automovil.setCilindraje(dto.getCilindraje());
        automovil.setSuspensionDeportiva(dto.getSuspensionDeportiva());
        automovil.setTraccion(TipoTraccion.valueOf(dto.getTraccion()));
        automovil.setTieneBalde(dto.getTieneBalde());
        automovil.setNumeroPuertas(dto.getNumeroPuertas());
        automovil.setNumeroAsientos(dto.getNumeroAsientos());
        automovil.setCapacidadTanque(dto.getCapacidadTanque());

        var automovilActualizado = automovilRepository.save(automovil);
        log.info("Automóvil con id {} actualizado.", id);
        return vehiculoMapper.toDTO(automovilActualizado);
    }

    @Override
    @Transactional
    public void eliminarAuto(UUID id) {
        var vehiculo = vehiculoRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Vehículo con id {} no encontrado.", id);
                return new RuntimeException("Vehículo con id " + id + " no encontrado.");
            });

        vehiculo.setActivo(false);
        vehiculoRepository.save(vehiculo);
        log.info("Vehículo con id {} eliminado (borrado lógico).", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponseDTO> listarTodosVehiculos() {
        return vehiculoRepository.findAll()
            .stream()
            .map(vehiculoMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponseDTO> listarVehiculosActivos() {
        return vehiculoRepository.findByActivoTrue()
            .stream()
            .map(vehiculoMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculoResponseDTO buscarPorId(UUID id) {
        var vehiculo = vehiculoRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Vehículo con id {} no encontrado.", id);
                return new RuntimeException("Vehículo con id " + id + " no encontrado.");
            });
        return vehiculoMapper.toDTO(vehiculo);
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculoResponseDTO buscarPorPlaca(String placa) {
        var vehiculo = vehiculoRepository.findByPlaca(placa)
            .orElseThrow(() -> {
                log.error("Vehículo con placa {} no encontrado.", placa);
                return new RuntimeException("Vehículo con placa " + placa + " no encontrado.");
            });
        return vehiculoMapper.toDTO(vehiculo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponseDTO> buscarPorPropietario(UUID idPropietario) {
        return vehiculoRepository.findByPropietarioId(idPropietario)
            .stream()
            .map(vehiculoMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponseDTO> buscarPorMarca(String marca) {
        return vehiculoRepository.findByMarcaContaining(marca)
            .stream()
            .map(vehiculoMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponseDTO> buscarPorModelo(String modelo) {
        return vehiculoRepository.findByModeloContaining(modelo)
            .stream()
            .map(vehiculoMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponseDTO> buscarPorColor(String color) {
        return vehiculoRepository.findByColor(color)
            .stream()
            .map(vehiculoMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponseDTO> buscarPorAnioFabricacion(Integer anio) {
        return vehiculoRepository.findByAnioFabricacion(anio)
            .stream()
            .map(vehiculoMapper::toDTO)
            .collect(Collectors.toList());
    }
}
