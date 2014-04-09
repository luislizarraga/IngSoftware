package models;
import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;

/**
 * Clase Profesor que modela a un profesor en la base de datos
 */
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

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "profesor")
    public List<Curso> cursos;


    public static Finder<Integer,Profesor> find = new Finder<Integer,Profesor>(
        Integer.class, Profesor.class
    ); 


    /**
     * Constructor de Profesor que recibe 5 parámetros.
     * @param  nombre            el nombre para el profesor
     * @param  apellidoPaterno   el apellido paterno para el profesor
     * @param  apellidoMaterno   el apellido materno para el profesor
     * @param  correoElectronico el correo electrónico para el profesor
     * @param  contrasena        la contraseña para el profesor
     */
    public Profesor(String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico, String contrasena) {
        this.nombre            = nombre;
        this.apellidoPaterno   = apellidoPaterno;
        this.apellidoMaterno   = apellidoMaterno;
        this.correoElectronico = correoElectronico;
        this.contrasena        = contrasena;
    }


    /**
     * Constructor de profesor que no recibe parámetros.
     */
    public Profesor(){}


    /**
     * Metodo get
     * @return Integer El identificador del profesor
     */
    public Integer getId() {
        return id;
    }


    /**
     * Metodo get
     * @return String El nombre del profesor
     */
    public String getNombre() {
        return nombre;
    }


    /**
     * Metodo get
     * @return String La contrasena del profesor
     */
    public String getContrasena() {
        return contrasena;
    }


    /**
     * Metodo get
     * @return String El apellido paterno del profesor
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }


    /**
     * Metodo get
     * @return String El apellido materno del profesor
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }


    /**
     * Metodo get
     * @return String El correo electronico del profesor
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }


    /**
     * Metodo get
     * @return String La direccion donde esta guardada la constancia del profesor
     */
    public String getConstancia() {
        return constancia;
    }


    /**
     * Metodo get
     * @return String La direccion donde esta guardada la constancia del profesor
     */
    public String getVideo() {
        return video;
    }


    /**
     * Metodo get
     * @return List<Curso> La lista de cursos del profesor
     */
    public List<Curso> getCursos() {
        return cursos;
    }


    /**
     * Metodo set
     * @param nombre El nuevo nombre del profesor
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    /**
     * Metodo set
     * @param apellidoPaterno El nuevo apellido paterno del profesor
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }


    /**
     * Metodo set
     * @param apellidoMaterno El nuevo apellido materno del profesor
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }


    /**
     * Metodo set
     * @param correoElectronico El nuevo correo electronico del profesor
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }


    /**
     * Metodo set
     * @param contrasena La nueva contrasena del profesor
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }


    /**
     * Metodo set
     * @param constancia La nueva constancia del profesor
     */
    public void setConstancia(String constancia) {
        this.constancia = constancia;
    }


    /**
     * Metodo set
     * @param video El nuevo video del profesor
     */
    public void setVideo(String video) {
        this.video = video;
    }


    /**
     * Método que valida si la información introducida al formulario es correcta.
     * @return una lista de errores de validación
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
