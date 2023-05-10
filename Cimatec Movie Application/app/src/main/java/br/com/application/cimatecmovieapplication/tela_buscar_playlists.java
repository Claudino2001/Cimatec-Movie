package br.com.application.cimatecmovieapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class tela_buscar_playlists extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ListView listViewPlayListsAmigos;
    ArrayList<ClassPlayList> playLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_buscar_playlists);

        listViewPlayListsAmigos = (ListView) findViewById(R.id.listViewPlayListsAmigos);

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
                            return;
                        }

                        List<String> cities = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("nome") != null) {
                                cities.add(doc.getString("nome"));
                            }
                        }
                        System.out.println("\n\n\n");
                        Log.d(TAG, "\n\n\nUsuarios: " + cities);
                        System.out.println("\n\n\n");
                    }
                });




//        for(int i = 0; i<10; i++){
//            ClassPlayList p = new ClassPlayList("Nome da PlayList", "Gabriel Claudino", null, 10);
//            playLists.add(p);
//        }
//
//        ArrayAdapter adapter = new AdapterTupla_PlayList(this, playLists);
//        listViewPlayListsAmigos.setAdapter(adapter);

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