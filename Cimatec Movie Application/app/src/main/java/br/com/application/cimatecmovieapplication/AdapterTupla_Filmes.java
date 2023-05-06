package br.com.application.cimatecmovieapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView numLike = (TextView) rowView.findViewById(R.id.textNumLike);
        //Button btnLike = (Button) rowView.findViewById(R.id.btnLike);

        cartaz.setImageResource(filmes.get(position).getCartaz());
        titulo.setText(filmes.get(position).getTitulo());
        classificacao.setText(filmes.get(position).getClassificacao());
        ano.setText(filmes.get(position).getAno());
        genero.setText(filmes.get(position).getGenero());
        numLike.setText(filmes.get(position).getCurtidas() + " curtidas");

        return rowView;
    }
}

//        TextView txt_apelido = (TextView) rowView.findViewById(R.id.txt_apelido);
//        TextView txt_divida_total = (TextView) rowView.findViewById(R.id.txt_divida_total);
//
//        txt_apelido.setText(filmes.get(position).getNome());
//        txt_divida_total.setText(String.valueOf(valorFormatado));
