package forms;

import java.util.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;

/**
 * Clase ModificacionProfesor, el formulario para modificar la información del profesor.
 */
public class ModificacionProfesor {

    public String nombre;
    
    public String apellidoPaterno;

    public String apellidoMaterno;

    public String correoElectronico;

    public String contrasenaNueva;

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
            Profesor p = Profesor.find.where().eq("correoElectronico", correoElectronico).findUnique();
            if (p != null && p.getApellidoPaterno().equals(apellidoPaterno) 
                && p.getApellidoMaterno().equals(apellidoMaterno) && p.getNombre().equals(nombre)
                && (!contrasenaNueva.equals("") && !confContrasena.equals(""))
                && p.getContrasena().equals(contrasenaNueva)
                && contrasenaNueva.equals(confContrasena)) {
                errores.add(new ValidationError("correoElectronico", "Este correo ya se encuentra registrado."));
            }
            if (!correoElectronico.matches("[a-zA-Z0-9-._\\+]+@[a-zA-Z0-9-\\+]+(.[a-zA-Z0-9-\\+]+)+")) {
                errores.add(new ValidationError("correoElectronico", "Correo electrónico inválido."));
            }
        }
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
        }
        if (apellidoPaterno.equals("")) {
            errores.add(new ValidationError("apellidoPaterno", "Este campo es requerido."));
        }
        return errores.isEmpty() ? null : errores;
    }


    /**
     * Constructor de ModificacionProfesor que recibe 4 parámetros
     * @param  nombre            el nombre para el profesor
     * @param  apellidoPaterno   el apellido paterno para el profesor
     * @param  apellidoMaterno   el apellido materno para el profesor
     * @param  correoElectronico el correo electrónico para el profesor
     */
    public ModificacionProfesor (String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico) {
        this.nombre            = nombre;
        this.apellidoPaterno   = apellidoPaterno;
        this.apellidoMaterno   = apellidoMaterno;
        this.correoElectronico = correoElectronico;
    }


    /**
     * Constructor de ModificacionProfesor que no recibe parámetros
     */
    public ModificacionProfesor () {
    }


}
