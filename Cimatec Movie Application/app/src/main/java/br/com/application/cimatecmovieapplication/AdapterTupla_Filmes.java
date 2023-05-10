package br.com.application.cimatecmovieapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdapterTupla_Filmes extends ArrayAdapter<ClassFilme> {

    private final Context context;
    private final ArrayList<ClassFilme> filmes;

    public AdapterTupla_Filmes(Context context, ArrayList<ClassFilme> filmes){
        super(context, R.layout.tupla_filme, filmes);
        this.context = context;
        this.filmes = filmes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tupla_filme, parent, false);

        ImageView cartaz = (ImageView) rowView.findViewById(R.id.imgCartaz);
        TextView titulo = (TextView) rowView.findViewById(R.id.textTitulo);
        TextView classificacao = (TextView) rowView.findViewById(R.id.txtClassificacao);
        TextView ano = (TextView) rowView.findViewById(R.id.textAno);
        TextView genero = (TextView) rowView.findViewById(R.id.textGenero);

        URL url;
        try {
            url = new URL(filmes.get(position).getUrl_cartaz());
        } catch (MalformedURLException e) {
            Toast.makeText(context, "erro: img" + position, Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }
        //GITHUB
        Glide.with(context).load(url).into(cartaz);

        titulo.setText(filmes.get(position).getTitulo());
        classificacao.setText(filmes.get(position).getClassificacao());
        ano.setText(filmes.get(position).getAno());
        genero.setText(filmes.get(position).getGenero());

        return rowView;
    }
}

