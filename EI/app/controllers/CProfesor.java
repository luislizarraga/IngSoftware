package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;

import views.html.*;
import models.*;
import play.data.*;
import java.util.*;
import forms.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import play.mvc.Http.MultipartFormData.FilePart;
import java.io.InputStream;
import java.io.FileInputStream;
import org.apache.commons.io.*;

public class CProfesor extends Controller {


    /**
     * [index description]
     * author: Luis Lizarraga
     * @return [description]
     */
    @Security.Authenticated(SecuredProfesor.class)
    public static Result index() {
        Profesor p = Profesor.find.where()
                            .eq("correoElectronico", session().get("correoElectronico"))
                            .findUnique();
        response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        response().setHeader("Pragma","no-cache");
        String user                                       = p.nombre + " " + p.apellidoPaterno;
        Form<ModificacionProfesor> modificacionFormulario = Form.form(ModificacionProfesor.class);
        modificacionFormulario = modificacionFormulario.fill(new ModificacionProfesor(p.nombre,
                                                                                    p.apellidoPaterno,
                                                                                    p.apellidoMaterno,
                                                                                    p.correoElectronico));
        return ok(views.html.profesor.profesorIniciadoIH.render("Página Principal", user, modificacionFormulario, p));
    }


    /**
     * [iniciarSesionP description]
     * author: Jose Vargas
     * @return [description]
     */
    public static Result iniciarSesionP() {
        Form<InicioSesionProfesor> formularioIniciar = Form.form(InicioSesionProfesor.class).bindFromRequest();
        if (formularioIniciar.hasErrors()) {
            return forbidden(views.html.profesor.profesorIniciarSesionFormularioIH.render(formularioIniciar));
        } else {
            session().clear();
            Profesor p = Profesor.find.where().eq("correoElectronico", formularioIniciar.get().correoElectronico).findUnique();
            if (p.getContrasena().equals(formularioIniciar.get().contrasena)) {
                //return redirect(routes.CProfesor.index());
                session("correoElectronico", formularioIniciar.get().correoElectronico);
                session("usuario", "profesor");
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
     * [cerrarSesionP description]
     * author: Jose Vargas
     * @return [description]
     */
    @Security.Authenticated(SecuredProfesor.class)
    public static Result cerrarSesionP() {
        session().clear(); // Se borra toda la información de la sesión
        //flash("success", "You've been logged out");
        response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        response().setHeader("Pragma","no-cache");
        return redirect(base.routes.CBase.index());
    }


    /**
     * [modificarInformacionP description]
     * author: Lorena Mireles
     * @return [description]
     */
    @Security.Authenticated(SecuredProfesor.class)
    public static Result modificarInformacionP() {
        Profesor p    = Profesor.find.where()
                            .eq("correoElectronico", session().get("correoElectronico"))
                            .findUnique();
        Form<ModificacionProfesor> modificacionFormulario = Form.form(ModificacionProfesor.class).bindFromRequest();
        //System.out.println(modificacionFormulario);
        if (modificacionFormulario.hasErrors()) {
            String user = p.nombre + " " + p.apellidoPaterno;
            return badRequest(views.html.profesor.profesorIniciadoIH.render("Página Principal", user, modificacionFormulario, p));
        } else {
            ModificacionProfesor mp = modificacionFormulario.get();
            p.setNombre(mp.nombre);
            p.setApellidoPaterno(mp.apellidoPaterno);
            p.setApellidoMaterno(mp.apellidoMaterno);
            p.setCorreoElectronico(mp.correoElectronico);
            session("correoElectronico", mp.correoElectronico);
            if (!mp.contrasenaNueva.equals(""))
                p.setContrasena(mp.contrasenaNueva);
            p.save();
            response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
            response().setHeader("Pragma","no-cache");
            return redirect(routes.CProfesor.index());
        }
    }


    /**
     * [eliminaRegistroP description]
     * author: Lorena Mireles
     * @return [description]
     */
    @Security.Authenticated(SecuredProfesor.class)
    public static Result eliminaRegistroP() {
        Profesor p  = Profesor.find.where()
                            .eq("correoElectronico", session().get("correoElectronico"))
                            .findUnique();
	    p.delete();
        response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        response().setHeader("Pragma","no-cache");
	    return redirect(base.routes.CBase.index());
    }


   /**
     * [registrarP description]
     * author: Norma Trinidad
     * @return [description]
     */
    public static Result registrarP() {
        Form<RegistroProfesor> formularioProfesor = Form.form(RegistroProfesor.class).bindFromRequest();
        //System.out.println(new File("public/1/const.txt").getAbsolutePath());
        //boolean f = (new File("public/1")).mkdirs();
        MultipartFormData body = request().body().asMultipartFormData();
        //System.out.println(formularioProfesorMultipart);
        //System.out.println(formularioProfesor);
        if (formularioProfesor.hasErrors()) {
            return badRequest(views.html.profesor.profesorRegistrarFormularioIH.render(formularioProfesor));
        } else {
            RegistroProfesor rp = formularioProfesor.get();
            Profesor user = new Profesor(rp.nombre,
                                     rp.apellidoPaterno,
                                     rp.apellidoMaterno,
                                     rp.correoElectronico,
                                     rp.contrasena);
            user.save();
            if (body != null) {
            FilePart filePart1 = body.getFile("constancia");
            FilePart filePart2 = body.getFile("video");
            boolean sePudo = (new File("public/professordata/"+user.getId())).mkdirs();
            File file1 = filePart1.getFile();
            File file2 = filePart2.getFile();
            System.out.println(filePart1.getFilename() + filePart2.getFilename());
            System.out.println(sePudo);
            System.out.println(user.getId());
            File newFile1 = new File("public/professordata/"+user.getId()+"/" + filePart1.getFilename());
            File newFile2 = new File("public/professordata/"+user.getId()+"/" + filePart2.getFilename());
            try {
                InputStream isFile1 = new FileInputStream(file1);
                InputStream isFile2 = new FileInputStream(file2);

                byte[] byteFile1 = IOUtils.toByteArray(isFile1);
                byte[] byteFile2 = IOUtils.toByteArray(isFile2);

                FileUtils.writeByteArrayToFile(newFile1, byteFile1);
                FileUtils.writeByteArrayToFile(newFile2, byteFile2);

                isFile1.close();
                isFile2.close();
            } catch (FileNotFoundException fnfe) {
                System.out.println("No se encontró el archivo");
            } catch (IOException ioe) {
                System.out.println("Error");
            }
            }
            Form<CrearCurso> formularioCurso = Form.form(CrearCurso.class);
            session().clear();
            session("correoElectronico", rp.correoElectronico);
            session("usuario", "profesor");
            response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
            response().setHeader("Pragma","no-cache");
            return ok(views.html.profesor.profesorAgregarCursoFormularioIH.render(formularioCurso));
            //return redirect(base.routes.CBase.index());
        }
    }


    @Security.Authenticated(SecuredProfesor.class)
    public static Result agregarCursoP() {
        Profesor p = Profesor.find.where()
                            .eq("correoElectronico", session().get("correoElectronico"))
                            .findUnique();
        Form<CrearCurso> cursoFormulario = Form.form(CrearCurso.class).bindFromRequest();
        if (p == null) {
            return badRequest("No se pudo crear un horario, favor de iniciar sesion y crear uno");
        }
        if (cursoFormulario.hasErrors()) {
            return badRequest(views.html.profesor.profesorAgregarCursoFormularioIH.render(cursoFormulario));
        } else {
            CrearCurso cc = cursoFormulario.get();
            Curso c = new Curso(false, " ", 0, cc.nivel, p);
            Horario h = new Horario(cc.dias, cc.horaInicio, cc.horaFin, c);
            c.save();
            h.save();
            response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
            response().setHeader("Pragma","no-cache");
            return redirect(routes.CProfesor.index());
        }
    }


    /**
     * [validaFormato description]
     * Posiblemente se necesite uno para inicio de sesion y otro diferente para registro y modificar info
     * author:varios
     * @return [description]
     */
    public static Result validaFormato() {
       // return redirect(base.routes.CBase.index());
       return redirect(routes.CProfesor.index());
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
        //return redirect(base.routes.CBase.index());
        return redirect(routes.CProfesor.index());
    }
}
