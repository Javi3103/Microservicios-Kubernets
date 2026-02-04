package ec.edu.espe.ms_clientes.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Entity
@Table(name = "persona_natural")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonaNatural extends Persona {

    @Column
    private String apellido;

    @Column(nullable = false)
    private LocalDateTime fechaNacimiento;

    @Column(length = 1)
    private TipoGenero genero;

    public boolean validarIdentificacion() {
        String cedula = (getIdentificacion() == null) ? "" : getIdentificacion().trim();

        // Debe tener exactamente 10 dígitos numéricos
        if (!cedula.matches("\\d{10}")) {
            return false;
        }

        // Código de provincia válido: 01-24
        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || provincia > 24) {
            return false;
        }

        // Tercer dígito para persona natural debe ser 0-5
        int tercerDigito = Character.getNumericValue(cedula.charAt(2));
        if (tercerDigito < 0 || tercerDigito > 5) {
            return false;
        }

        // Cálculo del dígito verificador según regla ecuatoriana
        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int dig = Character.getNumericValue(cedula.charAt(i));
            if (i % 2 == 0) { // posiciones impares (1,3,5,7,9) index 0,2,4...
                int prod = dig * 2;
                if (prod > 9) prod -= 9;
                suma += prod;
            } else { // posiciones pares
                suma += dig;
            }
        }

        int modulo = suma % 10;
        int verificadorCalculado = (modulo == 0) ? 0 : 10 - modulo;
        int verificadorReal = Character.getNumericValue(cedula.charAt(9));

        return verificadorCalculado == verificadorReal;
    }
}
