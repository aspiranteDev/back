package Controle;

import Serviço.ProfessorServiço;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import Dominio.professor.CriarProfessor;
import Dominio.professor.Professor;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;


public class ProfessorControle implements HttpHandler {

    private final ProfessorServiço professorServiço;

    public ProfessorControle(ProfessorServiço professorServiço) {
        this.professorServiço = professorServiço;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            switch (method) {
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
                default:
                    sendResponse(exchange, "Método não suportado", 405);
            }
        } catch (SQLException e) {
            sendResponse(exchange, "Erro interno do servidor", 500);
        }
    }

    private void handleGetRequest(String path, HttpExchange exchange) throws IOException, SQLException {
        if (path.equals("/api/teachers")) {

            List<Professor> professors = professorServiço.getAllTeachers();


            Gson gson = new Gson();
            String response = gson.toJson(professors);

            sendResponse(exchange, response);
        } else {
            int teacherId = extractIdFromPath(path);
            if (teacherId != -1) {
                Professor professor = professorServiço.getTeacherById(teacherId);
                if (professor != null) {
                    Gson gson = new Gson();
                    String response = gson.toJson(professor);

                    sendResponse(exchange, response);
                } else {
                    sendResponse(exchange, "Professor não encontrado", 404);
                }
            } else {
                sendResponse(exchange, "Solicitação inválida", 400);
            }
        }
    }


    private void handlePostRequest(String path, HttpExchange exchange) throws IOException, SQLException {
        if (path.equals("/api/teachers")) {
            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            CriarProfessor criarProfessor = parseCreateTeacherFromRequest(requestBody);

            professorServiço.criarProfessor(criarProfessor);
            sendResponse(exchange, "Professor criado com sucesso");
        } else {
            sendResponse(exchange, "Solicitação inválida", 400);
        }
    }

    private void handlePutRequest(String path, HttpExchange exchange) throws IOException, SQLException {
        // Atualizar um professor por ID
        int teacherId = extractIdFromPath(path);
        if (teacherId != -1) {
            // Exemplo: Analisar os parâmetros do corpo da solicitação para atualizar o professor
            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            CriarProfessor criarProfessor = parseCreateTeacherFromRequest(requestBody);

            professorServiço.updateTeacher(teacherId, criarProfessor);
            sendResponse(exchange, "Professor atualizado com sucesso");
        } else {
            sendResponse(exchange, "Solicitação inválida", 400);
        }
    }

    private void handleDeleteRequest(String path, HttpExchange exchange) throws IOException, SQLException {
        // Excluir um professor por ID
        int teacherId = extractIdFromPath(path);
        if (teacherId != -1) {
            professorServiço.deleteTeacher(teacherId);
            sendResponse(exchange, "Professor excluído com sucesso");
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

    private CriarProfessor parseCreateTeacherFromRequest(String requestBody) {
        Gson gson = new Gson();
        return gson.fromJson(requestBody, CriarProfessor.class);
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
