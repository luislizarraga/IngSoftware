package base;

import play.*;
import play.mvc.*;

import views.html.*;
import models.*;
import play.data.*;
import java.util.*;
import forms.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.io.File;


/**
 * Clase CBase, el controlador para la página principal
 */
public class CBase extends Controller {


    /**
     * Método index que muestra la página principal del servidor
     * author: Luis Lizárraga
     * @return un Http response 200, que contiene la página principal del servidor
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
     * Método que obtiene la información de un profesor dado su identificador
     * author: Luis Lizarraga
     * @return un Http response 200 que contiene una parte de la página principal que se despliega en el explorador
     */
    public static Result mostrarProfesor() {
        DynamicForm data = Form.form().bindFromRequest();
        Profesor p = Profesor.find.byId(Integer.parseInt(data.get("idProf")));
        return ok(views.html.principal.muestraProfesorIH.render(p));
    }

}
