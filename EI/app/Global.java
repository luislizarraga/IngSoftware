import play.*;
import play.libs.*;

import java.util.*;

import com.avaje.ebean.*;

import models.*;

public class Global extends GlobalSettings {

    public void onStart(Application app) {
        Logger.info("Application has started");
        InitialData.insert(app);
    }


    static class InitialData {
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

    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }

}
