package model;

public class UsuarioSistema implements Persistivel {

    private int id;
    private String login;
    private String senhaHash;
    private PerfilUsuario perfil;

    public UsuarioSistema() {}

    public UsuarioSistema(int id, String login, String senhaHash, PerfilUsuario perfil) {
        this.id = id;
        this.login = login;
        this.senhaHash = senhaHash;
        this.perfil = perfil;
    }

    @Override public int getId() {
        return id;
    }

    @Override public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String l) {
        this.login = l;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String s) {
        this.senhaHash = s;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUsuario p) {
        this.perfil = p;
    }
}