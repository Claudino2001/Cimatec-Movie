package br.com.application.cimatecmovieapplication;

import com.google.firebase.firestore.DocumentReference;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class ClassPlayList {

    private String nome_playlist;
    private String autor, id;
    private List<String> filmes;
    private int curtidas;

    public ClassPlayList() {
    }

    public ClassPlayList(String nome_playlist, String autor, int curtidas) {
        this.nome_playlist = nome_playlist;
        this.autor = autor;
        this.curtidas = curtidas;
    }

    public ClassPlayList(String nome_playlist, String autor, List<String> filmes, int curtidas) {
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

    public List<String> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<String> filmes) {
        this.filmes = filmes;
    }

    public int getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(int curtidas) {
        this.curtidas = curtidas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
