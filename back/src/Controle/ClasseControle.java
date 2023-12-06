package Controle;

import Serviço.ClasseServiço;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import Dominio.classe.Classe;
import Dominio.classe.CriarClasse;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

public class ClasseControle implements HttpHandler {
    private final ClasseServiço classeServiço;

    public ClasseControle(ClasseServiço classeServiço){
        this.classeServiço = classeServiço;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
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
        if(path.equals("/api/classrooms")){
            List<Classe> classes = classeServiço.getAllClassrooms();

            Gson gson = new Gson();
            String response = gson.toJson(classes);

            sendResponse(exchange, response);
        } else {
            int classroomId = extractIdFromPath(path);
            if(classroomId != -1){
                Classe classe = classeServiço.getClassroomById(classroomId);
                if(classe != null){
                    Gson gson = new Gson();
                    String response = gson.toJson(classe);

                    sendResponse(exchange, response);
                } else {
                    sendResponse(exchange, "Sala não encontrada ou cadastrada");
                }
            } else {
                sendResponse(exchange, "Solicitação inválida");
            }
        }
    }

    private void handlePostRequest(String path, HttpExchange exchange) throws IOException, SQLException{
        if (path.equals("/api/classrooms")) {
            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            CriarClasse criarClasse = parseCreateClassroomFromRequest(requestBody);

            classeServiço.criarClasse(criarClasse);
            sendResponse(exchange, "Disciplina criado com sucesso");
        } else {
            sendResponse(exchange, "Solicitação inválida", 400);
        }
    }

    private void handlePutRequest(String path, HttpExchange exchange) throws IOException, SQLException {

        int classroomId = extractIdFromPath(path);
        if (classroomId != -1) {

            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            CriarClasse criarClasse = parseCreateClassroomFromRequest(requestBody);

            classeServiço.updateClassroom(classroomId, criarClasse);
            sendResponse(exchange, "Disciplina atualizada com sucesso");
        } else {
            sendResponse(exchange, "Solicitação inválida", 400);
        }
    }
    private void handleDeleteRequest(String path, HttpExchange exchange) throws IOException, SQLException {

        int classroomId = extractIdFromPath(path);
        if (classroomId != -1) {
            classeServiço.deleteClassroom(classroomId);
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

    private CriarClasse parseCreateClassroomFromRequest(String requestBody) {
        Gson gson = new Gson();
        return gson.fromJson(requestBody, CriarClasse.class);
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
