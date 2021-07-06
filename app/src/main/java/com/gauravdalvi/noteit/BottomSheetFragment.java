package com.gauravdalvi.noteit;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gauravdalvi.noteit.databinding.FragmentBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class BottomSheetFragment extends BottomSheetDialogFragment {

    FragmentBottomSheetBinding binding;
    FirebaseAuth auth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetBinding.inflate(getLayoutInflater(), container, false);
        auth = FirebaseAuth.getInstance();

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = binding.textView.getText().toString();
                if (s.length() == 0) {
                    Toast.makeText(getContext(), "Enter a Note", Toast.LENGTH_SHORT).show();
                }
                else {
                    reference.child(Objects.requireNonNull(auth.getUid())).push().setValue(s);
                    Toast.makeText(getContext(), "Note Successfully Added", Toast.LENGTH_SHORT).show();
                    binding.textView.setText("");
                }

            }
        });

        binding.signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(getContext(), SignIn.class);
                getActivity().finish();
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }
}