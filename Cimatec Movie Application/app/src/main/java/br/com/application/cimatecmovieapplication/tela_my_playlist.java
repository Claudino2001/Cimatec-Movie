package br.com.application.cimatecmovieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class tela_my_playlist extends AppCompatActivity {

    public ListView listViewMyList;
    ArrayList<ClassFilme> filmes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_my_playlist);

        listViewMyList = (ListView) findViewById(R.id.listViewMyList);

        listarFilmes();

    }

    void listarFilmes(){
        for(int i = 0; i<10; i++){
            ClassFilme f = new ClassFilme(1, R.drawable.cartaz_img, "mariozinho", "18 anos", "1990", "Putaria", 10 );
            filmes.add(f);
        }
        ArrayAdapter adapter = new AdapterTupla_Filmes(this, filmes);
        listViewMyList.setAdapter(adapter);
    }

}