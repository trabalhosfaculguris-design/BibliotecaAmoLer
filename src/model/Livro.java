package model;

import java.util.ArrayList;
import java.util.List;

public class Livro implements Persistivel {

    private int id;
    private String titulo;
    private String isbn;
    private int anoPublicacao;
    private String editora;
    private int quantidade;
    private Categoria categoria;
    private List<Autor> autores = new ArrayList<>();

    public Livro() {}

    public Livro(int id, String titulo, String isbn,
                 int anoPublicacao, String editora,
                 int quantidade, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.editora = editora;
        this.quantidade = quantidade;
        this.categoria = categoria;
    }

    @Override public int getId() { 
        return id; 
    }
    
    @Override public void setId(int id) { 
        this.id = id; 
    }
    
    public String getTitulo() { 
        return titulo; 
    }
    
    public void setTitulo(String t) { 
        this.titulo = t; 
    }
    
    public String getIsbn() { 
        return isbn; 
    }
    
    public void setIsbn(String i) { 
        this.isbn = i; 
    }
    
    public int getAnoPublicacao() { 
        return anoPublicacao; 
    }
    
    public void setAnoPublicacao(int a) { 
        this.anoPublicacao = a; 
    }
    
    public String getEditora() { 
        return editora; 
    }
    
    public void setEditora(String e) { 
        this.editora = e; 
    }
    
    public int getQuantidade() { 
        return quantidade; 
    }
    
    public void setQuantidade(int q) { 
        this.quantidade = q; 
    }
    
    public Categoria getCategoria() { 
        return categoria; 
    }
    
    public void setCategoria(Categoria c) { 
        this.categoria = c; 
    }
    
    public List<Autor> getAutores() { 
        return autores; 
    }
    
    public void setAutores(List<Autor> a) { 
        this.autores = a; 
    }

    public boolean isDisponivel() { 
        return quantidade > 0; 
    }

    @Override
    public String toString() { 
        return titulo; 
    }
}