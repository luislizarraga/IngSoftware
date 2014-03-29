package forms;

import java.util.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;


public class RegistrationAlumno {

    public String nombre;
    
    public String apellidoPaterno;

    public String apellidoMaterno;

    public String correoElectronico;

    public String contrasena;

    public String confContrasena;


    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<ValidationError>();
        if (correoElectronico.equals("")) {
            errors.add(new ValidationError("correoElectronico", "Este campo es requerido."));
        } else {
            Alumno a = Alumno.find.where().eq("correoElectronico", correoElectronico).findUnique();
            if (a != null) {
                errors.add(new ValidationError("correoElectronico", "Este correo ya se encuentra registrado."));
            }
            System.out.println(correoElectronico.matches("[a-zA-Z0-9-\\+]+@[a-zA-Z0-9-\\+]+(.[a-zA-Z0-9-\\+]+)+"));
            if (!correoElectronico.matches("[a-zA-Z0-9-\\+]+@[a-zA-Z0-9-\\+]+(.[a-zA-Z0-9-\\+]+)+")) {
                errors.add(new ValidationError("correoElectronico", "Correo electrónico inválido."));
            }
        }
        if (contrasena.equals("") || confContrasena.equals("")) {
            errors.add(new ValidationError("contrasena", "Este campo es requerido."));
            errors.add(new ValidationError("confContrasena", "Este campo es requerido."));
        } else if (!contrasena.equals(confContrasena)) {
            errors.add(new ValidationError("contrasena", "Las contraseñas no coinciden."));
        }
        if (nombre.equals("")) {
            errors.add(new ValidationError("nombre", "Este campo es requerido."));
        }
        if (apellidoPaterno.equals("")) {
            errors.add(new ValidationError("apellidoPaterno", "Este campo es requerido."));
        }
        return errors.isEmpty() ? null : errors;
    }
}