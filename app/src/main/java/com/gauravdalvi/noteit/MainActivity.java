package com.gauravdalvi.noteit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.gauravdalvi.noteit.databinding.ActivityMainBinding;
import com.gauravdalvi.noteit.databinding.ActivitySignInBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;
    NotesAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        auth = FirebaseAuth.getInstance();

        recyclerView = binding.recycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child(Objects.requireNonNull(auth.getUid()));


        FirebaseRecyclerOptions<String> options =
                new FirebaseRecyclerOptions.Builder<String>()
                        .setQuery(query, String.class)
                        .build();

        adapter = new NotesAdapter(options);
        recyclerView.setAdapter(adapter);

        int a = recyclerView.getChildCount();

        binding.FloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment fragment = new BottomSheetFragment();
                fragment.show(getSupportFragmentManager(), fragment.getTag());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}