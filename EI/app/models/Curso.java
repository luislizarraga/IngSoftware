package models;
import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;

/**
 * Clase Curso que modela a un curso en la base de datos
 */
@Entity
public class Curso extends Model {

    @Id
    @GeneratedValue
    public Integer id;
    
    public boolean aprobado;

    public String constancia;
    
    public Integer calificacion;

    public boolean autorizado;

    public String notas;

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
     * Constructor de curso que recibe 5 parámetros.
     * @param  aprobado     un booleano que nos dice si fué aprobado el curso
     * @param  constancia   la constancia del curso
     * @param  calificacion la calificación del curso
     * @param  nivel        el nivel del curso
     * @param  alumno       el alumno del curso
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
     * Constructor de curso que recibe 4 parámetros.
     * @param  aprobado     un booleano que nos dice si fué aprobado el curso
     * @param  constancia   la constancia del curso
     * @param  calificacion la calificación del curso
     * @param  nivel        el nivel del curso
     */
    public Curso(boolean aprobado, String constancia, Integer calificacion, String nivel, Profesor profesor) {
        this(aprobado, constancia, calificacion, nivel, null, profesor);
    }


    /**
     * Constructor de curso que no recibe parámetros.
     */
    public Curso() {}


    /**
     * Metodo get
     * @return Integer el identificador del curso
     */
    public Integer getId() {
        return id;
    }


    /**
     * Metodo get
     * @return Profesor El profesor del curso
     */
    public Profesor getProfesor() {
        return profesor;
    }


    /**
     * Metodo get
     * @return Alumno El alumno del curso
     */
    public Alumno getAlumno() {
        return alumno;
    }


    /**
     * Metodo get
     * @return String el nivel del curso
     */
    public String getNivel() {
        return nivel;
    }


    /**
     * Metodo get
     * @return boolean Si fue aprobado el curso
     */
    public boolean getAprobado() {
        return aprobado;
    }


    /**
     * Metodo get
     * @return Integer La calificacion del curso
     */
    public Integer getCalificacion() {
        return calificacion;
    }


    /**
     * Metodo get
     * @return String La constancia del curso
     */
    public String getConstancia() {
        return constancia;
    }


    /**
     * Metodo get
     * @return String Las notas del curso
     */
    public String getNotas() {
        return notas;
    }


    /**
     * Metodo get
     * @return boolean La autorizacion del curso
     */
    public boolean getAutorizado() {
        return autorizado;
    }


    /**
     * Metodo get
     * @return List<Horario> Los horarios del curso
     */
    public List<Horario> getHorarios() {
        return horarios;
    }


    /**
     * Metodo set
     * @param profesor El nuevo profesor del curso
     */
    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }


    /**
     * Metodo set
     * @param alumno El nuevo alumno del curso
     */
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }


    /**
     * Metodo set
     * @param nivel El nuevo nivel del curso
     */
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }


    /**
     * Metodo set
     * @param aprobado Si fue aprobado el curso
     */
    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }


    /**
     * Metodo set
     * @param calificacion La nueva calificacion del curso
     */
    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }


    /**
     * Metodo set
     * @param constancia La nueva constancia del curso
     */
    public void setConstancia(Integer calificacion) {
        this.calificacion = calificacion;
    }


    /**
     * Metodo set
     * @param autorizado La nueva autorizacion del curso
     */
    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }


    /**
     * Metodo set
     * @param notas Las nuevas notas del curso
     */
    public void setNotas(String notas) {
        this.notas = notas;
    }
}