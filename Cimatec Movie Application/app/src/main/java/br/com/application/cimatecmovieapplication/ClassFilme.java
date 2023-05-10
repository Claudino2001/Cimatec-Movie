package br.com.application.cimatecmovieapplication;

import android.widget.ImageView;

public class ClassFilme {

    private String _id;
    private String titulo;
    private String url_cartaz;
    private String genero;
    private String classificacao;
    private String ano;

    public ClassFilme() {
    }

    public ClassFilme(String titulo, String url_cartaz, String genero, String classificacao, String ano) {
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

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }
}
