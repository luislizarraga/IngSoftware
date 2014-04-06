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

public class eliminacionAlumnoTest extends WithApplication {
    
    @Before
    public void setUp() {
        EbeanServer server = Ebean.getServer("default");
        ServerConfig config = new ServerConfig();
        DdlGenerator ddl = new DdlGenerator();
        ddl.setup((SpiEbeanServer) server, new MySqlPlatform(), config);
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
        Ebean.save(new Alumno("ana","Martines","Perez","ana@gmail.com","ana"));
        session().clear();                                                          // Se borra toda la información de la sesión
        session("correoElectronico", "ana@gmail.com");    // Se agrega el correoElectronico a la sesion
        session("usuario", "alumno");
    }


@Test
    public void deleteSuccess() {
        Result result = callAction(
            controllers.routes.ref.CAlumno.eliminarRegistroA(),
            fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                "correoElectronico", "ana@gmail.com",
                "contrasena", "ana"))
        );
        assertEquals(200, status(result));
    }	
}



