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


/**
 * Pruebas unitarias para modificar informacion del profesor
 * @extends WithApplication
 */
@SuppressWarnings("unchecked")
public class ModificarInformacionPTest extends WithApplication {


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
        Ebean.save(new Profesor("Maria","Toyos","Swanson","maria@gmail.com","maria"));
    }


    /**
     * Trata de iniciar con un usuario registrado
     * @return Result Resultado del inicio de sesión
     */
    public Result login() {
        return callAction(
                controllers.routes.ref.CProfesor.iniciarSesionP(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                    "correoElectronico", "maria@gmail.com",
                    "contrasena", "maria"))
            );
    }


    /**
     * Verifica que una modificacion a los datos del usuario sea exitosa siempre
     * y cuando todos los campos sean correctos
     */
    @Test
    public void modifySuccess() {
        Result result = login();
        assertEquals(200, status(result));
        final Cookie playSession = play.test.Helpers.cookie("PLAY_SESSION",result);
        Map<String,String> form  = new HashMap();
        form.put("nombre", "Maria Ana");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "maria@gmail.com");
        form.put("contrasenaNueva", "");
        form.put("confContrasena", "");
        result = callAction(
            controllers.routes.ref.CProfesor.modificarInformacionP(),
            fakeRequest().withCookies(playSession).withFormUrlEncodedBody(form)
        );
        assertEquals(200, status(result));
        Profesor a = Profesor.find.where().eq("correoElectronico", "maria@gmail.com").findUnique();
        assert(a != null);
        assertEquals("Maria Ana", a.getNombre());

        form  = new HashMap();
        form.put("nombre", "Maria Ana");
        form.put("apellidoPaterno", "Elizalde");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "maria@gmail.com");
        form.put("contrasenaNueva", "");
        form.put("confContrasena", "");
        result = callAction(
            controllers.routes.ref.CProfesor.modificarInformacionP(),
            fakeRequest().withCookies(playSession).withFormUrlEncodedBody(form)
        );
        assertEquals(200, status(result));
        a = Profesor.find.where().eq("correoElectronico", "maria@gmail.com").findUnique();
        assert(a != null);
        assertEquals("Elizalde", a.getApellidoPaterno());

        form  = new HashMap();
        form.put("nombre", "Maria Ana");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "Potrillo");
        form.put("correoElectronico", "maria@gmail.com");
        form.put("contrasenaNueva", "");
        form.put("confContrasena", "");
        result = callAction(
            controllers.routes.ref.CProfesor.modificarInformacionP(),
            fakeRequest().withCookies(playSession).withFormUrlEncodedBody(form)
        );
        assertEquals(200, status(result));
        a = Profesor.find.where().eq("correoElectronico", "maria@gmail.com").findUnique();
        assert(a != null);
        assertEquals("Potrillo", a.getApellidoMaterno());

        form  = new HashMap();
        form.put("nombre", "Maria Ana");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "ana@gmail.com");
        form.put("contrasenaNueva", "");
        form.put("confContrasena", "");
        result = callAction(
            controllers.routes.ref.CProfesor.modificarInformacionP(),
            fakeRequest().withCookies(playSession).withFormUrlEncodedBody(form)
        );
        assertEquals(200, status(result));
        a = Profesor.find.where().eq("correoElectronico", "maria@gmail.com").findUnique();
        assert(a == null);
        a = Profesor.find.where().eq("correoElectronico", "ana@gmail.com").findUnique();
        assert(a != null);
        final Cookie playSession2 = play.test.Helpers.cookie("PLAY_SESSION",result);

        form  = new HashMap();
        form.put("nombre", "Maria Ana");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "ana@gmail.com");
        form.put("contrasenaNueva", "bla");
        form.put("confContrasena", "bla");
        result = callAction(
            controllers.routes.ref.CProfesor.modificarInformacionP(),
            fakeRequest().withCookies(playSession2).withFormUrlEncodedBody(form)
        );
        assertEquals(200, status(result));
        a = Profesor.find.where().eq("correoElectronico", "ana@gmail.com").findUnique();
        assert(a != null);
        assertEquals("bla", a.getContrasena());
    }


    /**
     * Verifica que una modificacion a los datos del usuario falle cuando al menos
     * un campo sea incorrecto
     */
    @Test
    public void modifyFailed() {
        Result result = login();
        assertEquals(200, status(result));
        final Cookie playSession = play.test.Helpers.cookie("PLAY_SESSION",result);
        Map<String,String> form  = new HashMap();
        form.put("nombre", "");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "maria@gmail.com");
        form.put("contrasenaNueva", "");
        form.put("confContrasena", "");
        result = callAction(
            controllers.routes.ref.CProfesor.modificarInformacionP(),
            fakeRequest().withCookies(playSession).withFormUrlEncodedBody(form)
        );
        assertEquals(400, status(result));
        assert(contentAsString(result).contains("Este campo es requerido."));

        form  = new HashMap();
        form.put("nombre", "Maria Ana");
        form.put("apellidoPaterno", "");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "maria@gmail.com");
        form.put("contrasenaNueva", "");
        form.put("confContrasena", "");
        result = callAction(
            controllers.routes.ref.CProfesor.modificarInformacionP(),
            fakeRequest().withCookies(playSession).withFormUrlEncodedBody(form)
        );
        assertEquals(400, status(result));
        assert(contentAsString(result).contains("Este campo es requerido."));

        form  = new HashMap();
        form.put("nombre", "Maria Ana");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "mariagmail.com");
        form.put("contrasenaNueva", "");
        form.put("confContrasena", "");
        result = callAction(
            controllers.routes.ref.CProfesor.modificarInformacionP(),
            fakeRequest().withCookies(playSession).withFormUrlEncodedBody(form)
        );
        assertEquals(400, status(result));
        assert(contentAsString(result).contains("Correo electrónico inválido."));

        form  = new HashMap();
        form.put("nombre", "Maria");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "maria@gmail.com");
        form.put("contrasenaNueva", "maria");
        form.put("confContrasena", "maria");
        result = callAction(
            controllers.routes.ref.CProfesor.modificarInformacionP(),
            fakeRequest().withCookies(playSession).withFormUrlEncodedBody(form)
        );
        assertEquals(400, status(result));
        assert(contentAsString(result).contains("Este correo ya se encuentra registrado."));

        form  = new HashMap();
        form.put("nombre", "Maria Ana");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "maria@gmail.com");
        form.put("contrasenaNueva", "bla2");
        form.put("confContrasena", "");
        result = callAction(
            controllers.routes.ref.CProfesor.modificarInformacionP(),
            fakeRequest().withCookies(playSession).withFormUrlEncodedBody(form)
        );
        assertEquals(400, status(result));
        assert(contentAsString(result).contains("Las contraseñas no coinciden."));

        form  = new HashMap();
        form.put("nombre", "Maria Ana");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "maria@gmail.com");
        form.put("contrasenaNueva", "bla2");
        form.put("confContrasena", "fdsdfgsdfg");
        result = callAction(
            controllers.routes.ref.CProfesor.modificarInformacionP(),
            fakeRequest().withCookies(playSession).withFormUrlEncodedBody(form)
        );
        assertEquals(400, status(result));
        assert(contentAsString(result).contains("Las contraseñas no coinciden."));

        form  = new HashMap();
        form.put("nombre", "Maria Ana345234");
        form.put("apellidoPaterno", "Toyos");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "maria@gmail.com");
        form.put("contrasenaNueva", "");
        form.put("confContrasena", "");
        result = callAction(
            controllers.routes.ref.CProfesor.modificarInformacionP(),
            fakeRequest().withCookies(playSession).withFormUrlEncodedBody(form)
        );
        assertEquals(400, status(result));
        assert(contentAsString(result).contains("Nombre inválido."));

        form  = new HashMap();
        form.put("nombre", "Maria Ana");
        form.put("apellidoPaterno", "Toyos34345");
        form.put("apellidoMaterno", "Swanson");
        form.put("correoElectronico", "maria@gmail.com");
        form.put("contrasenaNueva", "");
        form.put("confContrasena", "");
        result = callAction(
            controllers.routes.ref.CProfesor.modificarInformacionP(),
            fakeRequest().withCookies(playSession).withFormUrlEncodedBody(form)
        );
        assertEquals(400, status(result));
        assert(contentAsString(result).contains("Apellido paterno inválido."));
    }

}