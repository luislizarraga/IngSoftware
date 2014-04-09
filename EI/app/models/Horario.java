package models;
import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;

/**
 * Clase Horario que modela a un horario de un curso en la base de datos
 */
@Entity
public class Horario extends Model {

    @Id
    @GeneratedValue
    public Integer id;
    
    @Constraints.Required
    @Column(nullable = false)
    public String dia;

    @Constraints.Required
    @Column(nullable = false)
    public String horaInicio;

    @Constraints.Required
    @Column(nullable = false)
    public String horaFin;

    @Constraints.Required
    @ManyToOne(optional = false)
    public Curso curso;

    public static Finder<Integer,Horario> find = new Finder<Integer,Horario>(
        Integer.class, Horario.class
    ); 


    /**
     * Constructor de Horario que recibe 3 parámetros
     * @param  dia        el día del curso
     * @param  horaInicio la hora de inicio del curso
     * @param  horaFin    la hora de termino del curso
     */
    public Horario(String dia, String horaInicio, String horaFin, Curso curso) {
        this.dia        = dia;
        this.horaInicio = horaInicio;
        this.horaFin    = horaFin;
        this.curso      = curso;
    }


    /**
     * Constructor de Horario que no recibe parámetros
     */
    public Horario() {}

    /**
     * Metodo get
     * @return Integer el identificador del horario
     */
    public Integer getId() {
        return id;
    }

    
    /**
     * Metodo get
     * @return String El dia del horario
     */
    public String getDia() {
        return dia;
    }


    /**
     * Metodo get
     * @return String La hora de inicio del horario
     */
    public String getHoraInicio() {
        return horaInicio;
    }


    /**
     * Metodo get
     * @return String la hora de termino del horario
     */
    public String getHoraFin() {
        return horaFin;
    }


    /**
     * Metodo get
     * @return Curso El curso relacionado a este horario
     */
    public Curso getCurso() {
        return curso;
    }


    /**
     * Metodo set
     * @param dia el nuevo dia del horario
     */
    public void setDia(String dia) {
        this.dia = dia;
    }


    /**
     * Metodo set
     * @param horaInicio La nueva hora de inicio del horario
     */
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }


    /**
     * Metodo set
     * @param horaFin La nueva hora de termino del horario
     */
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

}