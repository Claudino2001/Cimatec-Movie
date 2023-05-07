package br.com.application.cimatecmovieapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public TextView btnCriarConta;
    public EditText inputRA;
    public Button btLogin;

    String _ra;
    String key_user;
    String name_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCriarConta = (TextView) findViewById(R.id.btnCriarConta);
        btLogin = (Button) findViewById(R.id.btLogin);
        inputRA = (EditText) findViewById(R.id.inputRA);

        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, create_a_new_account.class));
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _ra = inputRA.getText().toString();
                if(!TextUtils.isEmpty(_ra)){
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

                if(!querySnapshot.isEmpty()){
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                    key_user = document.getId();
                    name_user = (String) document.getData().get("nome");
                    //System.out.println("\n\n\n\n>>>>>>>>>>>>>>>>>>>>>>>>> key_user: " + key_user + "  " + n + "\n\n\n\n");
                }

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
                Toast.makeText(getApplicationContext(), "Acesso concedido.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, tela_menu.class);
                //System.out.println("\n\n\nCONFIRMANDO QUE N TA VAZIO: " + key_user + "\n\n\n\n");
                intent.putExtra("key_user", key_user);
                intent.putExtra("name_user", name_user);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(), "Acesso negado. Usuário não encontrado.", Toast.LENGTH_SHORT).show();
            }

        }
    }

}