package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import models.*;
import play.data.*;
import java.util.*;
import forms.*;

public class CAlumno extends Controller {


    /**
     * [index description]
     * author: Luis Lizarraga
     * @return [description]
     */
    @Security.Authenticated(SecuredAlumno.class) // al poner esto encima de cualquier método o clase, se checara 
                                                //si hay un usuario iniciado y es alumno, si no cumple, 
                                                //lo manda a la pagina principal, dependiendo si en maestro o no
    public static Result index() {
        Alumno a = Alumno.find.where().eq("correoElectronico", session().get("email")).findUnique(); // aquí hago un query a la BD buscando a 
                                                                                                    // aquellos alumnos que tengan el email dado
                                                                                                    // eso de findUnique() se pone para que te regrese un
                                                                                                    // solo objeto y no una lista de objetos
        String user = a.nombre + " " + a.apellidoPaterno;
        return ok(views.html.alumno.alumnoIniciado.render("Página Principal", a, user));
    }


    /**
     * [iniciarSesionA description]
     * author: Jose Vargas
     * @return [description]
     */
    public static Result iniciarSesionA() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            Form<RegistrationAlumno> alumnoForma = Form.form(RegistrationAlumno.class);
            Form<Profesor> profesorForma = Form.form(Profesor.class);
            return badRequest(views.html.principalIH.render(alumnoForma, profesorForma, loginForm));
        } else {
            session().clear();                              // Se borra toda la información de la sesión
            session("email", loginForm.get().email);        // Se agrega el email a la sesion
            session("user", "alumno");                      // Se agrega el tipo de usuario a la sesion
            Alumno a = Alumno.find.where().eq("correoElectronico", loginForm.get().email).findUnique();
            if (a.getContrasena().equals(loginForm.get().password)) {
                return redirect(routes.CAlumno.index());
            } else {
                return redirect(base.routes.CBase.index());
            }
        }
    } 


    public static Result cerrarSesionA() {
        session().clear(); // Se borra toda la información de la sesión
        //flash("success", "You've been logged out");
        return redirect(base.routes.CBase.index());
    }

    // /**
    //  * [modificarInformacionA description]
    //  * author: Lorena Mireles
    //  * @return [description]
    //  */
    // public static Result modificarInformacionA() {
    //     Alumno a = Alumno.find("contrasena", request().username());
    //     return ok(views.html.everything.bullshit.render("Hola", a));
    // }


    // /**
    //  * [eliminaRegistroA description]
    //  * author: Lorena Mireles
    //  * @return [description]
    //  */
    // public static Result eliminaRegistroA() {
    //     Alumno a = Alumno.find("contrasena", request().username());
    //     return ok(views.html.everything.bullshit.render("Hola", a));
    // }


    // /**
    //  * [registrarA description]
    //  * author: Norma Trinidad
    //  * @return [description]
    //  */
    // public static Result registrarA() {
    //     Alumno a = Alumno.find("contrasena", request().username());
    //     return ok(views.html.everything.bullshit.render("Hola", a));
    // }


    // /**
    //  * [validaFormato description]
    //  * Posiblemente se necesite uno para inicio de sesion y otro diferente para registro y modificar info
    //  * author:varios
    //  * @return [description]
    //  */
    // public static Result validaFormato() {
    //     Alumno a = Alumno.find("contrasena", request().username());
    //     return ok(views.html.everything.bullshit.render("Hola", a));
    // }


    // /**
    //  * [modificacionExitosa description]
    //  * author: Lorena Mireles
    //  * @return [description]
    //  */
    // public static Result modificacionExitosa() {
    //     Alumno a = Alumno.find("contrasena", request().username());
    //     return ok(views.html.everything.bullshit.render("Hola", a));
    // }


    // /**
    //  * [eliminacionExitosa description]
    //  * author: Lorena Mireles
    //  * @return [description]
    //  */
    // public static Result eliminacionExitosa() {
    //     Alumno a = Alumno.find("contrasena", request().username());
    //     return ok(views.html.everything.bullshit.render("Hola", a));
    // }

}