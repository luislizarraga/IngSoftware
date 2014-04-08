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
     * @author Luis Lizarraga
     * @return [description]
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
                                                                                    a.apellidoMaterno,
                                                                                    a.correoElectronico));
        return ok(views.html.alumno.alumnoIniciadoIH.render("Página Principal", user, modificacionFormulario, a));
    }


    /**
     * [iniciarSesionA description]
     * author: Jose Vargas
     * @return [description]
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
                //return redirect(base.routes.CBase.index());
                return ok("noup");
            }
        }
    } 


    /**
     * [cerrarSesionA description]
     * author: Jose Vargas
     * @return [description]
     */
    @Security.Authenticated(SecuredAlumno.class)
    public static Result cerrarSesionA() {
        session().clear(); // Se borra toda la información de la sesión
        //flash("success", "You've been logged out");
        response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        response().setHeader("Pragma","no-cache");
        return redirect(base.routes.CBase.index());
    }


    /**
     * [modificarInformacionA description]
     * author: Lorena Mireles
     * @return [description]
     */
    @Security.Authenticated(SecuredAlumno.class)
    public static Result modificarInformacionA() {
        Alumno a    = Alumno.find.where()
                            .eq("correoElectronico", session().get("correoElectronico"))
                            .findUnique();
        Form<ModificacionAlumno> modificacionFormulario = Form.form(ModificacionAlumno.class).bindFromRequest();
        //System.out.println(modificacionFormulario);
        if (modificacionFormulario.hasErrors()) {
            String user = a.nombre + " " + a.apellidoPaterno;
            return badRequest(views.html.alumno.alumnoIniciadoIH.render("Página Principal", user, modificacionFormulario, a));
        } else {
            ModificacionAlumno ma = modificacionFormulario.get();
            a.setNombre(ma.nombre);
            a.setApellidoPaterno(ma.apellidoPaterno);
            a.setApellidoMaterno(ma.apellidoMaterno);
            a.setCorreoElectronico(ma.correoElectronico);
            session("correoElectronico", ma.correoElectronico);
            if (!ma.contrasenaNueva.equals(""))
                a.setContrasena(ma.contrasenaNueva);
            //System.out.println(ma.contrasenaNueva);
            a.save();
            response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
            response().setHeader("Pragma","no-cache");
            return redirect(routes.CAlumno.index());
        }
    }


    /**
     * [eliminaRegistroA description]
     * author: Lorena Mireles
     * @return [description]
     */
    @Security.Authenticated(SecuredAlumno.class)
    public static Result eliminaRegistroA() {
        Alumno a    = Alumno.find.where()
                            .eq("correoElectronico", session().get("correoElectronico"))
                            .findUnique();
	    a.delete();
        response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        response().setHeader("Pragma","no-cache");
	    return redirect(base.routes.CBase.index());
    }


    /**
     * [registrarA description]
     * author: Norma Trinidad
     * @return [description]
     */
    public static Result registrarA() {
        Form<RegistroAlumno> formularioAlumno = Form.form(RegistroAlumno.class).bindFromRequest();
        //System.out.println(formularioAlumno);
        if (formularioAlumno.hasErrors()) {
            return badRequest(views.html.alumno.alumnoRegistrarFormularioIH.render(formularioAlumno));
        } else {
            RegistroAlumno ra = formularioAlumno.get();
            Alumno user = new Alumno(ra.nombre,
                                     ra.apellidoPaterno,
                                     ra.apellidoMaterno,
                                     ra.correoElectronico,
                                     ra.contrasena);
            user.save();
            //return redirect(routes.CAlumno.index());
            //return redirect(base.routes.CBase.index());
            return ok();
        }
    }

    


    /**
     * [validaFormato description]
     * Posiblemente se necesite uno para inicio de sesion y otro diferente para registro y modificar info
     * author:varios
     * @return [description]
     */
    public static Result validaFormato() {
        return redirect(routes.CAlumno.index());
    }


    /**
     * [modificacionExitosa description]
     * author: Lorena Mireles
     * @return [description]
     */
    public static Result modificacionExitosa() {
        return redirect(routes.CAlumno.index());
    }


    /**
     * [eliminacionExitosa description]
     * author: Lorena Mireles
     * @return [description]
     */
    public static Result eliminacionExitosa() {
        return redirect(routes.CAlumno.index());
    }

}
