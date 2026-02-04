package ec.edu.espe.ms_clientes.services;

import ec.edu.espe.ms_clientes.dto.requests.AutomovilRequestDTO;
import ec.edu.espe.ms_clientes.dto.requests.MotoRequestDTO;
import ec.edu.espe.ms_clientes.dto.responses.VehiculoResponseDTO;

import java.util.List;
import java.util.UUID;

public interface VehiculoService {
    VehiculoResponseDTO crearMoto(MotoRequestDTO dto);
    VehiculoResponseDTO crearAutomovil(AutomovilRequestDTO dto);

    VehiculoResponseDTO actualizarMoto(UUID id, MotoRequestDTO dto);
    VehiculoResponseDTO actualizarAutomovil(UUID id, AutomovilRequestDTO dto);

    void eliminarAuto(UUID id);

    // Métodos de búsqueda
    List<VehiculoResponseDTO> listarTodosVehiculos();
    List<VehiculoResponseDTO> listarVehiculosActivos();
    VehiculoResponseDTO buscarPorId(UUID id);
    VehiculoResponseDTO buscarPorPlaca(String placa);
    List<VehiculoResponseDTO> buscarPorPropietario(UUID idPropietario);
    List<VehiculoResponseDTO> buscarPorMarca(String marca);
    List<VehiculoResponseDTO> buscarPorModelo(String modelo);
    List<VehiculoResponseDTO> buscarPorColor(String color);
    List<VehiculoResponseDTO> buscarPorAnioFabricacion(Integer anio);
}
