package models;
import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Alumno extends Model {

  @Id
  @GeneratedValue
  public Integer id;
  
  @Constraints.Required
  public String nombre;
  
  public boolean activo;
  
  @Constraints.Required
  public String apellidoPaterno;

  @Constraints.Required
  public String apellidoMaterno;

  @Constraints.Required
  public String correoElectronico;

  @Constraints.Required
  public String contrasena;

  public static Finder<Integer,Alumno> find = new Finder<Integer,Alumno>(
    Integer.class, Alumno.class
  ); 

  /*public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<ValidationError>();
    if (Alumno.byCorreoElectronico(correoElectronico) == "aa") {
        errors.add(new ValidationError("correoElectronico", "This e-mail is already registered."));
    }
    return errors.isEmpty() ? null : errors;
}*/

}