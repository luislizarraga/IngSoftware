package controllers;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

import models.Profesor;
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
 * Pruebas unitarias para mostrar informacion del profesor
 * @extends WithApplication
 */
@SuppressWarnings("unchecked")
public class MostrarProfesorTest extends WithApplication {
    

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
        Ebean.save(new Profesor("luis","lizarraga","santos","luis@gmail.com","luis"));
    }


    /**
     * Verifica que la informacion de profesor y los cursos se muestren correctamente
     */
    @Test
    public void mostrarSuccess() {
        Map<String,String> form  = new HashMap();
        form.put("idProf", "1");
        Result result = callAction(
            base.routes.ref.CBase.mostrarProfesor(),
            fakeRequest().withFormUrlEncodedBody(form)
        );
        assertEquals(200, status(result));
        assert(contentAsString(result).contains("luis"));
        assert(contentAsString(result).contains("lizarraga"));
        assert(contentAsString(result).contains("santos"));
        assert(contentAsString(result).contains("luis@gmail.com"));
        assert(contentAsString(result).contains("Este profesor aún no tiene cursos registrados"));
    }


    /**
     * Verifica que no se pueda mostrar un profesor no encontrado
     */
    @Test
    public void mostrarFailed() {
        Map<String,String> form  = new HashMap();
        form.put("idProf", "2342345");
        Result result = callAction(
            base.routes.ref.CBase.mostrarProfesor(),
            fakeRequest().withFormUrlEncodedBody(form)
        );
        assertEquals(200, status(result));
        assert(contentAsString(result).contains("Profesor no encontrado !"));
    }
}