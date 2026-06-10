package model;

public class Autor implements Persistivel {

    private int id;
    private String nome;
    private String email;
    private String pais;

    public Autor() {}

    public Autor(int id, String nome, String email, String pais) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.pais = pais;
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
    
    public String getEmail() { 
    	return email;
    }
    
    public void setEmail(String e) {
    	this.email = e; 
    }
    
    public String getPais() { 
    	return pais; 
    }
    
    public void setPais(String p) { 
    	this.pais = p;
    }

    @Override
    public String toString() {
    	return nome; 
    }
}
