package models;
import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import models.*;

@Entity
public class Horario extends Model {

    @Id
    @GeneratedValue
    public Integer id;
    
    @Constraints.Required
    public String dia;

    @Constraints.Required
    @Formats.DateTime(pattern="HH:mm")
    public Date horaInicio = new Date();

    @Constraints.Required
    @Formats.DateTime(pattern="HH:mm")
    public Date horaFin = new Date();

    @Constraints.Required
    @ManyToOne(optional = false)
    public Curso curso;

    public static Finder<Integer,Horario> find = new Finder<Integer,Horario>(
        Integer.class, Horario.class
    ); 


    /**
     * [Horario description]
     * @param  dia        [description]
     * @param  horaInicio [description]
     * @param  horaFin    [description]
     */
    public Horario(String dia, Date horaInicio, Date horaFin) {
        this.dia        = dia;
        this.horaInicio = horaInicio;
        this.horaFin    = horaFin;
    }


    /**
     * [Horario description]
     */
    public Horario() {}

    /**
     * [getId description]
     * @return [description]
     */
    public Integer getId() {
        return id;
    }

    
    /**
     * [getDia description]
     * @return [description]
     */
    public String getDia() {
        return dia;
    }


    /**
     * [getHoraInicio description]
     * @return [description]
     */
    public Date getHoraInicio() {
        return horaInicio;
    }


    /**
     * [getHoraFin description]
     * @return [description]
     */
    public Date getHoraFin() {
        return horaFin;
    }


    /**
     * [setDia description]
     * @param dia [description]
     */
    public void setDia(String dia) {
        this.dia = dia;
    }


    /**
     * [setHoraInicio description]
     * @param horaInicio [description]
     */
    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }


    /**
     * [setHoraFin description]
     * @param horaFin [description]
     */
    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

}