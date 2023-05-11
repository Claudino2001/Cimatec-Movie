package br.com.application.cimatecmovieapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class tela_menu extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Button btMyPlayList, btSearchPlayLists;
    public TextView txtTituloMenu;
    String key_user, name_user;
    //BANCO LOCAL
    private SQLiteDatabase banco;
    private static final String DATABASE_NAME = "dbCimatecMovie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_menu);

        acessarKey_user();

        btMyPlayList = (Button) findViewById(R.id.btMyPlayList);
        btSearchPlayLists = (Button) findViewById(R.id.btSearchPlayLists);
        txtTituloMenu = (TextView) findViewById(R.id.txtTituloMenu);

        welcomeUser();

        btMyPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tela_menu.this, tela_my_playlist.class);
                intent.putExtra("key_user" ,key_user);
                startActivity(intent);
            }
        });

        btSearchPlayLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tela_menu.this, tela_buscar_playlists.class));
            }
        });

    }

    public void acessarKey_user(){
        try{
            banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            Cursor cursor = banco.rawQuery("SELECT key_user FROM tb_key_user;", null);

            if(cursor.moveToFirst()){
                do{
                    key_user = cursor.getString((int) cursor.getColumnIndex("key_user"));

                }while (cursor.moveToNext());
            }

            banco.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void welcomeUser(){
        DocumentReference docRef = db.collection("Usuarios").document(key_user);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        txtTituloMenu.setText("Bem-vindo(a), " + document.getString("nome"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

}