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
        Form<RegistroAlumno> formularioAlumno        = Form.form(RegistroAlumno.class);
        Form<Profesor> formularioProfesor            = Form.form(Profesor.class);
        Form<InicioSesionAlumno> formularioIniciarAlumno   = Form.form(InicioSesionAlumno.class);
        Form<InicioSesionProfesor> formularioIniciarProfesor = Form.form(InicioSesionProfesor.class);
        return ok(views.html.principalIH.render(formularioAlumno, formularioProfesor, formularioIniciarAlumno, formularioIniciarProfesor));
    }


    // /**
    //  * [registraAlumno description]
    //  * author:
    //  * @return [description]
    //  */
    // public static Result registraAlumno() {
    //     Form<RegistroAlumno> formularioAlumno = Form.form(RegistroAlumno.class).bindFromRequest();
    //     System.out.println(formularioAlumno);
    //     if (formularioAlumno.hasErrors()) {
    //         Form<Profesor> formularioProfesor  = Form.form(Profesor.class);
    //         Form<InicioSesion> formularioIniciarAlumno = Form.form(InicioSesion.class);
    //         return badRequest(views.html.principalIH.render(formularioAlumno, formularioProfesor,formularioIniciarAlumno));
    //     } else {
    //         RegistroAlumno ra = formularioAlumno.get();
    //         Alumno user = new Alumno(ra.nombre,
    //                                  ra.apellidoPaterno,
    //                                  ra.apellidoMaterno,
    //                                  ra.correoElectronico,
    //                                  ra.contrasena);
    //         user.save();
    //         return redirect(routes.CBase.index());
    //     }
    // }


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
