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
        Form<RegistroAlumno> formularioAlumno                = Form.form(RegistroAlumno.class);
        Form<RegistroProfesor> formularioProfesor            = Form.form(RegistroProfesor.class);
        Form<InicioSesionAlumno> formularioIniciarAlumno     = Form.form(InicioSesionAlumno.class);
        Form<InicioSesionProfesor> formularioIniciarProfesor = Form.form(InicioSesionProfesor.class);
        List<Curso> cursos                                   = Curso.find.all();
        List<Profesor> profesores                            = Profesor.find.all();
        List<Horario> lunes                                  = Horario.find.where().eq("dia", "Lunes").findList();
        List<Horario> martes                                 = Horario.find.where().eq("dia", "Martes").findList();
        List<Horario> miercoles                              = Horario.find.where().eq("dia", "Miércoles").findList();
        List<Horario> jueves                                 = Horario.find.where().eq("dia", "Jueves").findList();
        List<Horario> viernes                                = Horario.find.where().eq("dia", "Viernes").findList();
        return ok(views.html.principalIH.render(formularioAlumno, 
                                                formularioProfesor,
                                                formularioIniciarAlumno, 
                                                formularioIniciarProfesor, 
                                                cursos, 
                                                profesores, lunes, martes, miercoles, jueves, viernes));
    }


    /**
     * [MostrarProfesores description]
     * author: Luis Lizarraga
     * @return [description]
     */
    public static Result mostrarProfesor() {
        DynamicForm data = Form.form().bindFromRequest();
        Profesor p = Profesor.find.byId(Integer.parseInt(data.get("idProf")));
        return ok(views.html.principal.muestraProfesorIH.render(p));
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
