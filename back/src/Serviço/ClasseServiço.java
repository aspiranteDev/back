package Serviço;

import DAO.ClasseDAO;
import DAO.ConnectionDB;
import Dominio.classe.Classe;
import Dominio.classe.CriarClasse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClasseServiço {
    private final ClasseDAO classeDAO;

    public ClasseServiço() {
        this.classeDAO = new ClasseDAO();
    }

    // Método para criar uma sala de aula
    public void criarClasse(CriarClasse criarClasse) throws SQLException {
        try (Connection connection = ConnectionDB.getConnection()) {
            classeDAO.criarClasse(connection, criarClasse);
        }
    }

    // Método para obter uma sala de aula pelo ID
    public Classe getClassroomById(int classroomId) throws SQLException {
        try (Connection connection = ConnectionDB.getConnection()) {
            return classeDAO.getClassroomById(connection, classroomId);
        }
    }

    // Método para obter todas as salas de aula
    public List<Classe> getAllClassrooms() throws SQLException {
        try (Connection connection = ConnectionDB.getConnection()) {
            return classeDAO.getAllClassrooms(connection);
        }
    }

    // Método para atualizar informações de uma sala de aula
    public void updateClassroom(int classroomId, CriarClasse criarClasse) throws SQLException {
        try (Connection connection = ConnectionDB.getConnection()) {
            classeDAO.updateClassroom(connection, classroomId, criarClasse);
        }
    }

    // Método para excluir uma sala de aula pelo ID
    public void deleteClassroom(int classroomId) throws SQLException {
        try (Connection connection = ConnectionDB.getConnection()) {
            classeDAO.deleteClassroom(connection, classroomId);
        }
    }
}
