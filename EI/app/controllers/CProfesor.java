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
        Profesor p = Profesor.find.where().eq("correoElectronico", session().get("correoElectronico")).findUnique(); // aquí hago un query a la BD buscando a 
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
        return redirect(base.routes.CBase.index());
    } 


    /**
     * [modificarInformacionP description]
     * author: Lorena Mireles
     * @return [description]
     */
    public static Result modificarInformacionP() {
        return redirect(base.routes.CBase.index());
    }


    /**
     * [eliminaRegistroP description]
     * author: Lorena Mireles
     * @return [description]
     */
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