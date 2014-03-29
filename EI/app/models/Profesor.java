package models;
import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;

@Entity
public class Profesor extends Model {

    @Id
    @GeneratedValue
    public Integer id;
    
    @Column(columnDefinition = "tinyint(1) default 1")
    public boolean activo;

    @Constraints.Required
    @Column(nullable = false)
    public String nombre;
    
    @Constraints.Required
    @Column(nullable = false)
    public String apellidoPaterno;

    
    public String apellidoMaterno;

    @Constraints.Required
    @Column(nullable = false, unique = true)
    public String correoElectronico;

    @Constraints.Required
    @Column(nullable = false)
    public String contrasena;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Curso> cursos;

    public static Finder<Integer,Profesor> find = new Finder<Integer,Profesor>(
        Integer.class, Profesor.class
    ); 

    public String getNombre() {
        return nombre;
    }

    public String getContrasena() {
        return contrasena;
    }
}