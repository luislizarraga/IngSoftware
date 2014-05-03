package models;
import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;

/**
 * Clase Alumno que modela a un alumno en la base de datos
 */
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alumno")
    public List<Curso> cursos;

    public static Finder<Integer,Alumno> find = new Finder<Integer,Alumno>(
        Integer.class, Alumno.class
    ); 


    /**
     * Constructor de Alumno que recibe 5 parámetros.
     * @param  nombre            el nombre del alumno
     * @param  apellidoPaterno   el apellido paterno del alumno
     * @param  apellidoMaterno   el apellido materno del alumno
     * @param  correoElectronico el correo electrónico del alumno
     * @param  contrasena        la contraseña del alumno
     */
    public Alumno(String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico, String contrasena) {
        this.nombre            = nombre;
        this.apellidoPaterno   = apellidoPaterno;
        this.apellidoMaterno   = apellidoMaterno;
        this.correoElectronico = correoElectronico;
        this.contrasena        = contrasena;
    }


    /**
     * Constructor de Alumno que no recibe parámetros.
     */
    public Alumno(){}


    /**
     * Metodo de acceso
     * @return Integer El id del alumno
     */
    public Integer getId() {
        return id;
    }


    /**
     * Metodo de acceso
     * @return String El nombre del alumno
     */
    public String getNombre() {
        return nombre;
    }


    /**
     * Metodo de acceso
     * @return String La contrasena del alumno
     */
    public String getContrasena() {
        return contrasena;
    }


    /**
     * Metodo de acceso
     * @return String El apellido paterno del alumno
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }


    /**
     * Metodo de acceso
     * @return String El apellido materno del alumno
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }


    /**
     * Metodo de acceso
     * @return String El correo electronico del alumno
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }


    public List<Curso> getCursos(){
        return cursos;
    }

    /**
     * Metodo set
     * @param nombre Nuevo valor nombre del alumno
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    /**
     * Metodo set
     * @param apellidoPaterno Nuevo valor de apellido paterno del alumno
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }


    /**
     * Metodo set
     * @param apellidoMaterno Nuevo valor de apellido materno del alumno
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }


    /**
     * Metodo set
     * @param correoElectronico Nuevo valor de correo electronico del alumno
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }


    /**
     * Metodo set
     * @param contrasena Nuevo valor de la contrasena del alumno
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }


    public void addCurso(Curso curso) {
        cursos.add(curso);
    }


    /**
     * Método que valida si la información introducida al formulario es correcta.
     * @return una lista de errores de validación
     */
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<ValidationError>();
        Alumno a = Alumno.find.where().eq("correoElectronico", correoElectronico).findUnique();
        System.out.println(a);
        if (a != null) {
            errors.add(new ValidationError("correoElectronico", "Este correo ya se encuentra registrado."));
        }
        if (!correoElectronico.matches("[a-zA-Z0-9-\\+]+@[a-zA-Z0-9-\\+]+"))
            errors.add(new ValidationError("correoElectronico", "Correo Electrónico inválido."));

        return errors.isEmpty() ? null : errors;
    }


}