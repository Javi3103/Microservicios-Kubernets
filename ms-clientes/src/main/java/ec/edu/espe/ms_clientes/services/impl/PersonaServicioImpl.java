package ec.edu.espe.ms_clientes.services.impl;

import ec.edu.espe.ms_clientes.dto.mappers.PersonaMapper;
import ec.edu.espe.ms_clientes.dto.requests.PersonaJuridicaRequestDTO;
import ec.edu.espe.ms_clientes.dto.requests.PersonaNaturalRequestDTO;
import ec.edu.espe.ms_clientes.dto.responses.PersonaResponseDTO;
import ec.edu.espe.ms_clientes.models.PersonaJuridica;
import ec.edu.espe.ms_clientes.models.PersonaNatural;
import ec.edu.espe.ms_clientes.models.TipoGenero;
import ec.edu.espe.ms_clientes.repositories.PersonaRepository;
import ec.edu.espe.ms_clientes.services.PersonaService;
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
public class PersonaServicioImpl implements PersonaService {

    private final PersonaRepository personaRepositorio;
    private final PersonaMapper personaMapper;

    @Override
    @Transactional
    public PersonaResponseDTO crearPersonaNatural(PersonaNaturalRequestDTO dto) {

        if (personaRepositorio.existsByIdentificacion(dto.getIdentificacion())) {
            log.error("La persona con identificacion {} ya existe.", dto.getIdentificacion());
            throw new RuntimeException("La persona con identificacion " + dto.getIdentificacion() + " ya existe.");
        }

        if (personaRepositorio.existsByEmail(dto.getEmail())) {
            log.error("La persona con email {} ya existe.", dto.getEmail());
            throw new RuntimeException("La persona con email " + dto.getEmail() + " ya existe.");
        }

        if (personaRepositorio.existsByTelefono(dto.getTelefono())) {
            log.error("La persona con telefono {} ya existe.", dto.getTelefono());
            throw new RuntimeException("La persona con telefono " + dto.getTelefono() + " ya existe.");
        }

        var personaNatural = personaMapper.toEntity(dto);
        
        if (!personaNatural.validarIdentificacion()) {
            log.error("La identificación {} no es válida.", dto.getIdentificacion());
            throw new RuntimeException("La identificación " + dto.getIdentificacion() + " no es válida.");
        }
        
        var personaGuardada = personaRepositorio.save(personaNatural);
        log.info("Persona natural creada con id: {}", personaGuardada.getId());
        return personaMapper.toDTO((PersonaNatural) personaGuardada);

    }

