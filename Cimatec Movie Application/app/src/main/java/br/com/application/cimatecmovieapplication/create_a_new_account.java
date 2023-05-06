package br.com.application.cimatecmovieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class create_a_new_account extends AppCompatActivity {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarios = reference.child("Usuarios");

    public EditText inputName, inputRA;
    public Button btCreate;

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

    }

    void cadastrar(){
        Usuario u = new Usuario(2, "Nome", "RA");
        usuarios.child("2").setValue(u);
    }

}