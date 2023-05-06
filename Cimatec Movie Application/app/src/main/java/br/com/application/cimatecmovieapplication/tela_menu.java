package br.com.application.cimatecmovieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class tela_menu extends AppCompatActivity {

    public Button btMyPlayList, btSearchPlayLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_menu);

        btMyPlayList = (Button) findViewById(R.id.btMyPlayList);
        btSearchPlayLists = (Button) findViewById(R.id.btSearchPlayLists);

        btMyPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tela_menu.this, tela_my_playlist.class));
            }
        });

        btSearchPlayLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tela_menu.this, tela_buscar_playlists.class));
            }
        });

    }
}