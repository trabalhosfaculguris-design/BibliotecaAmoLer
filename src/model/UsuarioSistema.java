package model;

public class UsuarioSistema implements Persistivel {

    private int id;
    private String login;
    private PerfilUsuario perfil;
    private String senha;

    public UsuarioSistema() {
    }

    public UsuarioSistema(int id, String login, String senha, PerfilUsuario perfil) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUsuario perfil) {
        this.perfil = perfil;
    }
}