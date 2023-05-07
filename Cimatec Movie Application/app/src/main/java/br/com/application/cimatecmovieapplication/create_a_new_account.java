package br.com.application.cimatecmovieapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class create_a_new_account extends AppCompatActivity {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarios = reference.child("Usuarios");

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public EditText inputName, inputRA;
    public Button btCreate;
    int id_user_bd;

    String _ra, _name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_anew_account);

        inputName = (EditText) findViewById(R.id.inputName);
        inputRA = (EditText) findViewById(R.id.inputRA);
        btCreate = (Button) findViewById(R.id.btCreate);

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _ra = inputRA.getText().toString();
                _name = inputName.getText().toString();
                if(!TextUtils.isEmpty(_ra) && !TextUtils.isEmpty(_name)){
                    MinhaAsyncTask task = new MinhaAsyncTask();
                    task.execute();
                }
            }
        });
    }

    private class MinhaAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            // Faça a chamada à API ou busca de dados aqui
            // e use a Task do Firebase para aguardar a resposta
            //Task<AlgumTipoDeDados> task = api.getDados();

            try {
                Task<QuerySnapshot> task = db.collection("Usuarios")
                        .whereEqualTo("ra", _ra)
                        .get();

                QuerySnapshot querySnapshot = Tasks.await(task);
                System.out.println("isso: "+querySnapshot.toString());
                boolean raExiste = !querySnapshot.isEmpty();

                return raExiste;
            } catch (Exception e) {
                // Trate o caso em que a API falha em obter os dados
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean resultado) {
            // Use o resultado da função aqui
//            if (resultado) {
//                System.out.println(">>>> " + resultado);
//                // RA já existe
//            } else {
//                System.out.println(">>>> " + resultado);
//                // RA não existe
//            }

            if(resultado){
                Toast.makeText(create_a_new_account.this, "R.A. ja cadastrado.", Toast.LENGTH_SHORT).show();
            }else{
                cadastrar();
                Toast.makeText(create_a_new_account.this, "Cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void cadastrar(){
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("nome", _name);
        user.put("ra", _ra);

        HashMap x = new HashMap<>();

        // Add a new document with a generated ID
        db.collection("Usuarios")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                        // Create a new collection "Notas" inside the new user document
                        documentReference.collection("PlayList")
                                .document("MinhaPlayList")
                                .set(new ClassPlayList("PlayList Foda", documentReference.getId().toString(), null, 10))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "New note created for user " + documentReference.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding note", e);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        inputName.setText("");
        inputRA.setText("");
    }

    public ArrayList<String> lista_de_filmes(){
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("filme 1");
        strings.add("filme 2");
        strings.add("filme 3");
        strings.add("filme 4");
        return strings;
    }

}