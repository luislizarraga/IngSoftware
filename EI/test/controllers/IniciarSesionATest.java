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


/**
 * Pruebas unitarias para iniciar sesion como alumno
 * @extends WithApplication
 */
public class IniciarSesionATest extends WithApplication {
    

    /**
     * Crea un servidor, una base de datos temporal y da de alta un alumno 
     * para las pruebas
     */
    @Before
    public void setUp() {
        EbeanServer server = Ebean.getServer("default");
        ServerConfig config = new ServerConfig();
        DdlGenerator ddl = new DdlGenerator();
        ddl.setup((SpiEbeanServer) server, new MySqlPlatform(), config);
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
        Ebean.save(new Alumno("luis","lizarraga","santos","luis@gmail.com","luis"));
    }


    /**
     * Verifica que el usuario pueda iniciar sesión sin problema, asumiendo
     * que esta registrado en la base de datos
     */
    @Test
    public void authenticateSuccess() {
        Result result = callAction(
            controllers.routes.ref.CAlumno.iniciarSesionA(),
            fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                "correoElectronico", "luis@gmail.com",
                "contrasena", "luis"))
        );
        assertEquals(200, status(result));
        assertEquals("luis@gmail.com", session(result).get("correoElectronico"));
        assertEquals("alumno", session(result).get("usuario"));
    }


    /**
     * Verifica que se rechaze el inicio de sesion del usuario debido a que 
     * los campos proporcionados no son correctos
     */
    @Test
    public void authenticateFailed() {
        Result result = callAction(
            controllers.routes.ref.CAlumno.iniciarSesionA(),
            fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                "correoElectronico", "luis@gmail.com",
                "contrasena", "bla"))
        );
        assertEquals(401, status(result));
        assert(contentAsString(result).contains("Contraseña incorrecta."));
        
        result = callAction(
            controllers.routes.ref.CAlumno.iniciarSesionA(),
            fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                "correoElectronico", "luis@gmail.com",
                "contrasena", ""))
        );
        assertEquals(401, status(result));
        assert(contentAsString(result).contains("Este campo es requerido."));
        
        result = callAction(
            controllers.routes.ref.CAlumno.iniciarSesionA(),
            fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                "correoElectronico", "",
                "contrasena", "bla"))
        );
        assertEquals(401, status(result));
        assert(contentAsString(result).contains("Este campo es requerido."));
        
        result = callAction(
            controllers.routes.ref.CAlumno.iniciarSesionA(),
            fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                "correoElectronico", "adsfadsf",
                "contrasena", "bla"))
        );
        assertEquals(401, status(result));
        assert(contentAsString(result).contains("Usuario inexistente."));

        result = callAction(
            controllers.routes.ref.CAlumno.iniciarSesionA(),
            fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                "correoElectronico", "",
                "contrasena", ""))
        );
        assertEquals(401, status(result));
        assert(contentAsString(result).contains("Este campo es requerido."));
    }

}