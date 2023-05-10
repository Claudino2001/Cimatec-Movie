package br.com.application.cimatecmovieapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class tela_playlist_alheia extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public TextView txtNomePlaylist, txtAutor;
    String autor_playlist, nome_playlist, id_autor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_playlist_alheia);

        txtNomePlaylist = (TextView) findViewById(R.id.txtNomePlaylist);
        txtAutor =(TextView) findViewById(R.id.txtAutor);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            autor_playlist = extras.getString("NOME_AUTOR");
            nome_playlist = extras.getString("NOME_PLAYLIST");
            id_autor = extras.getString("ID_AUTOR");
        }

        System.out.println("\n\n>>> CHEGOU: " + autor_playlist + nome_playlist + id_autor);

        txtNomePlaylist.setText(nome_playlist);
        txtAutor.setText("By " + autor_playlist);

        MinhaAsyncTask task = new MinhaAsyncTask();
        task.execute();

    }

    void listarFilmes(){

        final DocumentReference docRef = db.collection("Usuarios").document(id_autor);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());
                    if (snapshot.get("playlist") != null) {
                        String _id_user = snapshot.getId();
                        System.out.println("\nID DO user: " + snapshot.getId());
                        List<DocumentReference> playlistRefs = (List<DocumentReference>) snapshot.get("playlist");
                        System.out.println("Referências de playlist:");
                        for (DocumentReference ref : playlistRefs) {
                            //Apresentando na tela os filmes da lista
                            mostrando_filmes_da_playlist(ref.getPath(), _id_user);
                            System.out.println(ref.getPath());
                        }
                    }

                } else {
                    Log.d(TAG, "Current data: null");
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

    public void mostrando_filmes_da_playlist(String filmeRef, String _id){
        // Obtém uma referência para o documento "Filmes/qFAVz9n4kgbdXJrVpUZZ"
        DocumentReference ref = db.document(filmeRef);

        // Busca o documento referenciado pela referência
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // O documento existe, você pode acessar os dados usando o objeto DocumentSnapshot
                    String titulo = documentSnapshot.getString("Titulo");
                    String diretor = documentSnapshot.getString("Genero");
                    //int ano = documentSnapshot.getLong("ano").intValue();

                    System.out.println("Título: " + titulo);
                    System.out.println("Genero: " + diretor);
                    //System.out.println("Ano: " + ano);
                } else {
                    System.out.println("O documento não existe.");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Falha ao buscar o documento referenciado.");
                e.printStackTrace();
            }
        });
    }

}