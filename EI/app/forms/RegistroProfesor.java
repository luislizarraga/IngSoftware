package forms;

import java.util.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;
import java.io.File;

/**
 * Clase RegistroProfesor, el formulario para registrar a un profesor.
 */
public class RegistroProfesor {

	public String nombre;
    
    public String apellidoPaterno;

    public String apellidoMaterno;

    public String correoElectronico;

    public String contrasena;

    public String confContrasena;

    public File constancia;

    public File video;


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
            if (p != null) {
                errores.add(new ValidationError("correoElectronico", "Este correo ya se encuentra registrado."));
            }
            if (!correoElectronico.matches("[a-zA-Z0-9-._\\+]+@[a-zA-Z0-9-\\+]+(.[a-zA-Z0-9-\\+]+)+")) {
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
        }
        if (apellidoPaterno.equals("")) {
            errores.add(new ValidationError("apellidoPaterno", "Este campo es requerido."));
        }
        return errores.isEmpty() ? null : errores;
    }



}