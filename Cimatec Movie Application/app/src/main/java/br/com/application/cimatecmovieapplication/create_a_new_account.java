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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class create_a_new_account extends AppCompatActivity {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarios = reference.child("Usuarios");

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public EditText inputName, inputRA, inputSenha;
    public Button btCreate;
    int id_user_bd;

    String _ra, _name, _senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_anew_account);

        inputName = (EditText) findViewById(R.id.inputName);
        inputSenha = (EditText) findViewById(R.id.inputSenha);
        inputRA = (EditText) findViewById(R.id.inputRA);
        btCreate = (Button) findViewById(R.id.btCreate);

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _ra = inputRA.getText().toString();
                _name = inputName.getText().toString();
                _senha = inputSenha.getText().toString();
                if(!TextUtils.isEmpty(_ra) && !TextUtils.isEmpty(_name) && !TextUtils.isEmpty(_senha)){
                    MinhaAsyncTask task = new MinhaAsyncTask();
                    task.execute();
                }
            }
        });
    }

    private class MinhaAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

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
            if(resultado){
                Toast.makeText(create_a_new_account.this, "R.A. ja cadastrado.", Toast.LENGTH_SHORT).show();
            }else{
                cadastrar();
            }

        }
    }

    public void cadastrar(){
        Map<String, Object> user = new HashMap<>();
        user.put("nome", _name);
        user.put("ra", _ra);
        user.put("senha", _senha);
        user.put("curtidas", 0);
        user.put("playlist", Arrays.asList());
        user.put("data_cadastro", FieldValue.serverTimestamp());

        // Add a new document with a generated ID
        db.collection("Usuarios")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
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
        inputSenha.setText("");
        finish();
        Toast.makeText(create_a_new_account.this, "Cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
    }


}