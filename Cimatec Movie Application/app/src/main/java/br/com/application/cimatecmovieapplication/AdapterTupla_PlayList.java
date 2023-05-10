package br.com.application.cimatecmovieapplication;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdapterTupla_PlayList extends ArrayAdapter<ClassPlayList> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final Context context;
    private final ArrayList<ClassPlayList> playlists;

    public AdapterTupla_PlayList(Context context, ArrayList<ClassPlayList> playlists){
        super(context, R.layout.tupla_playlist, playlists);
        this.context = context;
        this.playlists = playlists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tupla_playlist, parent, false);

        ImageView cartaz = (ImageView) rowView.findViewById(R.id.imgPlayList);
        TextView titulo = (TextView) rowView.findViewById(R.id.textTitulo);
        TextView autor = (TextView) rowView.findViewById(R.id.textAutor);
        TextView curtidas = (TextView) rowView.findViewById(R.id.textCurtidas);
        Button btnLike = (Button) rowView.findViewById(R.id.btnLike);

        cartaz.setImageResource(R.drawable.ic_playlists);
        titulo.setText(playlists.get(position).getNome_playlist());
        autor.setText(playlists.get(position).getAutor());
        curtidas.setText(playlists.get(position).getCurtidas() + " curtidas");
        
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Like!", Toast.LENGTH_SHORT).show();
                MinhaAsyncTask task = new MinhaAsyncTask();
                task.execute(playlists.get(position));
            }
        });

        return rowView;
    }

    public void curtir(ClassPlayList p){
        DocumentReference likeRaf = db.collection("Usuarios").document(p.getId());
        likeRaf
                .update("curtidas", p.getCurtidas() + 1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    private class MinhaAsyncTask extends AsyncTask<ClassPlayList, Void, Boolean> {
        @Override
        protected Boolean doInBackground(ClassPlayList... classPlayLists) {
            ClassPlayList playList = classPlayLists[0];
            try {
                curtir(playList);
                return true;
            } catch (Exception e) {
                // Trate o caso em que a API falha em obter os dados
                return false;
            }
        }
    }

}

