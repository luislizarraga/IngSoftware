package forms;

import java.util.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;

/**
 * Clase CrearCurso, el formulario para crear un curso
 */
public class ModificarCurso {

    public String calificacion;

    public String notas;

    /**
     * Método que valida si la información introducida al formulario es correcta.
     * @return una lista de errores de validación
     */
    public List<ValidationError> validate() {
        List<ValidationError> errores = new ArrayList<ValidationError>();
        try {
            Integer cali = Integer.parseInt(calificacion);
            if (cali > 10 || cali < 0) {
                errores.add(new ValidationError("calificacion", "Número no válido"));
            }
        } catch (NumberFormatException nfe) {
            //System.out.println("weeeeesdfgsdfgs");
            errores.add(new ValidationError("calificacion", "Número no válido"));
        }
        // if (nivel.equals("")) {
        //     errores.add(new ValidationError("nivel", "Este campo es requerido."));
        // }
        // if (dias.equals("")) {
        //     errores.add(new ValidationError("dias", "Este campo es requerido."));
        // }
        // if (horaInicio.equals("")) {
        //     errores.add(new ValidationError("horaInicio", "Este campo es requerido."));
        // }
        // if (horaFin.equals("")) {
        //     errores.add(new ValidationError("horaFin", "Este campo es requerido."));
        // }
        return errores.isEmpty() ? null : errores;
    }

    public ModificarCurso(Integer calificacion, String notas) {
        this.calificacion = "" + calificacion;
        this.notas = notas;
    }

    public ModificarCurso(){}

}