package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Dominio.professor.CriarProfessor;
import Dominio.professor.Professor;
public class ProfessorDAO {

    public static void createTeacherTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS teacher (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(255) NOT NULL," +
                "email VARCHAR(255) NOT NULL" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }
    public void criarProfessor(Connection connection, CriarProfessor criarProfessor) throws SQLException {
        createTeacherTable(connection);

        String sql = "INSERT INTO teacher (name, email) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, criarProfessor.name());
            statement.setString(2, criarProfessor.email());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar o professor, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    Professor createdTeacher = new Professor(generatedId, criarProfessor.name(), criarProfessor.email());
                    // faça o que precisar com o objeto Teacher criado
                } else {
                    throw new SQLException("Falha ao criar o professor, nenhum ID obtido.");
                }
            }

        }
    }


    public Professor getTeacherById(Connection connection, int teacherId) throws SQLException {
        String sql = "SELECT * FROM teacher WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, teacherId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToTeacher(resultSet);
                }
            }
        }

        return null;
    }


    public List<Professor> getAllTeachers(Connection connection) throws SQLException {
        String sql = "SELECT * FROM teacher";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            List<Professor> professors = new ArrayList<>();

            while (resultSet.next()) {
                Professor professor = mapResultSetToTeacher(resultSet);
                professors.add(professor);
            }

            return professors;
        }
    }


    public void updateTeacher(Connection connection, int teacherId, CriarProfessor criarProfessor) throws SQLException {
        String sql = "UPDATE teacher SET name = ?, email = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, criarProfessor.name());
            statement.setString(2, criarProfessor.email());
            statement.setInt(3, teacherId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao atualizar o professor, nenhum professor encontrado com o ID: " + teacherId);
            }
        }
    }


    public void deleteTeacher(Connection connection, int teacherId) throws SQLException {
        String sql = "DELETE FROM teacher WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, teacherId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao excluir o professor, nenhum professor encontrado com o ID: " + teacherId);
            }
        }
    }

    // Método auxiliar para mapear um ResultSet para um objeto Teacher
    private Professor mapResultSetToTeacher(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");

        return new Professor(id, name, email);
    }

}
