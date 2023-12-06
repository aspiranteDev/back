import Serviço.ClasseServiço;
import Serviço.DisciplinaServiço;
import Serviço.ProfessorServiço;

import com.sun.net.httpserver.HttpServer;

import Controle.ClasseControle;
import Controle.DisciplinaControle;
import Controle.ProfessorControle;
import Controle.tests.MyHandler;
import Controle.tests.SecondHandler;

import java.net.InetSocketAddress;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ProfessorServiço professorServiço = new ProfessorServiço();
        ProfessorControle professorControle = new ProfessorControle(professorServiço);

        DisciplinaServiço disciplinaServiço = new DisciplinaServiço();
        DisciplinaControle disciplinaControle = new DisciplinaControle(disciplinaServiço);

        ClasseServiço classeServiço = new ClasseServiço();
        ClasseControle classeControle = new ClasseControle(classeServiço);

        HttpServer server = HttpServer.create(new InetSocketAddress(3000), 0);

        server.createContext("/api/teachers", professorControle);

        server.createContext("/api/disciplines", disciplinaControle);

        server.createContext("/api/classrooms", classeControle);


        server.setExecutor(null);
        server.start();
        System.out.println("Servidor HTTP iniciado na porta 3000");
    }
}