    @Override
    @Transactional
    public PersonaResponseDTO crearPersonaJuridica(PersonaJuridicaRequestDTO dto) {
        if (personaRepositorio.existsByIdentificacion(dto.getIdentificacion())) {
            log.error("La persona juridica con identificacion {} ya existe.", dto.getIdentificacion());
            throw new RuntimeException("La persona con identificacion " + dto.getIdentificacion() + " ya existe.");
        }

        if (personaRepositorio.existsByEmail(dto.getEmail())) {
            log.error("La persona juridica con email {} ya existe.", dto.getEmail());
            throw new RuntimeException("La persona con email " + dto.getEmail() + " ya existe.");
        }

        if (personaRepositorio.existsByTelefono(dto.getTelefono())) {
            log.error("La persona juridica con telefono {} ya existe.", dto.getTelefono());
            throw new RuntimeException("La persona con telefono " + dto.getTelefono() + " ya existe.");
        }

        var personaJuridica = personaMapper.toEntity(dto);
        
        if (!personaJuridica.validarIdentificacion()) {
            log.error("La identificación {} no es válida.", dto.getIdentificacion());
            throw new RuntimeException("La identificación " + dto.getIdentificacion() + " no es válida.");
        }
        
        var personaGuardada = personaRepositorio.save(personaJuridica);
        log.info("Persona jurídica creada con id: {}", personaGuardada.getId());
        return personaMapper.toDTO((PersonaJuridica) personaGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponseDTO> findAllPersona() {
        return personaRepositorio.findAll()
            .stream()
            .map(personaMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaResponseDTO buscarPorIdentificacion(String identificacion) {
        var persona = personaRepositorio.findByIdentificacion(identificacion)
            .orElseThrow(() -> {
                log.error("Persona con identificacion {} no encontrada.", identificacion);
                return new RuntimeException("Persona con identificacion " + identificacion + " no encontrada.");
            });
        return personaMapper.toDTO(persona);
    }

    @Override
    @Transactional
    public void eliminarPersona(UUID id) {
        var persona = personaRepositorio.findById(id)
            .orElseThrow(() -> {
                log.error("Persona con id {} no encontrada.", id);
                return new RuntimeException("Persona con id " + id + " no encontrada.");
            });
        
        persona.setActivo(false);
        personaRepositorio.save(persona);
        log.info("Persona con id {} eliminada (borrado lógico).", id);
    }

    @Override
    @Transactional
    public PersonaResponseDTO actualizarpersonanatural(UUID id, PersonaNaturalRequestDTO dto) {
        var persona = personaRepositorio.findById(id)
            .orElseThrow(() -> {
                log.error("Persona con id {} no encontrada.", id);
                return new RuntimeException("Persona con id " + id + " no encontrada.");
            });
        
        if (!(persona instanceof PersonaNatural)) {
            log.error("La persona con id {} no es una persona natural.", id);
            throw new RuntimeException("La persona con id " + id + " no es una persona natural.");
        }
        
        var personaNatural = (PersonaNatural) persona;
        
        if (!personaNatural.getIdentificacion().equals(dto.getIdentificacion()) &&
            personaRepositorio.existsByIdentificacion(dto.getIdentificacion())) {
            log.error("La identificación {} ya existe.", dto.getIdentificacion());
            throw new RuntimeException("La identificación " + dto.getIdentificacion() + " ya existe.");
        }
        
        if (!personaNatural.getEmail().equals(dto.getEmail()) &&
            personaRepositorio.existsByEmail(dto.getEmail())) {
            log.error("El email {} ya existe.", dto.getEmail());
            throw new RuntimeException("El email " + dto.getEmail() + " ya existe.");
        }
        
        if (!personaNatural.getTelefono().equals(dto.getTelefono()) &&
            personaRepositorio.existsByTelefono(dto.getTelefono())) {
            log.error("El teléfono {} ya existe.", dto.getTelefono());
            throw new RuntimeException("El teléfono " + dto.getTelefono() + " ya existe.");
        }
        
        // Actualizar campos
        personaNatural.setIdentificacion(dto.getIdentificacion());
        personaNatural.setNombre(dto.getNombre());
        personaNatural.setEmail(dto.getEmail());
        personaNatural.setTelefono(dto.getTelefono());
        personaNatural.setDireccion(dto.getDireccion());
        personaNatural.setApellido(dto.getApellido());
        personaNatural.setFechaNacimiento(dto.getFechaNacimiento());
        personaNatural.setGenero(TipoGenero.valueOf(dto.getGenero()));
        
        if (!personaNatural.validarIdentificacion()) {
            log.error("La identificación {} no es válida.", dto.getIdentificacion());
            throw new RuntimeException("La identificación " + dto.getIdentificacion() + " no es válida.");
        }
        
        var personaActualizada = personaRepositorio.save(personaNatural);
        log.info("Persona natural con id {} actualizada.", id);
        return personaMapper.toDTO((PersonaNatural) personaActualizada);
    }

    @Override
    @Transactional
    public PersonaResponseDTO actualizarpersonajuridica(UUID id, PersonaJuridicaRequestDTO dto) {
        var persona = personaRepositorio.findById(id)
            .orElseThrow(() -> {
                log.error("Persona con id {} no encontrada.", id);
                return new RuntimeException("Persona con id " + id + " no encontrada.");
            });
        
        if (!(persona instanceof PersonaJuridica)) {
            log.error("La persona con id {} no es una persona jurídica.", id);
            throw new RuntimeException("La persona con id " + id + " no es una persona jurídica.");
        }
        
        var personaJuridica = (PersonaJuridica) persona;
        
        if (!personaJuridica.getIdentificacion().equals(dto.getIdentificacion()) &&
            personaRepositorio.existsByIdentificacion(dto.getIdentificacion())) {
            log.error("La identificación {} ya existe.", dto.getIdentificacion());
            throw new RuntimeException("La identificación " + dto.getIdentificacion() + " ya existe.");
        }
        
        if (!personaJuridica.getEmail().equals(dto.getEmail()) &&
            personaRepositorio.existsByEmail(dto.getEmail())) {
            log.error("El email {} ya existe.", dto.getEmail());
            throw new RuntimeException("El email " + dto.getEmail() + " ya existe.");
        }

        if (!personaJuridica.getTelefono().equals(dto.getTelefono()) &&
            personaRepositorio.existsByTelefono(dto.getTelefono())) {
            log.error("El teléfono {} ya existe.", dto.getTelefono());
            throw new RuntimeException("El teléfono " + dto.getTelefono() + " ya existe.");
        }
        
        personaJuridica.setIdentificacion(dto.getIdentificacion());
        personaJuridica.setNombre(dto.getNombre());
        personaJuridica.setEmail(dto.getEmail());
        personaJuridica.setTelefono(dto.getTelefono());
        personaJuridica.setDireccion(dto.getDireccion());
        personaJuridica.setRazonSocial(dto.getRazonSocial());
        personaJuridica.setRepresentanteLegal(dto.getRepresentanteLegal());
        personaJuridica.setActividadEconomica(dto.getActividadEconomica());
        
        if (!personaJuridica.validarIdentificacion()) {
            log.error("La identificación {} no es válida.", dto.getIdentificacion());
            throw new RuntimeException("La identificación " + dto.getIdentificacion() + " no es válida.");
        }
        
        var personaActualizada = personaRepositorio.save(personaJuridica);
        log.info("Persona jurídica con id {} actualizada.", id);
        return personaMapper.toDTO((PersonaJuridica) personaActualizada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponseDTO> listarPersonasNaturales() {
        return personaRepositorio.findPersonasNaturalesActivas()
            .stream()
            .map(persona -> personaMapper.toDTO(persona))
            .collect(Collectors.toList());
    }
}
