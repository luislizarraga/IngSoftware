package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import models.*;
import play.data.*;
import java.util.*;
import forms.*;
import com.typesafe.plugin.*;


/**
 * Clase CAlumno el controlador del Alumno
 */
public class CAlumno extends Controller {


    /**
     * Método index que muestra la página principal para el alumno en el servidor
     * @author Luis Lizarraga
     * @return una página html que se despliega en el explorador
     */
    @Security.Authenticated(SecuredAlumno.class)
    public static Result index() {
        Alumno a    = Alumno.find.where()                                                    // aquí hago un query a la BD buscando a 
                            .eq("correoElectronico", session().get("correoElectronico"))     // aquellos alumnos que tengan el email dado
                            .findUnique();                                                   // eso de findUnique() se pone para que te regrese un
                                                                                             // solo objeto y no una lista de objetos                                                                                
        response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        response().setHeader("Pragma","no-cache");
        String user                                     = a.nombre + " " + a.apellidoPaterno;
        Form<ModificacionAlumno> modificacionFormulario = Form.form(ModificacionAlumno.class);
        modificacionFormulario = modificacionFormulario.fill(new ModificacionAlumno(a.nombre,
                                                                                    a.apellidoPaterno,
                                                                                    a.apellidoMaterno));
        List<Curso> cursos = a.getCursos();
        System.out.println(cursos);
        return ok(views.html.alumno.alumnoIniciadoIH.render("Página Principal", user, modificacionFormulario, a, cursos));
    }


    /**
     * Método que verifica que la contrasña dada sea la misma que el usuario dado y crea una sesion con el usuario
     * author: Jose Vargas
     * @return un Http response 200 si se pudo iniciar sesión, 401 si no correspondió la contraseña con el usuario
     */
    public static Result iniciarSesionA() {
        Form<InicioSesionAlumno> formularioIniciar = Form.form(InicioSesionAlumno.class).bindFromRequest();
        if (formularioIniciar.hasErrors()) {
            return unauthorized(views.html.alumno.alumnoIniciarSesionFormularioIH.render(formularioIniciar));
        } else {
            session().clear();                                                          // Se borra toda la información de la sesión
            session("correoElectronico", formularioIniciar.get().correoElectronico);    // Se agrega el correoElectronico a la sesion
            session("usuario", "alumno");                                               // Se agrega el tipo de usuario a la sesion
            Alumno a = Alumno.find.where().eq("correoElectronico", formularioIniciar.get().correoElectronico).findUnique();
            if (a.getContrasena().equals(formularioIniciar.get().contrasena)) {
                //return redirect(routes.CAlumno.index());
                response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
                response().setHeader("Pragma","no-cache");  
                return ok();
            } else {
                return ok("noup");
            }
        }
    } 


    /**
     * Método que invalida la sesión del usuario
     * author: Jose Vargas
     * @return un http response 303, redireccionando a la página principal del servidor
     */
    @Security.Authenticated(SecuredAlumno.class)
    public static Result cerrarSesionA() {
        session().clear(); // Se borra toda la información de la sesión
        response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        response().setHeader("Pragma","no-cache");
        return redirect(base.routes.CBase.index());
    }


    /**
     * Método que modifica la información en la base de datos de un usuario(Alumno) dado.
     * author: Lorena Mireles
     * @return un Http response 200 si se pudo modificar la información o un Http response 403 si no pasó la verificación
     */
    @Security.Authenticated(SecuredAlumno.class)
    public static Result modificarInformacionA() {
        Alumno a    = Alumno.find.where()
                            .eq("correoElectronico", session().get("correoElectronico"))
                            .findUnique();
        Form<ModificacionAlumno> modificacionFormulario = Form.form(ModificacionAlumno.class).bindFromRequest();
        if (modificacionFormulario.hasErrors()) {
            String user = a.nombre + " " + a.apellidoPaterno;
            //return badRequest(views.html.alumno.alumnoIniciadoIH.render("Página Principal", user, modificacionFormulario, a));
            return badRequest(views.html.alumno.alumnoModificarDatosFormularioIH.render(modificacionFormulario,a));
        } else {
            ModificacionAlumno ma = modificacionFormulario.get();
            a.setNombre(ma.nombre);
            a.setApellidoPaterno(ma.apellidoPaterno);
            a.setApellidoMaterno(ma.apellidoMaterno);
            //a.setCorreoElectronico(ma.correoElectronico);
            //session("correoElectronico", ma.correoElectronico);
            if (!ma.contrasenaNueva.equals(""))
                a.setContrasena(ma.contrasenaNueva);
            a.save();
            response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
            response().setHeader("Pragma","no-cache");
            modificacionFormulario = Form.form(ModificacionAlumno.class);
            modificacionFormulario = modificacionFormulario.fill(new ModificacionAlumno(a.nombre,
                                                                                    a.apellidoPaterno,
                                                                                    a.apellidoMaterno));
            return ok(views.html.alumno.alumnoModificarDatosFormularioIH.render(modificacionFormulario,a));
        }
    }


