package br.com.application.cimatecmovieapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Lista_de_filmes_no_banco extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ListView listadefilmes;
    ArrayList<ClassFilme> listFilmes;
    String _id, _id_filme = null;

    int numFilmesPlaylist = 0;

    boolean pode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_filmes_no_banco);

        listadefilmes = (ListView) findViewById(R.id.listadefilmes);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _id = extras.getString("key_user");
        }

        System.out.println("\n\n\n\nID_USER:::: " + _id);

        listadefilmes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Add filme
                String str_titulo_filme = listFilmes.get(i).getTitulo();
                String str_id_filme = listFilmes.get(i).get_id();
                _id_filme = str_id_filme;
                Toast.makeText(Lista_de_filmes_no_banco.this, str_titulo_filme, Toast.LENGTH_SHORT).show();
                solicitarConfirmacao(str_titulo_filme, str_id_filme);

            }
        });

        MinhaAsyncTask task = new MinhaAsyncTask();
        task.execute();

    }


    public void listarFilmes(){
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
                listarFilmes();
                return true;
            } catch (Exception e) {
                // Trate o caso em que a API falha em obter os dados
                return false;
            }
        }
    }

    ///////////////////////////////////////// ADICIONAR FILME ////////////////////////////////////////////
    public void solicitarConfirmacao(String str_titulo_filme, String str_id_filme){
        System.out.println("AQUI 1");
        AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
        msgBox.setTitle("Adicionar filme");
        msgBox.setIcon(R.drawable.ic_add_filme);
        msgBox.setMessage("Deseja adicionar "+ str_titulo_filme +" a sua playlist?");
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PossoAddAsyncTask task = new PossoAddAsyncTask();
                task.execute(str_id_filme);
            }
        });
        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Lista_de_filmes_no_banco.this, "Operação cancelada.", Toast.LENGTH_SHORT).show();
            }
        });
        msgBox.show();
    }
    private class PossoAddAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String id = strings[0];
            try {
                DocumentReference docRef = db.collection("Usuarios").document(_id);
                DocumentSnapshot snapshot = Tasks.await(docRef.get());
                if (snapshot.exists() && snapshot.get("playlist") != null) {
                    List<DocumentReference> playlistRefs = (List<DocumentReference>) snapshot.get("playlist");
                    int numFilmesPlaylist = playlistRefs.size();
                    for (DocumentReference ref : playlistRefs) {
                        String refPath = ref.getPath().toString();
                        String filmeComp = "Filmes/" + id;
                        System.out.println(filmeComp + " " + numFilmesPlaylist);
                        System.out.println(filmeComp+"\n"+refPath);
                        if ((refPath.equals(filmeComp)) || numFilmesPlaylist >= 4) {
                            return false;
                        }
                    }
                }
                return true;
            } catch (Exception e) {
                // Trate o caso em que a API falha em obter os dados
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean podeAdicionar) {
            if (podeAdicionar) {
                System.out.println("\n\n\nPOSSO\n\n\n");
                Toast.makeText(Lista_de_filmes_no_banco.this, "Filme adicionado.", Toast.LENGTH_SHORT).show();
                adicionarFilmeNaPlaylist();
            } else {
                System.out.println("\n\n\nNÃO POSSO\n\n\n");
                Toast.makeText(Lista_de_filmes_no_banco.this, "Máximo atingido ou Filme repetido.", Toast.LENGTH_SHORT).show();
            }
        }

        public void adicionarFilmeNaPlaylist(){

            DocumentReference userRef = db.collection("Usuarios").document(_id);

            DocumentReference filmeRef = db.document("Filmes/"+_id_filme);

            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot snapshot) {
                    List<DocumentReference> playlist = (List<DocumentReference>) snapshot.get("playlist");
                    playlist.add(filmeRef);
                    userRef.update("playlist", playlist);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Erro ao adicionar referência à playlist", e);
                }
            });
        }
    }
}


