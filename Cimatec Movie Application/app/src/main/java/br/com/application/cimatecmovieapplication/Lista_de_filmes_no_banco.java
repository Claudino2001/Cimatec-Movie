package br.com.application.cimatecmovieapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;

public class Lista_de_filmes_no_banco extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ListView listadefilmes;
    ArrayList<ClassFilme> listFilmes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_filmes_no_banco);

        listadefilmes = (ListView) findViewById(R.id.listadefilmes);

        MinhaAsyncTask task = new MinhaAsyncTask();
        task.execute();

    }


    public void listarFIlmes(){
        listFilmes = new ArrayList<ClassFilme>();
        db.collection("Filmes").orderBy("Titulo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ClassFilme filme = new ClassFilme();
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                filme.set_id(document.getId());
                                filme.setTitulo(document.getString("Titulo"));
                                filme.setClassificacao(document.getString("Classificacao"));
                                filme.setAno(document.getString("Ano"));
                                filme.setUrl_cartaz(document.getString("cartaz_url"));
                                filme.setGenero(document.getString("Genero"));
                                listFilmes.add(filme);
                            }
                            AdapterTupla_Filmes adapter = new AdapterTupla_Filmes(getApplicationContext(), listFilmes);
                            listadefilmes.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private class MinhaAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                listarFIlmes();
                return true;
            } catch (Exception e) {
                // Trate o caso em que a API falha em obter os dados
                return false;
            }
        }
    }



}