package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

/**
 * Clase SecuredProfesor que verifica que haya un profesor con sesión iniciada
 */
public class SecuredProfesor extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        if (ctx.session().get("correoElectronico") != null) {
            if (ctx.session().get("usuario").equals("profesor")) {
                return ctx.session().get("correoElectronico");
            }
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        if (ctx.session().get("usuario") != null && ctx.session().get("usuario").equals("alumno")) {
            return redirect(controllers.routes.CAlumno.index());
        }
        return redirect(base.routes.CBase.index());
    }
}