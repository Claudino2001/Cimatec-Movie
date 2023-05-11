package br.com.application.cimatecmovieapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class tela_buscar_playlists extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("Usuarios");
    public ListView listViewPlayListsAmigos;
    ArrayList<ClassPlayList> playLists;
    private static final String TAG = "MinhaActivityBuscarPlaylists";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_buscar_playlists);

        listViewPlayListsAmigos = (ListView) findViewById(R.id.listViewFilmesDoAmigo);

        listViewPlayListsAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(tela_buscar_playlists.this, "Item " + i, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(tela_buscar_playlists.this, tela_playlist_alheia.class);
                intent.putExtra("NOME_AUTOR", playLists.get(i).getAutor());
                intent.putExtra("NOME_PLAYLIST", playLists.get(i).getNome_playlist());
                intent.putExtra("ID_AUTOR", playLists.get(i).getId());
                startActivity(intent);
            }
        });

        MinhaAsyncTask task = new MinhaAsyncTask();
        task.execute();

    }


    void listar(){
        db.collection("Usuarios").
                orderBy("nome").
                addSnapshotListener(new EventListener<QuerySnapshot>() {

                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            //return false;
                        }
                        playLists = new ArrayList<>();
                        //DocumentReference playListref;
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("playlist") != null) {
                                ClassPlayList p;
                                System.out.println("\nID DO user: " + doc.getId());
                                System.out.println("Nome da PlayList: " + doc.getString("nome_playlist"));
                                System.out.println("Numéro de curtidas: " + doc.getDouble("curtidas"));
                                System.out.println("Nome do criador: " + doc.getString("nome"));
                                Double doubleNum = doc.getDouble("curtidas");
                                int curtidas = doubleNum.intValue();
                                p = new ClassPlayList(doc.getString("nome_playlist"), doc.getString("nome"), curtidas);
                                p.setId(doc.getId());
                                playLists.add(p);
                            }
                        }
                        AdapterTupla_PlayList adapter = new AdapterTupla_PlayList(getApplicationContext(), playLists);
                        listViewPlayListsAmigos.setAdapter(adapter);
                        //return false;
                    }
                });
    }

    private class MinhaAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                listar();
                return true;
            } catch (Exception e) {
                // Trate o caso em que a API falha em obter os dados
                return false;
            }
        }
    }

}