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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profesor")
    public List<Curso> cursos;


    public static Finder<Integer,Profesor> find = new Finder<Integer,Profesor>(
        Integer.class, Profesor.class
    ); 


    /**
     * [Profesor description]
     * @param  nombre            [description]
     * @param  apellidoPaterno   [description]
     * @param  apellidoMaterno   [description]
     * @param  correoElectronico [description]
     * @param  contrasena        [description]
     */
    public Profesor(String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico, String contrasena) {
        this.nombre            = nombre;
        this.apellidoPaterno   = apellidoPaterno;
        this.apellidoMaterno   = apellidoMaterno;
        this.correoElectronico = correoElectronico;
        this.contrasena        = contrasena;
    }


    /**
     * [Profesor description]
     */
    public Profesor(){}


    /**
     * [getId description]
     * @return [description]
     */
    public Integer getId() {
        return id;
    }


    /**
     * [getNombre description]
     * @return [description]
     */
    public String getNombre() {
        return nombre;
    }


    /**
     * [getContrasena description]
     * @return [description]
     */
    public String getContrasena() {
        return contrasena;
    }


    /**
     * [getApellidoPaterno description]
     * @return [description]
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }


    /**
     * [getApellidoMaterno description]
     * @return [description]
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }


    /**
     * [getCorreoElectronico description]
     * @return [description]
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }


    /**
     * [getConstancia description]
     * @return [description]
     */
    public String getConstancia() {
        return constancia;
    }


    /**
     * [getVideo description]
     * @return [description]
     */
    public String getVideo() {
        return video;
    }


    /**
     * [setNombre description]
     * @param nombre [description]
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    /**
     * [setApellidoPaterno description]
     * @param apellidoPaterno [description]
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }


    /**
     * [setApellidoMaterno description]
     * @param apellidoMaterno [description]
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }


    /**
     * [setCorreoElectronico description]
     * @param correoElectronico [description]
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }


    /**
     * [setContrasena description]
     * @param contrasena [description]
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }


    /**
     * [setConstancia description]
     * @param constancia [description]
     */
    public void setConstancia(String constancia) {
        this.constancia = constancia;
    }


    /**
     * [setVideo description]
     * @param video [description]
     */
    public void setVideo(String video) {
        this.video = video;
    }


    /**
     * [validate description]
     * @return [description]
     */
    /**
     * [validate description]
     * @return [description]
     */
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<ValidationError>();
        Profesor p = Profesor.find.where().eq("correoElectronico", correoElectronico).findUnique();
        System.out.println(p);
        if (p != null) {
            errors.add(new ValidationError("correoElectronico", "Este correo ya se encuentra registrado."));
        }
        //System.out.println(correoElectronico.matches("[a-zA-Z0-9-\\+]+@[a-zA-Z0-9-\\+]+"));
        if (!correoElectronico.matches("[a-zA-Z0-9-\\+]+@[a-zA-Z0-9-\\+]+"))
            errors.add(new ValidationError("correoElectronico", "Correo Electrónico inválido."));

        return errors.isEmpty() ? null : errors;
    }



}
