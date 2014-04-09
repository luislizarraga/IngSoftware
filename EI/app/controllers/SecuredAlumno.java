package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

/**
 * Clase SecuredAlumno que verifica que haya un alumno con sesión iniciada
 */
public class SecuredAlumno extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        if (ctx.session().get("correoElectronico") != null) {
            if (ctx.session().get("usuario").equals("alumno")) {
                return ctx.session().get("correoElectronico");
            }
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        if (ctx.session().get("usuario") != null && ctx.session().get("usuario").equals("profesor")) {
            return redirect(controllers.routes.CProfesor.index());
        }
        return redirect(base.routes.CBase.index());
    }
}