import play.*;
import play.libs.*;

import java.util.*;

import com.avaje.ebean.*;

import models.*;

/**
 * Clase que se se encarga de ejecutar acciones dados eventos del servidor.
 */
public class Global extends GlobalSettings {


    /**
     * Método que se ejecuta al iniciar la aplicación
     * @param app la aplicación iniciada
     */
    public void onStart(Application app) {
        Logger.info("Application has started");
        InitialData.insert(app);
    }


    /**
     * Clase InitialData que se encarga de inyectar en la base de datos información inicial
     */
    static class InitialData {

        /**
         * Método que carga la información inicial de la base de datos desde un archivo y la inyecta a la BD
         * @param app la aplicación iniciada
         */
        public static void insert(Application app) {
            if(Ebean.find(Alumno.class).findRowCount() == 0) {
                @SuppressWarnings("unchecked")
                Map<String, List<Object>> all = (Map<String, List<Object>>)Yaml.load("initial-data.yml");
                Ebean.save(all.get("alumnos"));
                Ebean.save(all.get("profesores"));
                Ebean.save(all.get("cursos"));
                Ebean.save(all.get("horarios"));
            }
        }
    }


    /**
     * Método que se ejecuta al finalizar la aplicación
     * @param app la aplicación iniciada
     */
    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }

}
