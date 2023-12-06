package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Dominio.disciplina.CriarDisciplina;
import Dominio.disciplina.Disciplina;

public class DisciplinaDAO {
    public static void createTableDiscipline(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS discipline (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(255) NOT NULL," +
                ")";
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        }
    }

    public void criarDisciplina(Connection connection, CriarDisciplina criarDisciplina) throws SQLException{

        createTableDiscipline(connection);

        String sql = "INSERT INTO discipline (name) VALUES (?)";

        try(PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, criarDisciplina.name());

            int affectedRows = statement.executeUpdate();

            if(affectedRows == 0){
                throw new SQLException("Falha ao criar a disciplina, nenhuma linha afetada");
            }

            try(ResultSet generetadKeys = statement.getGeneratedKeys()){
                if(generetadKeys.next()){
                    int generatedID = generetadKeys.getInt(1);
                    criarDisciplina.setId(generatedID);
                }else {
                    throw new SQLException("Falha ao criar a disciplina, nenhum ID obtido");
                }
            }
        }
    }

    public Disciplina getDisciplineById(Connection connection, int disciplineId) throws SQLException{
        String sql = "SELECT * FROM discipline WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, disciplineId);

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    return mapResultSetToDiscipline(resultSet);
                }
            }
        }
        return null;
    }


    public List<Disciplina> getAllDisciplines(Connection connection) throws SQLException{
        String sql = "SELECT * FROM discipline";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            List<Disciplina> discplines = new ArrayList<>();

            while (resultSet.next()) {
                Disciplina disciplina = mapResultSetToDiscipline(resultSet);
                discplines.add(disciplina);
            }

            return discplines;
        }
    }

    public void updateDiscipline(Connection connection, int disciplineId, CriarDisciplina criarDisciplina) throws SQLException {
        String sql = "UPDATE discipline SET name = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, criarDisciplina.name());
            statement.setInt(3, disciplineId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao atualizar o professor, nenhuma disciplina encontrado com o ID: " + disciplineId);
            }
        }
    }


    public void deleteDiscipline(Connection connection, int disciplineId) throws SQLException {
        String sql = "DELETE FROM discipline WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, disciplineId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao excluir a disciplina, nenhuma disciplina encontrada com o ID: " + disciplineId);
            }
        }
    }


    private Disciplina mapResultSetToDiscipline(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");

        return new Disciplina(id, name);
    }
}
