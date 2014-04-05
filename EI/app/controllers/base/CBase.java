package base;

import play.*;
import play.mvc.*;

import views.html.*;
import views.html.everything.*;
import models.*;
import play.data.*;
import java.util.*;
import forms.*;
import java.text.DateFormat;
import java.text.ParseException;

public class CBase extends Controller {


    /**
     * [index description]
     * author:
     * @return [description]
     */
    public static Result index() {
        Form<RegistroAlumno> formularioAlumno      = Form.form(RegistroAlumno.class);
        Form<Profesor> formularioProfesor          = Form.form(Profesor.class);
        Form<InicioSesionAlumno> formularioIniciarAlumno   = Form.form(InicioSesionAlumno.class);
        Form<InicioSesionProfesor> formularioIniciarProfesor = Form.form(InicioSesionProfesor.class);
        List<Curso> cursos = Curso.find.all();
        List<Profesor> profesores = Profesor.find.all();
        Profesor p    = Profesor.find.where()
                            .eq("id", 1)
                            .findUnique();
        System.out.println(p.cursos.get(0).getNivel());
        // Horario hor = null;
        // try {
        //     hor = new Horario("Lunes", (new Date().setHour), (DateFormat.getTimeInstance(DateFormat.HOUR_OF_DAY0_FIELD).parse("16")));
        // } catch (ParseException pe) {
        //     System.out.println("parse exception");
        // }
        // System.out.println(hor.getDia());
        // System.out.println(hor.getHoraInicio());
        return ok(views.html.principalIH.render(formularioAlumno, formularioProfesor, formularioIniciarAlumno, formularioIniciarProfesor, cursos, profesores));
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
