package Dominio.classe;

import Dominio.calendario.Dias;
import Dominio.calendario.Turno;

public class Classe {
    private int id;
    private Dias daysOfWeek;
    private Turno turno;
    private String schedule;
    private final int teacherId;
    private final int disciplineId;


    public Classe(CriarClasse create) {
        this.daysOfWeek = create.daysOfWeek();
        this.turno = create.turno();
        this.schedule = create.schedule();
        this.teacherId = create.professor().getId();
        this.disciplineId = create.disciplina().getId();
    }

    public int getId() {
        return id;
    }

    public Dias getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(Dias daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public Turno getShift() {
        return turno;
    }

    public void setShift(Turno turno) {
        this.turno = turno;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getTeacher() {
        return teacherId;
    }


    public int getDiscipline() {
        return disciplineId;
    }


}
