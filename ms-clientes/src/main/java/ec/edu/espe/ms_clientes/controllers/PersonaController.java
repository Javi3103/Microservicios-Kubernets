package ec.edu.espe.ms_clientes.controllers;

import ec.edu.espe.ms_clientes.dto.requests.PersonaJuridicaRequestDTO;
import ec.edu.espe.ms_clientes.dto.requests.PersonaNaturalRequestDTO;
import ec.edu.espe.ms_clientes.dto.responses.PersonaResponseDTO;
import ec.edu.espe.ms_clientes.services.PersonaService;
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
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {
	@Autowired
	private PersonaService personaService;

	@GetMapping
	public ResponseEntity<List<PersonaResponseDTO>> listarTodas() {
		return ResponseEntity.ok(personaService.findAllPersona());
	}

	@GetMapping("/identificacion/{identificacion}")
	public ResponseEntity<PersonaResponseDTO> getByIdentificacion(@PathVariable String identificacion) {
		return ResponseEntity.ok(personaService.buscarPorIdentificacion(identificacion));
	}

	@PostMapping("/natural")
	public ResponseEntity<PersonaResponseDTO> crearPersonaNatural(@Valid @RequestBody PersonaNaturalRequestDTO dto) {
		var creada = personaService.crearPersonaNatural(dto);
		return ResponseEntity.status(201).body(creada);
	}

	@PostMapping("/juridica")
	public ResponseEntity<PersonaResponseDTO> crearPersonaJuridica(@Valid @RequestBody PersonaJuridicaRequestDTO dto) {
		var creada = personaService.crearPersonaJuridica(dto);
		return ResponseEntity.status(201).body(creada);
	}

	@PutMapping("/natural/{id}")
	public ResponseEntity<PersonaResponseDTO> actualizarPersonaNatural(
			@PathVariable String id,
			@Valid @RequestBody PersonaNaturalRequestDTO dto) {
		return ResponseEntity.ok(personaService.actualizarpersonanatural(java.util.UUID.fromString(id), dto));
	}

	@PutMapping("/juridica/{id}")
	public ResponseEntity<PersonaResponseDTO> actualizarPersonaJuridica(
			@PathVariable String id,
			@Valid @RequestBody PersonaJuridicaRequestDTO dto) {
		return ResponseEntity.ok(personaService.actualizarpersonajuridica(java.util.UUID.fromString(id), dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarPersona(@PathVariable String id) {
		personaService.eliminarPersona(UUID.fromString(id));
		return ResponseEntity.noContent().build();
	}
}
