package models;
import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;

@Entity
public class Curso extends Model {

  @Id
  @GeneratedValue
  public Integer id;
  
  public boolean aprobado;

  public String constancia;
  
  public Integer calificacion;

  @Constraints.Required
  public String nivel;

  @Formats.DateTime(pattern="dd/MM/yyyy kk:mm:ss")
  public Date horario = new Date();

  @OneToOne(cascade = CascadeType.ALL)
  public Alumno alumno;

  @Constraints.Required
  @OneToOne(cascade = CascadeType.ALL)
  public Profesor profesor;

  public static Finder<Integer,Curso> find = new Finder<Integer,Curso>(
    Integer.class, Curso.class
  ); 

  /*public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<ValidationError>();
    if (Alumno.byCorreoElectronico(correoElectronico) == "aa") {
        errors.add(new ValidationError("correoElectronico", "This e-mail is already registered."));
    }
    return errors.isEmpty() ? null : errors;
}*/

}