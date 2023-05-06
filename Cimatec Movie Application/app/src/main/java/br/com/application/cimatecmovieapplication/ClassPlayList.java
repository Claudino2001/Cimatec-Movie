package br.com.application.cimatecmovieapplication;

public class ClassPlayList {

    private int id;
    private int id_criador;
    private String titulo;
    private String autor;
    private int curtidas;
    private int img;

    public ClassPlayList() {
    }

    public ClassPlayList(int id, int id_criador, String titulo, String autor, int curtidas, int img) {
        this.id = id;
        this.id_criador = id_criador;
        this.titulo = titulo;
        this.autor = autor;
        this.curtidas = curtidas;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_criador() {
        return id_criador;
    }

    public void setId_criador(int id_criador) {
        this.id_criador = id_criador;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(int curtidas) {
        this.curtidas = curtidas;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
