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
 * Pruebas unitarias para eliminar alumno
 * @extends WithApplication
 */
public class EliminarAlumnoTest extends WithApplication {
    

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
        Ebean.save(new Alumno("Ana","Martinez","Perez","ana@gmail.com","ana"));
    }


    /**
     * Trata de iniciar con un usuario registrado
     * @return Result Resultado del inicio de sesión
     */
    public Result login() {
        return callAction(
                controllers.routes.ref.CAlumno.iniciarSesionA(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                    "correoElectronico", "ana@gmail.com",
                    "contrasena", "ana"))
            );
    }


    /**
     * Elimina a un alumno para despues verificar que éste ya no exista en la
     * base de datos
     */
    @Test
    public void deleteSuccess() {
        Result result = login();
        assertEquals(200, status(result));
        final Cookie playSession = play.test.Helpers.cookie("PLAY_SESSION",result);

        result = callAction(
            controllers.routes.ref.CAlumno.eliminaRegistroA(),
            fakeRequest().withCookies(playSession)
        );
        assertEquals(303, status(result));
        assert(Alumno.find.where().eq("correoElectronico", "ana@gmail.com").findUnique() == null);
    }	


    /**
     * Trata de eliminar a un alumno que ya no esta en la base de datos
     */
    @Test
    public void deleteFailed() {
        Result result = callAction(
            controllers.routes.ref.CAlumno.eliminaRegistroA()
            );
        assertEquals(303, status(result));
    }
}



