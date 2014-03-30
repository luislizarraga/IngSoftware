package models;
import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;

@Entity
public class Alumno extends Model {

    @Id
    @GeneratedValue
    public Integer id;
    
    @Column(columnDefinition = "tinyint(1) default 1")
    public boolean activo = true;

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


    public static Finder<Integer,Alumno> find = new Finder<Integer,Alumno>(
        Integer.class, Alumno.class
    ); 


    public Alumno(String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico, String contrasena) {
        this.nombre            = nombre;
        this.apellidoPaterno   = apellidoPaterno;
        this.apellidoMaterno   = apellidoMaterno;
        this.correoElectronico = correoElectronico;
        this.contrasena        = contrasena;
    }


    public Alumno(){}


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


    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<ValidationError>();
        Alumno a = Alumno.find.where().eq("correoElectronico", correoElectronico).findUnique();
        System.out.println(a);
        if (a != null) {
            errors.add(new ValidationError("correoElectronico", "Este correo ya se encuentra registrado."));
        }
        //System.out.println(correoElectronico.matches("[a-zA-Z0-9-\\+]+@[a-zA-Z0-9-\\+]+"));
        if (!correoElectronico.matches("[a-zA-Z0-9-\\+]+@[a-zA-Z0-9-\\+]+"))
            errors.add(new ValidationError("correoElectronico", "Correo Electrónico inválido."));

        return errors.isEmpty() ? null : errors;
    }


}