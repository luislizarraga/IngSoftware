package controllers;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

import models.Alumno;
import play.mvc.*;
import play.libs.*;
import play.test.*;
import play.mvc.Http.Cookie;
import static play.test.Helpers.*;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.MySqlPlatform;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import com.google.common.collect.ImmutableMap;

/**
 * Pruebas unitarias para Cerrar Sesion Alumno
 * @extends WithApplication
 */
public class CerrarSesionATest extends WithApplication {


    /**
     * Crea un servidor, una base de datos temporal y da de alta un profesor 
     * para las pruebas
     */
    @Before
    public void setUp() {
        EbeanServer server = Ebean.getServer("default");
        ServerConfig config = new ServerConfig();
        DdlGenerator ddl = new DdlGenerator();
        ddl.setup((SpiEbeanServer) server, new MySqlPlatform(), config);
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
        Ebean.save(new Alumno("Jorge","Ruìz","Ramírez","jorge@gmail.com","jorge"));
    }


    /**
     * Trata de iniciar con un usuario registrado
     * @return Result Resultado del inicio de sesión
     */
    public Result login() {
        return callAction(
                controllers.routes.ref.CAlumno.iniciarSesionA(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                    "correoElectronico", "jorge@gmail.com",
                    "contrasena", "jorge"))
            );
    }


    /**
     * Trata de cerrar sesion
     */
    @Test
    public void logOutSuccess() {
        Result result = login();
        assertEquals(200, status(result));
        assertEquals("jorge@gmail.com", session(result).get("correoElectronico"));
        final Cookie playSession = play.test.Helpers.cookie("PLAY_SESSION",result);
        result = callAction(
            controllers.routes.ref.CAlumno.cerrarSesionA(),
            fakeRequest().withCookies(playSession)
        );
        assertEquals(303, status(result));
        assert(session(result).isEmpty());
    }

}