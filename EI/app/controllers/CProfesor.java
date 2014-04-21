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

/**
 * Clase CProfesor, el controlador para profesor
 */
public class CProfesor extends Controller {


    /**
     * Método index que muestra la página principal para el profesor en el servidor
     * author: Luis Lizarraga
     * @return Http response 200, que contiene la página principal del profesor
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
     * Método que verifica que la contrasña dada sea la misma que el usuario dado y crea una sesion con el usuario
     * author: Jose Vargas
     * @return un Http response 200 si se pudo iniciar sesión, 401 si no correspondió la contraseña con el usuario
     */
    public static Result iniciarSesionP() {
        Form<InicioSesionProfesor> formularioIniciar = Form.form(InicioSesionProfesor.class).bindFromRequest();
        if (formularioIniciar.hasErrors()) {
            return forbidden(views.html.profesor.profesorIniciarSesionFormularioIH.render(formularioIniciar));
        } else {
            session().clear();
            Profesor p = Profesor.find.where().eq("correoElectronico", formularioIniciar.get().correoElectronico).findUnique();
            if (p.getContrasena().equals(formularioIniciar.get().contrasena)) {
                session("correoElectronico", formularioIniciar.get().correoElectronico);
                session("usuario", "profesor");
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
    @Security.Authenticated(SecuredProfesor.class)
    public static Result cerrarSesionP() {
        session().clear(); // Se borra toda la información de la sesión
        response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        response().setHeader("Pragma","no-cache");
        return redirect(base.routes.CBase.index());
    }


    /**
     * Método que modifica la información en la base de datos de un usuario(Profesor) dado.
     * author: Lorena Mireles
     * @return un Http response 200 si se pudo modificar la información o un Http response 403 si no pasó la verificación
     */
    @Security.Authenticated(SecuredProfesor.class)
    public static Result modificarInformacionP() {
        Profesor p    = Profesor.find.where()
                            .eq("correoElectronico", session().get("correoElectronico"))
                            .findUnique();
        Form<ModificacionProfesor> modificacionFormulario = Form.form(ModificacionProfesor.class).bindFromRequest();
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
     * Método que elimina a un usuario(Profesor) de la base de datos
     * author: Lorena Mireles
     * @return un Http response 303 que redirecciona a la página principal
     */
    @Security.Authenticated(SecuredProfesor.class)
    public static Result eliminaRegistroP() {
        Profesor p  = Profesor.find.where()
                            .eq("correoElectronico", session().get("correoElectronico"))
                            .findUnique();
	    p.delete();
        session().clear();
        response().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        response().setHeader("Pragma","no-cache");
	    return redirect(base.routes.CBase.index());
    }


    /**
     * Método que registra un profesor dado un formulario que contiene la constancia y el video
     * author: Norma Trinidad
     * @return un Http Response 200 si no hubo errores de verificacion, Http response 401 si los hubo
     */
    public static Result registrarP() {
        Form<RegistroProfesor> formularioProfesor = Form.form(RegistroProfesor.class).bindFromRequest();
        MultipartFormData body = request().body().asMultipartFormData();
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

                user.setConstancia("public/professordata/"+user.getId()+"/" + filePart1.getFilename());
                user.setVideo("public/professordata/"+user.getId()+"/" + filePart2.getFilename());
                user.save();

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

    public static Result descargaArchivos(Integer id, int file) {
        Profesor p = Profesor.find.byId(id);
        File f = null;
        if (file == 1) {
            f = new File(p.getConstancia());
        } else if (file == 2) {
            f = new File(p.getVideo());
        }
        return ok(f);
    }
}
