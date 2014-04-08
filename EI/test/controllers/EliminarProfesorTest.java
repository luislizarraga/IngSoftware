package controllers;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

import models.Profesor;
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

public class EliminarProfesorTest extends WithApplication {
    
    @Before
    public void setUp() {
        EbeanServer server = Ebean.getServer("default");
        ServerConfig config = new ServerConfig();
        DdlGenerator ddl = new DdlGenerator();
        ddl.setup((SpiEbeanServer) server, new MySqlPlatform(), config);
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
        Ebean.save(new Profesor("Jorge","Ruìz","Ramírez","jorge@gmail.com","jorge"));
    }

    
    public Result login() {
        return callAction(
                controllers.routes.ref.CProfesor.iniciarSesionP(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                    "correoElectronico", "jorge@gmail.com",
                    "contrasena", "jorge"))
            );
    }



    @Test
    public void deleteSuccess() {
        Result result = login();
        assertEquals(200, status(result));
        final Cookie playSession = play.test.Helpers.cookie("PLAY_SESSION",result);

        result = callAction(
            controllers.routes.ref.CProfesor.eliminaRegistroP(),
            fakeRequest().withCookies(playSession)
        );
        assertEquals(303, status(result));
        assert(Profesor.find.where().eq("correoElectronico", "jorge@gmail.com").findUnique() == null);
    }   


    @Test
    public void deleteFailed() {
        Result result = callAction(
            controllers.routes.ref.CProfesor.eliminaRegistroP()
            );
        assertEquals(303, status(result));
        System.out.println(result);
    }
	
}