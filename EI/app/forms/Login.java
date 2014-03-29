package forms;

import java.util.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;

public class Login {

    public String email;

    public String password;

    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<ValidationError>();
        if (email.equals("")) {
            errors.add(new ValidationError("email", "Este campo es requerido."));
        } else {
            Alumno a = Alumno.find.where().eq("correoElectronico", email).findUnique();
            if (a == null) {
                errors.add(new ValidationError("email", "Usuario inexistente."));
            }
        }
        if (password.equals("")) {
            errors.add(new ValidationError("password", "Este campo es requerido."));
        } else {
            Alumno a = Alumno.find.where().eq("correoElectronico", email).findUnique();
            if (a != null && !password.equals(a.getContrasena())) {
                errors.add(new ValidationError("password", "Contraseña incorrecta."));
            }
        }
        return errors.isEmpty() ? null : errors;
    }

}