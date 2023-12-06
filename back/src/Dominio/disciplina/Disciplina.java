package Dominio.disciplina;

public class Disciplina {
    private int id;

    private String name;

    public Disciplina(CriarDisciplina criarDisciplina){
        this.id = criarDisciplina.id();
        this.name = criarDisciplina.name();
    }

    public Disciplina(int id, String name){

    }
    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
