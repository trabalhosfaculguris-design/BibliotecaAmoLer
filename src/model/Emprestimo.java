package model;

import java.time.LocalDate;

public class Emprestimo implements Persistivel {

    private int id;
    private Membro membro;
    private Livro livro;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevista;
    private LocalDate dataDevolucao;
    private StatusEmprestimo status;

    public Emprestimo() {
        this.dataEmprestimo = LocalDate.now();
        this.dataPrevista = LocalDate.now().plusDays(14);
        this.status = StatusEmprestimo.ATIVO;
    }

    public Emprestimo(int id, Membro membro, Livro livro,
                      LocalDate dataEmprestimo, LocalDate dataPrevista,
                      LocalDate dataDevolucao, StatusEmprestimo status) {
        this.id = id;
        this.membro = membro;
        this.livro  = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevista = dataPrevista;
        this.dataDevolucao = dataDevolucao;
        this.status  = status;
    }

    @Override public int getId() { 
        return id; 
    }
    
    @Override public void setId(int id) { 
        this.id = id; 
    }
    
    public Membro getMembro() { 
        return membro; 
    }
    
    public void setMembro(Membro m) { 
        this.membro = m; 
    }
    
    public Livro getLivro() { 
        return livro; 
    }
    
    public void setLivro(Livro l) { 
        this.livro = l; 
    }
    
    public LocalDate getDataEmprestimo() { 
        return dataEmprestimo; 
    }
    
    public void setDataEmprestimo(LocalDate d) { 
        this.dataEmprestimo = d; 
    }
    
    public LocalDate getDataPrevista() { 
        return dataPrevista; 
    }
    
    public void setDataPrevista(LocalDate d) { 
        this.dataPrevista = d; 
    }
    
    public LocalDate getDataDevolucao() { 
        return dataDevolucao; 
    }
    
    public void setDataDevolucao(LocalDate d) { 
        this.dataDevolucao = d; 
    }
    
    public StatusEmprestimo getStatus() { 
        return status; 
    }
    
    public void setStatus(StatusEmprestimo s) { 
        this.status = s; 
    }

    public boolean estaAtrasado() {
        return status == StatusEmprestimo.ATIVO
            && LocalDate.now().isAfter(dataPrevista);
    }
}