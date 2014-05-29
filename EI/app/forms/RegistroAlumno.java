package forms;

import java.util.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;

/**
 * Clase RegistroAlumno, el formulario para registrar a un alumno.
 */
public class RegistroAlumno {

    public String nombre;
    
    public String apellidoPaterno;

    public String apellidoMaterno;

    public String correoElectronico;

    public String contrasena;

    public String confContrasena;


    /**
     * Método que valida si la información introducida al formulario es correcta.
     * @return una lista de errores de validación
     */
    public List<ValidationError> validate() {
        List<ValidationError> errores = new ArrayList<ValidationError>();
        if (correoElectronico.equals("")) {
            errores.add(new ValidationError("correoElectronico", "Este campo es requerido."));
        } else {
            Alumno a = Alumno.find.where().eq("correoElectronico", correoElectronico).findUnique();
            if (a != null) {
                errores.add(new ValidationError("correoElectronico", "Este correo ya se encuentra registrado."));
            }
            if (!correoElectronico.matches("[a-zA-Z0-9-._\\+]+@[a-zA-Z0-9-\\+]+(\\.[a-zA-Z0-9-\\+]+)+")) {
                errores.add(new ValidationError("correoElectronico", "Correo electrónico inválido."));
            }
        }
        if (contrasena.equals("") || confContrasena.equals("")) {
            errores.add(new ValidationError("contrasena", "Este campo es requerido."));
            errores.add(new ValidationError("confContrasena", "Este campo es requerido."));
        } else if (!contrasena.equals(confContrasena)) {
            errores.add(new ValidationError("contrasena", "Las contraseñas no coinciden."));
        }
        if (nombre.equals("")) {
            errores.add(new ValidationError("nombre", "Este campo es requerido."));
        } else if (!nombre.matches("[a-zA-Z \\+]+")) {
            errores.add(new ValidationError("nombre", "Nombre inválido."));
        }
        if (apellidoPaterno.equals("")) {
            errores.add(new ValidationError("apellidoPaterno", "Este campo es requerido."));
        } else if (!apellidoPaterno.matches("[a-zA-Z \\+]+")) {
            errores.add(new ValidationError("apellidoPaterno", "Apellido paterno inválido."));
        }
        return errores.isEmpty() ? null : errores;
    }


    /**
     * Constructor de RegistroAlumno que recibe 6 parámetros.
     * @param  nombre            el nombre del alumno por registrar
     * @param  apellidoPaterno   el apellido paterno del alumno por registrar
     * @param  apellidoMaterno   el apellido materno del alumno por registrar
     * @param  correoElectronico el correo electrónico del alumno por registrar
     * @param  contrasena        la contraseña del alumno por registrar
     * @param  confContrasena    la confirmación de la contraseña del alumno por registrar
     */
    public RegistroAlumno (String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico, String contrasena, String confContrasena) {
        this.nombre            = nombre;
        this.apellidoPaterno   = apellidoPaterno;
        this.apellidoMaterno   = apellidoMaterno;
        this.correoElectronico = correoElectronico;
        this.contrasena        = contrasena;
        this.confContrasena    = confContrasena;
    }


    /**
     * Constructor de RegistroAlumno que no recibe parámetros.
     */
    public RegistroAlumno () {
    }
}