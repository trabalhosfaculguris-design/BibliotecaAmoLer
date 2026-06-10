package model;

public class Membro implements Persistivel {

    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String endereco;
    private boolean ativo;

    public Membro() { 
    	this.ativo = true; 
    }

    public Membro(int id, String nome, String cpf,
                  String email, String telefone,
                  String endereco, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.ativo = ativo;
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
    
    public String getCpf() { 
        return cpf; 
    }
    
    public void setCpf(String c) { 
        this.cpf = c; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String e) { 
        this.email = e; 
    }
    
    public String getTelefone() { 
        return telefone; 
    }
    
    public void setTelefone(String t) { 
        this.telefone = t; 
    }
    
    public String getEndereco() { 
        return endereco; 
    }
    
    public void setEndereco(String e) { 
        this.endereco = e; 
    }
    
    public boolean isAtivo() { 
        return ativo; 
    }
    
    public void setAtivo(boolean a) { 
        this.ativo = a; 
    }

    @Override
    public String toString() { 
        return nome + " (" + cpf + ")"; 
    }
}