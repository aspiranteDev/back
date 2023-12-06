package Dominio.classe;
import Dominio.calendario.Dias;
import Dominio.calendario.Turno;
import Dominio.disciplina.Disciplina;
import Dominio.professor.Professor;
public record CriarClasse(int id, Dias daysOfWeek, Turno turno, String schedule, Professor professor, Disciplina disciplina) {

}
