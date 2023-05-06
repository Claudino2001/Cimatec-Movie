package br.com.application.cimatecmovieapplication;

import android.widget.ImageView;

public class ClassFilme {

    int id;
    int cartaz;
    String titulo;
    String classificacao;
    String ano;
    String genero;
    int curtidas;

    public ClassFilme(int id, int cartaz, String titulo, String classificacao, String ano, String genero, int curtidas) {
        this.id = id;
        this.cartaz = cartaz;
        this.titulo = titulo;
        this.classificacao = classificacao;
        this.ano = ano;
        this.genero = genero;
        this.curtidas = curtidas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCartaz() {
        return cartaz;
    }

    public void setCartaz(int cartaz) {
        this.cartaz = cartaz;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(int curtidas) {
        this.curtidas = curtidas;
    }
}
