package com.gauravdalvi.noteit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class NotesAdapter extends FirebaseRecyclerAdapter<String, NotesAdapter.myViewHolder> {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();

    public NotesAdapter(@NonNull FirebaseRecyclerOptions<String> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull String model) {
        holder.note.setText(model);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = getRef(holder.getAdapterPosition()).getKey();
                reference.child(Objects.requireNonNull(auth.getUid())).child(key).removeValue();
                Toast.makeText(view.getContext(), "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new NotesAdapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView note;
        ImageView delete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            note = (TextView) itemView.findViewById(R.id.note);
            delete = (ImageView) itemView.findViewById(R.id.delete);

        }
    }
}
