package model;

public class Categoria implements Persistivel {

    private int id;
    private String nome;
    private String descricao;

    public Categoria() {}

    public Categoria(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    @Override public int getId() { 
    	return id; 
    }
    
    @Override public void setId(int id) { 
    	this.id = id; 
    }
    
    public String getNome() { 
    	return nome; 
    }
    
    public void setNome(String n) { 
    	this.nome = n; 
    }
    
    public String getDescricao() { 
    	return descricao; 
    }
    
    public void setDescricao(String d) { 
    	this.descricao = d; 
    }

    @Override
    public String toString() {
    	return nome;
    }
}