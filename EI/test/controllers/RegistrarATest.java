package controllers;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

import models.Alumno;
import play.mvc.*;
import play.libs.*;
import play.test.*;
import static play.test.Helpers.*;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.MySqlPlatform;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import com.google.common.collect.ImmutableMap;

@SuppressWarnings("unchecked")
public class RegistrarATest extends WithApplication {
    
    @Before
    public void setUp() {
        //start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
        //Ebean.save((List) Yaml.load("test-data.yml"));
        EbeanServer server = Ebean.getServer("default");
        ServerConfig config = new ServerConfig();
        DdlGenerator ddl = new DdlGenerator();
        ddl.setup((SpiEbeanServer) server, new MySqlPlatform(), config);
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
        //Ebean.save(new Alumno("luis","lizarraga","santos","luis@gmail.com","luis"));
    }


    @Test
    public void registerSuccess() {
        Map<String,String> form  = new HashMap();
        form.put("nombre", "Maria");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "maria@gmail.com");
        form.put("contrasena", "maria");
        form.put("confContrasena", "maria");
        Result result = callAction(
            controllers.routes.ref.CAlumno.registrarA(),
            fakeRequest().withFormUrlEncodedBody(form)
        );
        //System.out.println(session(result));
        assertEquals(200, status(result));
        Alumno a = Alumno.find.where().eq("correoElectronico", "maria@gmail.com").findUnique();
        assert(a != null);
        assertEquals(a.getNombre(), "Maria");
        assertEquals(a.getApellidoPaterno(), "Toyos");
        assertEquals(a.getApellidoMaterno(), "Swanson");
        assertEquals(a.getContrasena(), "maria");
        assertEquals(a.getCorreoElectronico(), "maria@gmail.com");

        form  = new HashMap();
        form.put("nombre", "Maria");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "");
        form.put("correoElectronico", "maria1@gmail.com");
        form.put("contrasena", "maria");
        form.put("confContrasena", "maria");
        result = callAction(
            controllers.routes.ref.CAlumno.registrarA(),
            fakeRequest().withFormUrlEncodedBody(form)
        );
        //System.out.println(session(result));
        assertEquals(200, status(result));
        a = Alumno.find.where().eq("correoElectronico", "maria1@gmail.com").findUnique();
        assert(a != null);
        assertEquals(a.getNombre(), "Maria");
        assertEquals(a.getApellidoPaterno(), "Toyos");
        assert(a.getApellidoMaterno() == null);
        assertEquals(a.getContrasena(), "maria");
        assertEquals(a.getCorreoElectronico(), "maria1@gmail.com");
    }


    @Test
    public void registerFailed() {
        Map<String,String> form  = new HashMap();
        form.put("nombre", "");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "maria@gmail.com");
        form.put("contrasena", "maria");
        form.put("confContrasena", "maria");
        Result result = callAction(
            controllers.routes.ref.CAlumno.registrarA(),
            fakeRequest().withFormUrlEncodedBody(form)
        );
        assertEquals(400, status(result));
        assert(contentAsString(result).contains("Este campo es requerido."));
        assert(contentAsString(result).contains("Este correo ya se encuentra registrado."));

        form  = new HashMap();
        form.put("nombre", "Maria");
        form.put("apellidoPaterno", "");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "maria@gmail.com");
        form.put("contrasena", "maria");
        form.put("confContrasena", "maria");
        result = callAction(
            controllers.routes.ref.CAlumno.registrarA(),
            fakeRequest().withFormUrlEncodedBody(form)
        );
        assertEquals(400, status(result));
        assert(contentAsString(result).contains("Este campo es requerido."));
        assert(contentAsString(result).contains("Este correo ya se encuentra registrado."));

        form  = new HashMap();
        form.put("nombre", "Maria");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "");
        form.put("contrasena", "maria");
        form.put("confContrasena", "maria");
        result = callAction(
            controllers.routes.ref.CAlumno.registrarA(),
            fakeRequest().withFormUrlEncodedBody(form)
        );
        assertEquals(400, status(result));
        assert(contentAsString(result).contains("Este campo es requerido."));

        form  = new HashMap();
        form.put("nombre", "Maria");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "maria@gmail.com");
        form.put("contrasena", "");
        form.put("confContrasena", "maria");
        result = callAction(
            controllers.routes.ref.CAlumno.registrarA(),
            fakeRequest().withFormUrlEncodedBody(form)
        );
        assertEquals(400, status(result));
        assert(contentAsString(result).contains("Este campo es requerido."));
        assert(contentAsString(result).contains("Este correo ya se encuentra registrado."));

        form  = new HashMap();
        form.put("nombre", "Maria");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "maria@gmail.com");
        form.put("contrasena", "bla");
        form.put("confContrasena", "maria");
        result = callAction(
            controllers.routes.ref.CAlumno.registrarA(),
            fakeRequest().withFormUrlEncodedBody(form)
        );
        assertEquals(400, status(result));
        assert(contentAsString(result).contains("Las contraseñas no coinciden."));
        assert(contentAsString(result).contains("Este correo ya se encuentra registrado."));

        form  = new HashMap();
        form.put("nombre", "Maria");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "maria@gmail.com");
        form.put("contrasena", "maria");
        form.put("confContrasena", "bla");
        result = callAction(
            controllers.routes.ref.CAlumno.registrarA(),
            fakeRequest().withFormUrlEncodedBody(form)
        );
        assertEquals(400, status(result));
        assert(contentAsString(result).contains("Las contraseñas no coinciden."));
        assert(contentAsString(result).contains("Este correo ya se encuentra registrado."));

        form  = new HashMap();
        form.put("nombre", "Maria");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "mariagmail.com");
        form.put("contrasena", "maria");
        form.put("confContrasena", "maria");
        result = callAction(
            controllers.routes.ref.CAlumno.registrarA(),
            fakeRequest().withFormUrlEncodedBody(form)
        );
        assertEquals(400, status(result));
        assert(contentAsString(result).contains("Correo electrónico inválido."));
    }

}