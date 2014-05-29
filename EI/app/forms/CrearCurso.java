package forms;

import java.util.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;

/**
 * Clase CrearCurso, el formulario para crear un curso
 */
public class CrearCurso {

    public String nivel;

    public String dias;

    public String horaInicio;

    public String horaFin;

    /**
     * Método que valida si la información introducida al formulario es correcta.
     * @return una lista de errores de validación
     */
    public List<ValidationError> validate() {
        List<ValidationError> errores = new ArrayList<ValidationError>();
        if (nivel.equals("")) {
            errores.add(new ValidationError("nivel", "Este campo es requerido."));
        }
        if (dias.equals("")) {
            errores.add(new ValidationError("dias", "Este campo es requerido."));
        }
        if (horaInicio.equals("")) {
            errores.add(new ValidationError("horaInicio", "Este campo es requerido."));
        }
        if (horaFin.equals("")) {
            errores.add(new ValidationError("horaFin", "Este campo es requerido."));
        }
        int horainit = 0;
        if (horaInicio.length() == 4) {
            horainit = Integer.parseInt(horaInicio.substring(0, 1));
        } else {
            horainit = Integer.parseInt(horaInicio.substring(0, 2));
        }
        int horafin = 0;
        if (horaFin.length() == 4) {
            horafin = Integer.parseInt(horaFin.substring(0, 1));
        } else {
            horafin = Integer.parseInt(horaFin.substring(0, 2));
        }
        if (horainit > horafin) {
            errores.add(new ValidationError("horaInicio", "La hora de inicio no es válida para la hora de termino seleccionada."));
        }
        if (horainit == horafin) {
            errores.add(new ValidationError("horaInicio", "La hora de inicio no puede ser la misma que la hora de termino."));
        }
        return errores.isEmpty() ? null : errores;
    } 
}