package Serviço;

import DAO.ConnectionDB;
import DAO.DisciplinaDAO;
import Dominio.disciplina.CriarDisciplina;
import Dominio.disciplina.Disciplina;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DisciplinaServiço {
    private final DisciplinaDAO disciplinaDAO;

    public DisciplinaServiço(){
        this.disciplinaDAO = new DisciplinaDAO();
    }

    //Metodo para criar uma disciplina

    public void criarDisciplina(CriarDisciplina criarDisciplina) throws SQLException {
        try(Connection connection = ConnectionDB.getConnection()){
            disciplinaDAO.criarDisciplina(connection, criarDisciplina);
        }
    }

    //Metodo para obter uma disciplina por ID
    public Disciplina getDisciplineById(int disciplineId) throws SQLException{
        try(Connection connection = ConnectionDB.getConnection()){
            return disciplinaDAO.getDisciplineById(connection, disciplineId);
        }
    }

    //Metodo para obter todas as disciplinas
    public List<Disciplina> getAllDisciplines() throws SQLException{
        try(Connection connection = ConnectionDB.getConnection()){
            return disciplinaDAO.getAllDisciplines(connection);
        }
    }

    //Metodo para atualizar informações de uma disciplina
    public void updateDiscipline(int disciplineId, CriarDisciplina criarDisciplina) throws SQLException{
        try(Connection connection = ConnectionDB.getConnection()){
            disciplinaDAO.updateDiscipline(connection, disciplineId, criarDisciplina);
        }
    }

    //Metodo para excluir uma disciplina pelo id
    public void deleteDiscipline(int disciplineId) throws SQLException{
        try(Connection connection = ConnectionDB.getConnection()){
            disciplinaDAO.deleteDiscipline(connection, disciplineId);
        }
    }

}
