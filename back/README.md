# API-JAVA-SCHEDULE

###ENDPOINT PARA CRIAR UM PROFESSOR

[POST] http://localhost:3000/api/teachers:
<br />  
  Exemplo: 
  
      {
    "name": "professor",
    "email": "professor@professor.com.br"
      }
<br />
  
[GET] http://localhost:3000/api/teachers -> Pega todos os professores
<br />
<br />
[GET] http://localhost:3000/api/teachers/{id do professor aqui} -> Pega um professor por ID
<br />
<br />
[PUT] http://localhost:3000/api/teachers/{id do professor aqui} -> Atualiza um professor por ID
<br />
<br />
[DELETE] http://localhost:3000/api/teachers/{id do professor aqui} -> Deleta um professor por ID
<br />
<br />
-----------------------------------------------------
<br />
###ENDPOINT PARA CRIAR UMA DISCIPLINA 
<br />
[POST] http://localhost:3000/api/disciplines: 
<br />
  Exemplo: 

      {
    "name": "OOP",
      }
      
<br />

[GET] http://localhost:3000/api/disciplines -> Pega todos as disciplinas 
<br />
<br />
[GET] http://localhost:3000/api/disciplines/{id da disciplina aqui} -> Pega uma disciplina por ID
<br />
<br />
[PUT] http://localhost:3000/api/disciplines/{id da disciplinaaqui} -> Atualiza uma disciplina por ID
<br />
<br />
[DELETE] http://localhost:3000/api/disciplines/{id da disciplina aqui} -> Deleta uma disciplina por ID
<br />
<br />
-----------------------------------------------------
<br />
###ENDPOINT PARA CRIAR UMA SALA 
<br />
[POST] http://localhost:3000/api/classrooms: 
<br />
  Exemplo: 
      
      {
        "daysOfWeek": "SEGUNDA",
        "shift": "MATUTINO",
        "schedule": "7 as 11",
        teacherId: 1,
        disciplineId: 2
      
      }   
<br />

[GET] http://localhost:3000/api/classrooms -> Pega todas as salas
<br />
<br />
[GET] http://localhost:3000/api/classrooms/{id da sala aqui} -> Pega uma sala por ID
<br />
<br />
[PUT] http://localhost:3000/api/classrooms/{id da sala aqui} -> Atualiza uma sala por ID
<br />
<br />
[DELETE] http://localhost:3000/api/classrooms/{id da sala aqui} -> Deleta uma sala por ID
<br />
<br />
------------------------------------
<br />
<h1>Configuração do Banco</h1>

<p>Se encontra no arquivo <h3>ConnectionDB</h3></p>

### String de conexão: 

jdbc:mysql://localhost:3306/agenda_oop

Crie um schema no banco chamado:

<h3>agenda_oop</h3>

Nessas variáveis informe o seu usuario do banco e a senha: 

private static final String USER = "root"; // Exemplo de como está no arquivo. Altere root para o seu usuário do mysql

private static final String PASSWORD = "root123"; // Exemplo de como está no arquivo. Altere de root123 para a sua senha do mysql 




