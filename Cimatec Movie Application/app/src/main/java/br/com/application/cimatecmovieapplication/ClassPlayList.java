package br.com.application.cimatecmovieapplication;

import java.util.ArrayList;

public class ClassPlayList {

    private String nome_playlist;
    private String autor;
    private ArrayList<ClassFilme> filmes;
    private int curtidas;

    public ClassPlayList() {
    }

    public ClassPlayList(String nome_playlist, String autor, ArrayList<ClassFilme> filmes, int curtidas) {
        this.nome_playlist = nome_playlist;
        this.autor = autor;
        this.filmes = filmes;
        this.curtidas = curtidas;
    }

    public String getNome_playlist() {
        return nome_playlist;
    }

    public void setNome_playlist(String nome_playlist) {
        this.nome_playlist = nome_playlist;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public ArrayList<ClassFilme> getFilmes() {
        return filmes;
    }

    public void setFilmes(ArrayList<ClassFilme> filmes) {
        this.filmes = filmes;
    }

    public int getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(int curtidas) {
        this.curtidas = curtidas;
    }
}
