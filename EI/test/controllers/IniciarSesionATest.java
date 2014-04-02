package controllers;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

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

public class IniciarSesionATest extends WithApplication {
    
    @Before
    public void setUp() {
        //start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
        //Ebean.save((List) Yaml.load("test-data.yml"));
        EbeanServer server = Ebean.getServer("default");
        ServerConfig config = new ServerConfig();
        DdlGenerator ddl = new DdlGenerator();
        ddl.setup((SpiEbeanServer) server, new MySqlPlatform(), config);
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
    }


    @Test
    public void authenticateSuccess() {
        Result result = callAction(
            controllers.routes.ref.CAlumno.iniciarSesionA(),
            fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                "correoElectronico", "luis@gmail.com",
                "password", "luis"))
        );
        assertEquals(302, status(result));
        assertEquals("luis@gmail.com", session(result).get("email"));
        assertEquals("alumno", session(result).get("usuario"));
    }


}