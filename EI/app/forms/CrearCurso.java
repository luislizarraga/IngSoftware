package forms;

import java.util.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;

public class CrearCurso {

    public String nivel;

    public String dias;

    public String horaInicio;

    public String horaFin;

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
        return errores.isEmpty() ? null : errores;
    } 
}