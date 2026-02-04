package ec.edu.espe.ms_clientes.controllers;

import ec.edu.espe.ms_clientes.dto.responses.VehiculoResponseDTO;
import ec.edu.espe.ms_clientes.services.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import ec.edu.espe.ms_clientes.dto.requests.AutomovilRequestDTO;
import ec.edu.espe.ms_clientes.dto.requests.MotoRequestDTO;
import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping("/placa/{placa}")
    public ResponseEntity<VehiculoResponseDTO> getByPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(vehiculoService.buscarPorPlaca(placa));
    }

    @PostMapping("/automovil")
    public ResponseEntity<VehiculoResponseDTO> crearAutomovil(@Valid @RequestBody AutomovilRequestDTO dto) {
        var creado = vehiculoService.crearAutomovil(dto);
        return ResponseEntity.status(201).body(creado);
    }

    @PostMapping("/moto")
    public ResponseEntity<VehiculoResponseDTO> crearMoto(@Valid @RequestBody MotoRequestDTO dto) {
        var creado = vehiculoService.crearMoto(dto);
        return ResponseEntity.status(201).body(creado);
    }

	@GetMapping
	public ResponseEntity<List<VehiculoResponseDTO>> listarTodos() {
		return ResponseEntity.ok(vehiculoService.listarTodosVehiculos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<VehiculoResponseDTO> obtenerPorId(@PathVariable String id) {
		return ResponseEntity.ok(vehiculoService.buscarPorId(UUID.fromString(id)));
	}

	@GetMapping("/propietario/{idPropietario}")
	public ResponseEntity<List<VehiculoResponseDTO>> buscarPorPropietario(@PathVariable String idPropietario) {
		return ResponseEntity.ok(vehiculoService.buscarPorPropietario(UUID.fromString(idPropietario)));
	}

	@GetMapping("/marca/{marca}")
	public ResponseEntity<List<VehiculoResponseDTO>> buscarPorMarca(@PathVariable String marca) {
		return ResponseEntity.ok(vehiculoService.buscarPorMarca(marca));
	}

	@GetMapping("/activos")
	public ResponseEntity<List<VehiculoResponseDTO>> listarActivos() {
		return ResponseEntity.ok(vehiculoService.listarVehiculosActivos());
	}

	@PutMapping("/moto/{id}")
	public ResponseEntity<VehiculoResponseDTO> actualizarMoto(
			@PathVariable String id,
			@Valid @RequestBody MotoRequestDTO dto) {
		return ResponseEntity.ok(vehiculoService.actualizarMoto(UUID.fromString(id), dto));
	}

	@PutMapping("/automovil/{id}")
	public ResponseEntity<VehiculoResponseDTO> actualizarAutomovil(
			@PathVariable String id,
			@Valid @RequestBody AutomovilRequestDTO dto) {
		return ResponseEntity.ok(vehiculoService.actualizarAutomovil(UUID.fromString(id), dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarVehiculo(@PathVariable String id) {
		vehiculoService.eliminarAuto(UUID.fromString(id));
		return ResponseEntity.noContent().build();
	}
}
