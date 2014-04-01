package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import models.*;
import play.data.*;
import java.util.*;
import forms.*;

public class CProfesor extends Controller {


    /**
     * [index description]
     * author: Luis Lizarraga
     * @return [description]
     */
    @Security.Authenticated(SecuredProfesor.class)
    public static Result index() {
        Profesor p = Profesor.find.where().eq("correoElectronico", session().get("correoElectronico")).findUnique(); // aquí hago un query a la BD buscando a 
                                                                                                    // aquellos alumnos que tengan el email dado
                                                                                                    // eso de findUnique() se pone para que te regrese un
                                                                                                    // solo objeto y no una lista de objetos
        String user = p.nombre + " " + p.apellidoPaterno;
        Form<ModificacionProfesor> modificacionFormulario = Form.form(ModificacionProfesor.class);
        modificacionFormulario = modificacionFormulario.fill(new ModificacionProfesor(p.nombre,
                                                                                    p.apellidoPaterno,
                                                                                    p.apellidoMaterno,
                                                                                    p.correoElectronico));
        return ok(views.html.profesor.profesorIniciado.render("Página Principal", user, modificacionFormulario, p));
    }


    /**
     * [iniciarSesionP description]
     * author: Jose Vargas
     * @return [description]
     */
    public static Result iniciarSesionP() {
        Form<InicioSesionProfesor> formularioIniciar = Form.form(InicioSesionProfesor.class).bindFromRequest();
        System.out.println(formularioIniciar);
        if (formularioIniciar.hasErrors()) {
            return forbidden(views.html.profesor.profesorIniciarSesionFormulario.render(formularioIniciar));
        } else {
            session().clear();                                                          // Se borra toda la información de la sesión
            session("correoElectronico", formularioIniciar.get().correoElectronico);    // Se agrega el correoElectronico a la sesion
            session("usuario", "profesor");                                               // Se agrega el tipo de usuario a la sesion
            Profesor p = Profesor.find.where().eq("correoElectronico", formularioIniciar.get().correoElectronico).findUnique();
            if (p.getContrasena().equals(formularioIniciar.get().contrasena)) {
                //return redirect(routes.CProfesor.index());
                return ok();
            } else {
                //return redirect(base.routes.CBase.index());
                return ok("noup");
            }
        }
    } 

    /**
     * [cerrarSesionP description]
     * author: Jose Vargas
     * @return [description]
     */
    @Security.Authenticated(SecuredProfesor.class)
    public static Result cerrarSesionP() {
        session().clear(); // Se borra toda la información de la sesión
        //flash("success", "You've been logged out");
        return redirect(base.routes.CBase.index());
    }

    /**
     * [modificarInformacionP description]
     * author: Lorena Mireles
     * @return [description]
     */
    @Security.Authenticated(SecuredProfesor.class)
    public static Result modificarInformacionP() {
        return redirect(base.routes.CBase.index());
    }


    /**
     * [eliminaRegistroP description]
     * author: Lorena Mireles
     * @return [description]
     */
    @Security.Authenticated(SecuredProfesor.class)
    public static Result eliminaRegistroP() {
        return redirect(base.routes.CBase.index());
    }


    /**
     * [registrarP description]
     * author: Norma Trinidad
     * @return [description]
     */
    public static Result registrarP() {
        return redirect(base.routes.CBase.index());
    }


    /**
     * [validaFormato description]
     * Posiblemente se necesite uno para inicio de sesion y otro diferente para registro y modificar info
     * author:varios
     * @return [description]
     */
    public static Result validaFormato() {
        return redirect(base.routes.CBase.index());
    }


    /**
     * [modificacionExitosa description]
     * author: Lorena Mireles
     * @return [description]
     */
    public static Result modificacionExitosa() {
        return redirect(base.routes.CBase.index());
    }


    /**
     * [eliminacionExitosa description]
     * author: Lorena Mireles
     * @return [description]
     */
    public static Result eliminacionExitosa() {
        return redirect(base.routes.CBase.index());
    }
}