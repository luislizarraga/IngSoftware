package base;

import play.*;
import play.mvc.*;

import views.html.*;
import models.*;
import play.data.Form;
import java.util.*;

public class Application extends Controller {

    public static Result index() {
        Form<Alumno> alumnoForma = Form.form(Alumno.class);
        Form<Profesor> profesorForma = Form.form(Profesor.class);
        return ok(views.html.baseViews.principalIH.render(alumnoForma, profesorForma));
    }



    public static Result registraAlumno() {
        Form<Alumno> alumnoForma = Form.form(Alumno.class).bindFromRequest();
        System.out.println(alumnoForma);
        if (alumnoForma.hasErrors()) {
            return badRequest(views.html.baseViews.principalIH.render(alumnoForma));
        } else {
            Alumno user = alumnoForma.get();
            user.save();
            return ok("Got user " + user);
        }
    }


    public static Result enlistaAlumnos() {
        List<Alumno> alumnos = Alumno.find.all();
        return ok(bla.render(alumnos));
    }


}
