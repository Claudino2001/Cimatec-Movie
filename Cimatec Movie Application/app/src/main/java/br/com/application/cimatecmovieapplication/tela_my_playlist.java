package br.com.application.cimatecmovieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class tela_my_playlist extends AppCompatActivity {

    public FloatingActionButton btAddFilme;
    public ListView listViewMyList;
    ArrayList<ClassFilme> filmes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_my_playlist);

        listViewMyList = (ListView) findViewById(R.id.listViewMyList);
        btAddFilme = (FloatingActionButton) findViewById(R.id.btAddFilme);

        btAddFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tela_my_playlist.this, Lista_de_filmes_no_banco.class));
            }
        });

    }
}