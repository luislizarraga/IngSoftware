package forms;

import java.util.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;

public class InicioSesionProfesor {

    public String correoElectronico;

    public String contrasena;

    public List<ValidationError> validate() {
        List<ValidationError> errores = new ArrayList<ValidationError>();
        System.out.println(correoElectronico);
        if (correoElectronico.equals("")) {
            errores.add(new ValidationError("correoElectronico", "Este campo es requerido."));
        } else {
            Profesor p = Profesor.find.where().eq("correoElectronico", correoElectronico).findUnique();
            if (p == null) {
                System.out.println("Aqui");
                errores.add(new ValidationError("correoElectronico", "Usuario inexistente."));
            }
        }
        if (contrasena.equals("")) {
            errores.add(new ValidationError("contrasena", "Este campo es requerido."));
        } else {
            Profesor p = Profesor.find.where().eq("correoElectronico", correoElectronico).findUnique();
            if (p != null && !contrasena.equals(p.getContrasena())) {
                errores.add(new ValidationError("contrasena", "Contraseña incorrecta."));
            }
        }
        return errores.isEmpty() ? null : errores;
    }

}