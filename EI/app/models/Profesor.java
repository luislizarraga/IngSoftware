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

    public String video;

    public String constancia;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Curso> cursos;


    public static Finder<Integer,Profesor> find = new Finder<Integer,Profesor>(
        Integer.class, Profesor.class
    ); 


    public Profesor(String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico, String contrasena) {
        this.nombre            = nombre;
        this.apellidoPaterno   = apellidoPaterno;
        this.apellidoMaterno   = apellidoMaterno;
        this.correoElectronico = correoElectronico;
        this.contrasena        = contrasena;
    }


    public Profesor(){}


    public String getNombre() {
        return nombre;
    }


    public String getContrasena() {
        return contrasena;
    }


    public String getApellidoPaterno() {
        return apellidoPaterno;
    }


    public String getApellidoMaterno() {
        return apellidoMaterno;
    }


    public String getCorreoElectronico() {
        return correoElectronico;
    }


    public String getConstancia() {
        return constancia;
    }


    public String getVideo() {
        return video;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }


    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }


    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }


    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setConstancia(String constancia) {
        this.constancia = constancia;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    
}