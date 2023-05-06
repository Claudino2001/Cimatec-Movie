package br.com.application.cimatecmovieapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterTupla_PlayList extends ArrayAdapter<ClassPlayList> {

    private final Context context;
    private final ArrayList<ClassPlayList> playlists;

    public AdapterTupla_PlayList(Context context, ArrayList<ClassPlayList> playlists){
        super(context, R.layout.tupla_playlist, playlists);
        this.context = context;
        this.playlists = playlists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tupla_playlist, parent, false);

        ImageView cartaz = (ImageView) rowView.findViewById(R.id.imgPlayList);
        TextView titulo = (TextView) rowView.findViewById(R.id.textTitulo);
        TextView autor = (TextView) rowView.findViewById(R.id.textAutor);
        TextView curtidas = (TextView) rowView.findViewById(R.id.textCurtidas);
        //Button btnLike = (Button) rowView.findViewById(R.id.btnLike);

        cartaz.setImageResource(playlists.get(position).getImg());
        titulo.setText(playlists.get(position).getTitulo());
        autor.setText(playlists.get(position).getAutor());
        curtidas.setText(playlists.get(position).getCurtidas() + " curtidas");

        return rowView;
    }
}