    /**
     * Método que elimina a un usuario(Alumno) de la base de datos
     * author: Lorena Mireles
     * @return un Http response 303 que redirecciona a la página principal
     */
    @Security.Authenticated(SecuredAlumno.class)
    public static Result eliminaRegistroA() {
        Alumno a    = Alumno.find.where()
                            .eq("correoElectronico", session().get("correoElectronico"))
                            .findUnique();
	    a.delete();
        session().clear();
        response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        response().setHeader("Pragma","no-cache");
	    return redirect(base.routes.CBase.index());
    }


    /**
     * Método que registra un alumno dado un formulario
     * author: Norma Trinidad
     * @return un Http Response 200 si no hubo errores de verificacion, Http response 401 si los hubo
     */
    public static Result registrarA() {
        Form<RegistroAlumno> formularioAlumno = Form.form(RegistroAlumno.class).bindFromRequest();
        if (formularioAlumno.hasErrors()) {
            return badRequest(views.html.alumno.alumnoRegistrarFormularioIH.render(formularioAlumno));
        } else {
            RegistroAlumno ra = formularioAlumno.get();
            Alumno user       = new Alumno(ra.nombre,
                                        ra.apellidoPaterno,
                                        ra.apellidoMaterno,
                                        ra.correoElectronico,
                                        ra.contrasena);
            user.save();
            formularioAlumno  = Form.form(RegistroAlumno.class);
            return ok(views.html.alumno.alumnoRegistrarFormularioIH.render(formularioAlumno));
        }
    }


    @Security.Authenticated(SecuredAlumno.class)
    public static Result agregarCursoA() {
        DynamicForm data = Form.form().bindFromRequest();
        Curso c = Curso.find.byId(Integer.parseInt(data.get("idCurso")));
        System.out.println(c);
        Alumno a    = Alumno.find.where()
                            .eq("correoElectronico", session().get("correoElectronico"))
                            .findUnique();
        c.setAlumno(a);
        c.save();
        Profesor p = c.getProfesor();
        System.out.println(a.getCursos());
        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject("Alumno registrado!");
        mail.setRecipient(p.getNombre() + " " + p.getApellidoPaterno() + " <" + p.getCorreoElectronico() + "> ");
        mail.setFrom("Escuela de Inglés <noreply@escueladeingles.com>");
        String htmls = views.html.alumno.alumnoCorreoIH.render(p,a).toString();
        mail.sendHtml(htmls);
        //mail.send( "text", "<html>html</html>");
        response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        response().setHeader("Pragma","no-cache");
        return ok();
    }


    @Security.Authenticated(SecuredAlumno.class)
    public static Result eliminarCursoA() {
        DynamicForm data = Form.form().bindFromRequest();
        Curso c = Curso.find.byId(Integer.parseInt(data.get("idCurso")));
        c.setAlumno(null);
        c.save();
        return ok();
    }


    @Security.Authenticated(SecuredAlumno.class)
    public static Result verCursoA(Integer id) {
        Curso c = Curso.find.byId(id);
        Alumno a    = Alumno.find.where()
                            .eq("correoElectronico", session().get("correoElectronico"))
                            .findUnique();
        response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        response().setHeader("Pragma","no-cache");
        String user                                     = a.nombre + " " + a.apellidoPaterno;
        Form<ModificacionAlumno> modificacionFormulario = Form.form(ModificacionAlumno.class);
        modificacionFormulario = modificacionFormulario.fill(new ModificacionAlumno(a.nombre,
                                                                                    a.apellidoPaterno,
                                                                                    a.apellidoMaterno));
        return ok(views.html.alumno.alumnoMuestraCursoIH.render("Ver Curso", user, modificacionFormulario,a,c));
    }


    @Security.Authenticated(SecuredAlumno.class)
    public static Result obtenerConstancia() {
        return ok();
    }


    @Security.Authenticated(SecuredAlumno.class)
    public static Result obtenerCursos() {
        Alumno a    = Alumno.find.where()
                            .eq("correoElectronico", session().get("correoElectronico"))
                            .findUnique();
        response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        response().setHeader("Pragma","no-cache");
        String user                                     = a.nombre + " " + a.apellidoPaterno;
        Form<ModificacionAlumno> modificacionFormulario = Form.form(ModificacionAlumno.class);
        modificacionFormulario = modificacionFormulario.fill(new ModificacionAlumno(a.nombre,
                                                                                    a.apellidoPaterno,
                                                                                    a.apellidoMaterno));
        List<Curso> cursos     = Curso.find.all();
        return ok(views.html.alumno.alumnoListaCursosIH.render("Cursos Disponibles",user,modificacionFormulario,a, cursos));
    }


    @Security.Authenticated(SecuredAlumno.class)
    public static Result obtenerHorario() {
        return ok();
    }
}
