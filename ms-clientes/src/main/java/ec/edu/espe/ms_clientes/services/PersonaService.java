package ec.edu.espe.ms_clientes.services;


import ec.edu.espe.ms_clientes.dto.requests.PersonaJuridicaRequestDTO;
import ec.edu.espe.ms_clientes.dto.requests.PersonaNaturalRequestDTO;
import ec.edu.espe.ms_clientes.dto.responses.PersonaResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PersonaService {

    PersonaResponseDTO crearPersonaNatural(PersonaNaturalRequestDTO dto);
    PersonaResponseDTO crearPersonaJuridica(PersonaJuridicaRequestDTO dto);

    List<PersonaResponseDTO> findAllPersona();

    PersonaResponseDTO buscarPorIdentificacion(String identificacion);

    void eliminarPersona(UUID id);

    PersonaResponseDTO actualizarpersonanatural(UUID id, PersonaNaturalRequestDTO dto);
    PersonaResponseDTO actualizarpersonajuridica(UUID id, PersonaJuridicaRequestDTO dto);

    List<PersonaResponseDTO> listarPersonasNaturales();

}