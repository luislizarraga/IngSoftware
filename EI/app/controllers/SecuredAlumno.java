package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

public class SecuredAlumno extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        if (ctx.session().get("email") != null) {
            if (ctx.session().get("user").equals("alumno")) {
                return ctx.session().get("email");
            }
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        if (ctx.session().get("user").equals("profesor")) {
            return redirect(controllers.routes.CProfesor.index());
        }
        return redirect(base.routes.CBase.index());
    }
}