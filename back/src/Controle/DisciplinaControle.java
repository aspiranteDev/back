package Controle;

import Serviço.DisciplinaServiço;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import Dominio.disciplina.CriarDisciplina;
import Dominio.disciplina.Disciplina;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

public class DisciplinaControle implements HttpHandler{

    private final DisciplinaServiço disciplinaServiço;

    public DisciplinaControle(DisciplinaServiço disciplinaServiço){
        this.disciplinaServiço = disciplinaServiço;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        try{
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            switch (method){
                case "GET":
                    handleGetRequest(path, exchange);
                    break;
                case "POST":
                    handlePostRequest(path, exchange);
                    break;
                case "PUT":
                    handlePutRequest(path, exchange);
                    break;
                case "DELETE":
                    handleDeleteRequest(path, exchange);
                    break;
            }
        }catch (SQLException e){
            sendResponse(exchange, "Erro interno de servidor");
        }

    }

    private void handleGetRequest(String path, HttpExchange exchange) throws IOException, SQLException{
        if(path.equals("/api/disciplines")){
            List<Disciplina> disciplinas = disciplinaServiço.getAllDisciplines();

            Gson gson = new Gson();
            String response = gson.toJson(disciplinas);

            sendResponse(exchange, response);
        } else {
            int disciplineId = extractIdFromPath(path);
            if(disciplineId != -1){
                Disciplina disciplina = disciplinaServiço.getDisciplineById(disciplineId);
                if(disciplina != null){
                    Gson gson = new Gson();
                    String response = gson.toJson(disciplina);

                    sendResponse(exchange, response);
                } else {
                    sendResponse(exchange, "Discipline não encontrada ou cadastrada");
                }
            } else {
                sendResponse(exchange, "Solicitação inválida");
            }
        }
    }

    private void handlePostRequest(String path, HttpExchange exchange) throws IOException, SQLException{
        if (path.equals("/api/disciplines")) {
            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            CriarDisciplina criarDisciplina = parseCreateDisciplineFromRequest(requestBody);

            disciplinaServiço.criarDisciplina(criarDisciplina);
            sendResponse(exchange, "Disciplina criado com sucesso");
        } else {
            sendResponse(exchange, "Solicitação inválida", 400);
        }
    }

    private void handlePutRequest(String path, HttpExchange exchange) throws IOException, SQLException {

        int disciplineId = extractIdFromPath(path);
        if (disciplineId != -1) {

            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            CriarDisciplina criarDisciplina = parseCreateDisciplineFromRequest(requestBody);

            disciplinaServiço.updateDiscipline(disciplineId, criarDisciplina);
            sendResponse(exchange, "Disciplina atualizada com sucesso");
        } else {
            sendResponse(exchange, "Solicitação inválida", 400);
        }
    }
    private void handleDeleteRequest(String path, HttpExchange exchange) throws IOException, SQLException {

        int disciplineId = extractIdFromPath(path);
        if (disciplineId != -1) {
            disciplinaServiço.deleteDiscipline(disciplineId);
            sendResponse(exchange, "Disciplina excluída com sucesso");
        } else {
            sendResponse(exchange, "Solicitação inválida", 400);
        }
    }

    private int extractIdFromPath(String path) {
        try {
            String[] segments = path.split("/");
            return Integer.parseInt(segments[segments.length - 1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return -1;
        }
    }

    private CriarDisciplina parseCreateDisciplineFromRequest(String requestBody) {
        Gson gson = new Gson();
        return gson.fromJson(requestBody, CriarDisciplina.class);
    }


    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        sendResponse(exchange, response, 200);
    }
}
