package ec.edu.espe.ms_clientes.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "persona_juridica")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonaJuridica extends Persona {

    @Column(nullable = false,  unique = true)
    private String razonSocial;

    @Column(nullable = false)
    private String representanteLegal;

    @Column
    private String actividadEconomica;

    public boolean validarIdentificacion(){
        String ruc = getIdentificacion();
        if (ruc == null) return false;
        ruc = ruc.trim();
        if (!ruc.matches("\\d{13}")) return false;

        int provincia = Integer.parseInt(ruc.substring(0, 2));
        if (provincia < 1 || provincia > 24) return false;

        int tercer = Character.getNumericValue(ruc.charAt(2));
        try {
            int[] d = new int[13];
            for (int i = 0; i < 13; i++) d[i] = Character.getNumericValue(ruc.charAt(i));

            if (tercer < 6) { // Persona natural (módulo 10)
                int[] coef = {2,1,2,1,2,1,2,1,2};
                int sum = 0;
                for (int i = 0; i < 9; i++) {
                    int v = coef[i] * d[i];
                    if (v >= 10) v -= 9;
                    sum += v;
                }
                int verificador = (sum % 10 == 0) ? 0 : 10 - (sum % 10);
                return verificador == d[9] && "001".equals(ruc.substring(10));
            } else if (tercer == 6) { // Entidad pública (módulo 11)
                int[] coef = {3,2,7,6,5,4,3,2};
                int sum = 0;
                for (int i = 0; i < 8; i++) sum += coef[i] * d[i];
                int verificador = 11 - (sum % 11);
                if (verificador == 11) verificador = 0;
                return verificador == d[8] && "0001".equals(ruc.substring(9));
            } else if (tercer == 9) { // Sociedad privada (módulo 11)
                int[] coef = {4,3,2,7,6,5,4,3,2};
                int sum = 0;
                for (int i = 0; i < 9; i++) sum += coef[i] * d[i];
                int verificador = 11 - (sum % 11);
                if (verificador == 11) verificador = 0;
                return verificador == d[9] && "001".equals(ruc.substring(10));
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }
}
