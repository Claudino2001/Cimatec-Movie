package br.com.application.cimatecmovieapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class tela_my_playlist extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public FloatingActionButton btAddFilme;
    public ListView listViewMyList;
    ArrayList<ClassFilme> filmes = new ArrayList<>();
    String _id, _id_filme;
    private SQLiteDatabase banco;
    private static final String DATABASE_NAME = "dbCimatecMovie";
    ArrayList<ClassFilme> listFilmes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_my_playlist);

        listViewMyList = (ListView) findViewById(R.id.listViewMyList);
        btAddFilme = (FloatingActionButton) findViewById(R.id.btAddFilme);

        acessarKey_user();

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            _id = extras.getString("key_user");
//        }

        System.out.println("\n\n\n\n\n\n>>>>>>>>>> id tela my playlist BANCOOOO" + _id);

        btAddFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tela_my_playlist.this, Lista_de_filmes_no_banco.class);
                //intent.putExtra("key_user", _id);
                startActivity(intent);
            }
        });

        listViewMyList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str_titulo_filme = listFilmes.get(i).getTitulo();
                String str_id_filme = listFilmes.get(i).get_id();
                _id_filme = str_id_filme;
                solicitarConfirmacao(str_titulo_filme, str_id_filme, i);
                return true;
            }
        });

        MinhaAsyncTask task = new MinhaAsyncTask();
        task.execute();

    }

    public void solicitarConfirmacao(String str_titulo_filme, String str_id_filme, int posi){
        System.out.println("AQUI 1");
        AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
        msgBox.setTitle("Deletar Filme");
        msgBox.setIcon(R.drawable.ic_delete);
        msgBox.setMessage("Deseja deletar "+ str_titulo_filme +" a sua playlist?");
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deletarFilme(posi);
            }
        });
        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(tela_my_playlist.this, "Operação cancelada.", Toast.LENGTH_SHORT).show();
            }
        });
        msgBox.show();
    }

    public void deletarFilme(int position) {
        // Obter a referência do documento do usuário atual
        DocumentReference userRef = db.collection("Usuarios").document(_id);

        // Atualizar a lista localmente removendo a referência na posição especificada
        listFilmes.remove(position);

        List<DocumentReference> filmesRef = new ArrayList<DocumentReference>();
        for(ClassFilme f : listFilmes){
            String str = "Filmes/" + f.get_id();
            DocumentReference doc = db.document(str);
            filmesRef.add(doc);
        }
        // Atualizar o documento no Firestore com a lista atualizada
        userRef.update("playlist", filmesRef)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // A atualização foi bem sucedida
                        Log.d(TAG, "Referência removida com sucesso");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // A atualização falhou
                        Log.w(TAG, "Erro ao remover a referência", e);
                    }
                });
        listarFilmes();
    }


    public void listarFilmes() {
        listFilmes = new ArrayList<ClassFilme>();
        db.collection("Usuarios")
                .document(_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                List<DocumentReference> playlist = (List<DocumentReference>) document.get("playlist");
                                for (DocumentReference filmeRef : playlist) {
                                    filmeRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot filmeDoc = task.getResult();
                                                if (filmeDoc.exists()) {
                                                    ClassFilme filme = new ClassFilme();
                                                    filme.set_id(filmeDoc.getId());
                                                    filme.setTitulo(filmeDoc.getString("Titulo"));
                                                    filme.setClassificacao(filmeDoc.getString("Classificacao"));
                                                    filme.setAno(filmeDoc.getString("Ano"));
                                                    filme.setUrl_cartaz(filmeDoc.getString("cartaz_url"));
                                                    filme.setGenero(filmeDoc.getString("Genero"));
                                                    listFilmes.add(filme);
                                                    System.out.println("\n\n\n\n\n>>>>> " + listFilmes.size());
                                                    AdapterTupla_Filmes adapter = new AdapterTupla_Filmes(getApplicationContext(), listFilmes);
                                                    listViewMyList.setAdapter(adapter);
                                                } else {
                                                    Log.d(TAG, "Filme não encontrado!");
                                                }
                                            } else {
                                                Log.d(TAG, "Erro ao buscar filme!", task.getException());
                                            }
                                        }
                                    });
                                }
                            } else {
                                Log.d(TAG, "Documento não encontrado!");
                            }
                        } else {
                            Log.d(TAG, "Erro ao buscar documento!", task.getException());
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

    public void acessarKey_user(){
        try{
            banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            Cursor cursor = banco.rawQuery("SELECT key_user FROM tb_key_user;", null);

            if(cursor.moveToFirst()){
                do{
                    _id = cursor.getString((int) cursor.getColumnIndex("key_user"));

                }while (cursor.moveToNext());
            }

            banco.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}