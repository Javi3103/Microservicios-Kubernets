package ec.edu.espe.ms_clientes.dto.mappers;

import ec.edu.espe.ms_clientes.dto.requests.PersonaNaturalRequestDTO;
import ec.edu.espe.ms_clientes.dto.requests.PersonaJuridicaRequestDTO;
import ec.edu.espe.ms_clientes.dto.responses.PersonaResponseDTO;
import ec.edu.espe.ms_clientes.dto.responses.PersonaNaturalResponseDTO;
import ec.edu.espe.ms_clientes.dto.responses.PersonaJuridicaResponseDTO;
import ec.edu.espe.ms_clientes.models.Persona;
import ec.edu.espe.ms_clientes.models.PersonaJuridica;
import ec.edu.espe.ms_clientes.models.PersonaNatural;
import ec.edu.espe.ms_clientes.models.TipoGenero;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper {
    /*
        CONVERTIR PERSONA NATURAL REQUEST A ENTIDAD PERSONA NATURAL
     */
    public PersonaNatural toEntity(PersonaNaturalRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return PersonaNatural.builder()
                .identificacion(dto.getIdentificacion())
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .apellido(dto.getApellido())
                .fechaNacimiento(dto.getFechaNacimiento())
                .genero(TipoGenero.valueOf(dto.getGenero()))
                .build();
    }

    /*
        CONVERTIR PERSONA JURIDICA REQUEST A ENTIDAD PERSONA JURIDICA
     */
    public PersonaJuridica toEntity(PersonaJuridicaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return PersonaJuridica.builder()
                .identificacion(dto.getIdentificacion())
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .razonSocial(dto.getRazonSocial())
                .representanteLegal(dto.getRepresentanteLegal())
                .actividadEconomica(dto.getActividadEconomica())
                .build();
    }

    public PersonaResponseDTO toDTO(Persona persona) {
        if (persona == null) {
            return null;
        }
        return PersonaResponseDTO.builder()
                .id(persona.getId())
                .identificacion(persona.getIdentificacion())
                .nombre(persona.getNombre())
                .email(persona.getEmail())
                .telefono(persona.getTelefono())
                .direccion(persona.getDireccion())
                .tipo(determinarTipo(persona))
                .activo(persona.getActivo())
                .fechaCreacion(persona.getFechaCreacion())
                .build();
    }

    /*
        CONVERTIR PERSONA NATURAL A RESPONSE DTO ESPECÍFICO
     */
    public PersonaNaturalResponseDTO toDTO(PersonaNatural personaNatural) {
        if (personaNatural == null) {
            return null;
        }
       
        return PersonaNaturalResponseDTO.builder()
                .id(personaNatural.getId())
                .identificacion(personaNatural.getIdentificacion())
                .nombre(personaNatural.getNombre())
                .email(personaNatural.getEmail())
                .telefono(personaNatural.getTelefono())
                .direccion(personaNatural.getDireccion())
                .tipo("NATURAL")
                .activo(personaNatural.getActivo())
                .fechaCreacion(personaNatural.getFechaCreacion())
                .apellido(personaNatural.getApellido())
                .fechaNacimiento(personaNatural.getFechaNacimiento())
                .genero(personaNatural.getGenero().name())
                .build();
    }

    /*
        CONVERTIR PERSONA JURIDICA A RESPONSE DTO ESPECÍFICO
     */
    public PersonaJuridicaResponseDTO toDTO(PersonaJuridica personaJuridica) {
        if (personaJuridica == null) {
            return null;
        }
        
        return PersonaJuridicaResponseDTO.builder()
                .id(personaJuridica.getId())
                .identificacion(personaJuridica.getIdentificacion())
                .nombre(personaJuridica.getNombre())
                .email(personaJuridica.getEmail())
                .telefono(personaJuridica.getTelefono())
                .direccion(personaJuridica.getDireccion())
                .tipo("JURIDICA")
                .activo(personaJuridica.getActivo())
                .fechaCreacion(personaJuridica.getFechaCreacion())
                .razonSocial(personaJuridica.getRazonSocial())
                .representanteLegal(personaJuridica.getRepresentanteLegal())
                .actividadEconomica(personaJuridica.getActividadEconomica())
                .build();
    }

    private String determinarTipo(Persona persona) {
        if (persona instanceof PersonaNatural) {
            return "NATURAL";
        } else if (persona instanceof PersonaJuridica) {
            return "JURIDICA";
        } else {
            return "DESCONOCIDO";
        }   
    }
}
