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
    @Column(nullable = false)
    public String nivel;

    @ManyToOne()
    public Alumno alumno;

    @Constraints.Required
    @ManyToOne(optional = false)
    public Profesor profesor;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "curso")
    public List<Horario> horarios;

    public static Finder<Integer,Curso> find = new Finder<Integer,Curso>(
        Integer.class, Curso.class
    ); 


    /**
     * [Curso description]
     * @param  aprobado     [description]
     * @param  constancia   [description]
     * @param  calificacion [description]
     * @param  nivel        [description]
     * @param  alumno       [description]
     */
    public Curso(boolean aprobado, String constancia, Integer calificacion, String nivel, Alumno alumno, Profesor profesor) {
        this.aprobado     = aprobado;
        this.constancia   = constancia;
        this.calificacion = calificacion;
        this.nivel        = nivel;
        this.alumno       = alumno;
        this.profesor     = profesor;
    }


    /**
     * [Curso description]
     * @param  aprobado     [description]
     * @param  constancia   [description]
     * @param  calificacion [description]
     * @param  nivel        [description]
     */
    public Curso(boolean aprobado, String constancia, Integer calificacion, String nivel, Profesor profesor) {
        this(aprobado, constancia, calificacion, nivel, null, profesor);
    }


    /**
     * [Curso description]
     */
    public Curso() {}


    /**
     * [getId description]
     * @return [description]
     */
    public Integer getId() {
        return id;
    }


    /**
     * [getProfesor description]
     * @return [description]
     */
    public Profesor getProfesor() {
        return profesor;
    }


    /**
     * [getAlumno description]
     * @return [description]
     */
    public Alumno getAlumno() {
        return alumno;
    }


    /**
     * [getNivel description]
     * @return [description]
     */
    public String getNivel() {
        return nivel;
    }


    /**
     * [getAprobado description]
     * @return [description]
     */
    public boolean getAprobado() {
        return aprobado;
    }


    /**
     * [getCalificacion description]
     * @return [description]
     */
    public Integer getCalificacion() {
        return calificacion;
    }


    public List<Horario> getHorarios() {
        return horarios;
    }


    /**
     * [setProfesor description]
     * @param profesor [description]
     */
    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }


    /**
     * [setAlumno description]
     * @param alumno [description]
     */
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }


    /**
     * [setNivel description]
     * @param nivel [description]
     */
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }


    /**
     * [setAprobado description]
     * @param aprobado [description]
     */
    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }


    /**
     * [setCalificacion description]
     * @param calificacion [description]
     */
    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }


}