package Serviço;
import DAO.ConnectionDB;
import DAO.ProfessorDAO;
import Dominio.professor.CriarProfessor;
import Dominio.professor.Professor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
public class ProfessorServiço {
    private final ProfessorDAO professorDAO;

    public ProfessorServiço() {
        this.professorDAO = new ProfessorDAO();
    }

    // Método para criar um professor
    public void criarProfessor(CriarProfessor criarProfessor) throws SQLException {
        try (Connection connection = ConnectionDB.getConnection()) {
            professorDAO.criarProfessor(connection, criarProfessor);
        }
    }

    // Método para obter um professor pelo ID
    public Professor getTeacherById(int teacherId) throws SQLException {
        try (Connection connection = ConnectionDB.getConnection()) {
            return professorDAO.getTeacherById(connection, teacherId);
        }
    }

    // Método para obter todos os professores
    public List<Professor> getAllTeachers() throws SQLException {
        try (Connection connection = ConnectionDB.getConnection()) {
            return professorDAO.getAllTeachers(connection);
        }
    }

    // Método para atualizar informações de um professor
    public void updateTeacher(int teacherId, CriarProfessor criarProfessor) throws SQLException {
        try (Connection connection = ConnectionDB.getConnection()) {
            professorDAO.updateTeacher(connection, teacherId, criarProfessor);
        }
    }

    // Método para excluir um professor pelo ID
    public void deleteTeacher(int teacherId) throws SQLException {
        try (Connection connection = ConnectionDB.getConnection()) {
            professorDAO.deleteTeacher(connection, teacherId);
        }
    }
}
