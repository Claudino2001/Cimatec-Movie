package br.com.application.cimatecmovieapplication;

import android.widget.ImageView;

public class ClassFilme {

    String _id;
    String titulo;
    String url_cartaz;
    String genero;
    String classificacao;
    double ano;

    public ClassFilme() {
    }

    public ClassFilme(String titulo, String url_cartaz, String genero, String classificacao, double ano) {
        this.titulo = titulo;
        this.url_cartaz = url_cartaz;
        this.genero = genero;
        this.classificacao = classificacao;
        this.ano = ano;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl_cartaz() {
        return url_cartaz;
    }

    public void setUrl_cartaz(String url_cartaz) {
        this.url_cartaz = url_cartaz;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public double getAno() {
        return ano;
    }

    public void setAno(double ano) {
        this.ano = ano;
    }
}
