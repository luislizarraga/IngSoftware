package base;

import play.*;
import play.mvc.*;

import views.html.*;
import views.html.everything.*;
import models.*;
import play.data.*;
import java.util.*;
import forms.*;

public class CBase extends Controller {


    /**
     * [index description]
     * author:
     * @return [description]
     */
    public static Result index() {
        Form<RegistrationAlumno> alumnoForma = Form.form(RegistrationAlumno.class);
        Form<Profesor> profesorForma = Form.form(Profesor.class);
        Form<Login> alumnoLoginForm = Form.form(Login.class);
        return ok(views.html.principalIH.render(alumnoForma, profesorForma, alumnoLoginForm));
    }


    /**
     * [registraAlumno description]
     * author:
     * @return [description]
     */
    public static Result registraAlumno() {
        Form<RegistrationAlumno> alumnoForma = Form.form(RegistrationAlumno.class).bindFromRequest();
        System.out.println(alumnoForma);
        if (alumnoForma.hasErrors()) {
            Form<Profesor> profesorForma = Form.form(Profesor.class);
            Form<Login> alumnoLoginForm = Form.form(Login.class);
            return badRequest(views.html.principalIH.render(alumnoForma, profesorForma,alumnoLoginForm));
        } else {
            //Alumno user = alumnoForma.get();
            //user.save();
            //return ok("Got user " + user);
            return redirect(
                routes.CBase.index()
            );
        }
    }


    /**
     * [enlistaAlumnos description]
     * author:
     * @return [description]
     */
    public static Result enlistaAlumnos() {
        List<Alumno> alumnos = Alumno.find.all();
        return ok(bla.render(alumnos));
    }


    /**
     * [MostrarProfesores description]
     * author: Luis Lizarraga
     * @return [description]
     */
    public static Result MostrarProfesores() {
        List<Alumno> alumnos = Alumno.find.all();
        return ok(bla.render(alumnos));
    }


    /**
     * [mostrarHorarios description]
     * author: Luis Lizarraga
     * @return [description]
     */
    public static Result mostrarHorarios() {
        List<Alumno> alumnos = Alumno.find.all();
        return ok(bla.render(alumnos));
    }


    /**
     * [mostrarNiveles description]
     * author: Luis Lizarraga
     * @return [description]
     */
    public static Result mostrarNiveles() {
        List<Alumno> alumnos = Alumno.find.all();
        return ok(bla.render(alumnos));
    }

}
