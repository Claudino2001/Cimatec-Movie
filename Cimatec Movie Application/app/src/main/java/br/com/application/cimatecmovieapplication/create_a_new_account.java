package br.com.application.cimatecmovieapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class create_a_new_account extends AppCompatActivity {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarios = reference.child("Usuarios");
    public EditText inputName, inputRA;
    public Button btCreate;
    int id_user_bd;

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
                cadastrar();
            }
        });

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(">>> id_user_firebase: " + snapshot.child("id_user").getValue().toString());
                id_user_bd = Integer.parseInt(snapshot.child("id_user").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(create_a_new_account.this, "Falha de conexão: " + error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    void cadastrar(){
        id_user_bd = id_user_bd + 1;
        Usuario u = new Usuario();
        if(!TextUtils.isEmpty(inputName.getText().toString()) && !TextUtils.isEmpty(inputRA.getText().toString())){
            u.setId(id_user_bd);
            u.setNome(inputName.getText().toString());
            u.setRa(inputRA.getText().toString());
            usuarios.child(String.valueOf(id_user_bd)).setValue(u);
            usuarios.child("id_user").setValue(String.valueOf(id_user_bd));
            inputName.setText("");
            inputRA.setText("");
        }else{
            Toast.makeText(this, "Preencha ambos os campos.", Toast.LENGTH_SHORT).show();
        }
    }
}