package br.com.application.cimatecmovieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class tela_buscar_playlists extends AppCompatActivity {

    public ListView listViewPlayListsAmigos;
    ArrayList<ClassPlayList> playLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_buscar_playlists);

        listViewPlayListsAmigos = (ListView) findViewById(R.id.listViewPlayListsAmigos);

        listar();
    }

    void listar(){
        for(int i = 0; i<10; i++){
            ClassPlayList p = new ClassPlayList(i, i, "O Cu do Poder", "Gabriel Claudino", 10, R.drawable.ic_playlists);
            playLists.add(p);
        }

        ArrayAdapter adapter = new AdapterTupla_PlayList(this, playLists);
        listViewPlayListsAmigos.setAdapter(adapter);

    }

}