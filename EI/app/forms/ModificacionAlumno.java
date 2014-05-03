package forms;

import java.util.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;

/**
 * Clase ModificacionAlumno, el formulario para modificar la información del alumno.
 */
public class ModificacionAlumno {

    public String nombre;
    
    public String apellidoPaterno;

    public String apellidoMaterno;

    //public String correoElectronico;

    public String contrasenaNueva;

    public String confContrasena;


    /**
     * Método que valida si la información introducida al formulario es correcta.
     * @return una lista de errores de validación
     */
    public List<ValidationError> validate() {
        List<ValidationError> errores = new ArrayList<ValidationError>();
        // if (correoElectronico.equals("")) {
        //     errores.add(new ValidationError("correoElectronico", "Este campo es requerido."));
        // } else {
        //     //Alumno a = Alumno.find.where().eq("correoElectronico", correoElectronico).findUnique();
        //     // if (a != null && a.getApellidoPaterno().equals(apellidoPaterno) 
        //     //     && a.getApellidoMaterno().equals(apellidoMaterno) && a.getNombre().equals(nombre)
        //     //     && (!contrasenaNueva.equals("") && !confContrasena.equals(""))
        //     //     && a.getContrasena().equals(contrasenaNueva)
        //     //     && contrasenaNueva.equals(confContrasena)) {
        //     //     errores.add(new ValidationError("correoElectronico", "Este correo ya se encuentra registrado."));
        //     // }
        //     // if (!correoElectronico.matches("[a-zA-Z0-9-._\\+]+@[a-zA-Z0-9-\\+]+(.[a-zA-Z0-9-\\+]+)+")) {
        //     //     errores.add(new ValidationError("correoElectronico", "Correo electrónico inválido."));
        //     // }
        // }
        if (!contrasenaNueva.equals("") && !confContrasena.equals("")) {
            if (!contrasenaNueva.equals(confContrasena)) {
                errores.add(new ValidationError("contrasenaNueva", "Las contraseñas no coinciden."));
            }
        }
        if (!contrasenaNueva.equals("") && confContrasena.equals("")) {
                errores.add(new ValidationError("contrasenaNueva", "Las contraseñas no coinciden.")); 
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
     * Constructor de ModificacionAlumno que recibe 4 parámetros
     * @param  nombre            el nombre para el alumno
     * @param  apellidoPaterno   el apellido paterno para el alumno
     * @param  apellidoMaterno   el apellido materno para el alumno
     * @param  correoElectronico el correo electrónico para el alumno
     */
    public ModificacionAlumno (String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.nombre            = nombre;
        this.apellidoPaterno   = apellidoPaterno;
        this.apellidoMaterno   = apellidoMaterno;
        //this.correoElectronico = correoElectronico;
    }


    /**
     * Constructor de ModificacionAlumno que no recibe parámetros
     */
    public ModificacionAlumno () {}


}