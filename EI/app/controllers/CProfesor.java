package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import models.*;
import play.data.Form;
import java.util.*;
import forms.*;

public class CProfesor extends Controller {


    /**
     * [index description]
     * author: Luis Lizarraga
     * @return [description]
     */
    public static Result index() {
        Profesor p = Profesor.find.where().eq("correoElectronico", session().get("email")).findUnique(); // aquí hago un query a la BD buscando a 
                                                                                                    // aquellos alumnos que tengan el email dado
                                                                                                    // eso de findUnique() se pone para que te regrese un
                                                                                                    // solo objeto y no una lista de objetos
        String user = "";
        return ok(views.html.profesor.profesorIniciado.render("Página Principal", p, user));
    }


    /**
     * [iniciarSesionP description]
     * author: Jose Vargas
     * @return [description]
     */
    public static Result iniciarSesionP() {
        Form<RegistrationAlumno> alumnoForma = Form.form(RegistrationAlumno.class);
        Form<Profesor> profesorForma = Form.form(Profesor.class);
        Form<Login> alumnoLoginForm = Form.form(Login.class);
        return ok(views.html.principalIH.render(alumnoForma, profesorForma, alumnoLoginForm));
    } 


    /**
     * [modificarInformacionP description]
     * author: Lorena Mireles
     * @return [description]
     */
    public static Result modificarInformacionP() {
        Form<RegistrationAlumno> alumnoForma = Form.form(RegistrationAlumno.class);
        Form<Profesor> profesorForma = Form.form(Profesor.class);
        Form<Login> alumnoLoginForm = Form.form(Login.class);
        return ok(views.html.principalIH.render(alumnoForma, profesorForma, alumnoLoginForm));
    }


    /**
     * [eliminaRegistroP description]
     * author: Lorena Mireles
     * @return [description]
     */
    public static Result eliminaRegistroP() {
        Form<RegistrationAlumno> alumnoForma = Form.form(RegistrationAlumno.class);
        Form<Profesor> profesorForma = Form.form(Profesor.class);
        Form<Login> alumnoLoginForm = Form.form(Login.class);
        return ok(views.html.principalIH.render(alumnoForma, profesorForma, alumnoLoginForm));
    }


    /**
     * [registrarP description]
     * author: Norma Trinidad
     * @return [description]
     */
    public static Result registrarP() {
        Form<RegistrationAlumno> alumnoForma = Form.form(RegistrationAlumno.class);
        Form<Profesor> profesorForma = Form.form(Profesor.class);
        Form<Login> alumnoLoginForm = Form.form(Login.class);
        return ok(views.html.principalIH.render(alumnoForma, profesorForma, alumnoLoginForm));
    }


    /**
     * [validaFormato description]
     * Posiblemente se necesite uno para inicio de sesion y otro diferente para registro y modificar info
     * author:varios
     * @return [description]
     */
    public static Result validaFormato() {
        Form<RegistrationAlumno> alumnoForma = Form.form(RegistrationAlumno.class);
        Form<Profesor> profesorForma = Form.form(Profesor.class);
        Form<Login> alumnoLoginForm = Form.form(Login.class);
        return ok(views.html.principalIH.render(alumnoForma, profesorForma, alumnoLoginForm));
    }


    /**
     * [modificacionExitosa description]
     * author: Lorena Mireles
     * @return [description]
     */
    public static Result modificacionExitosa() {
        Form<RegistrationAlumno> alumnoForma = Form.form(RegistrationAlumno.class);
        Form<Profesor> profesorForma = Form.form(Profesor.class);
        Form<Login> alumnoLoginForm = Form.form(Login.class);
        return ok(views.html.principalIH.render(alumnoForma, profesorForma, alumnoLoginForm));
    }


    /**
     * [eliminacionExitosa description]
     * author: Lorena Mireles
     * @return [description]
     */
    public static Result eliminacionExitosa() {
        Form<RegistrationAlumno> alumnoForma = Form.form(RegistrationAlumno.class);
        Form<Profesor> profesorForma = Form.form(Profesor.class);
        Form<Login> alumnoLoginForm = Form.form(Login.class);
        return ok(views.html.principalIH.render(alumnoForma, profesorForma, alumnoLoginForm));
    }
}