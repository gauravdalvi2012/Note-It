package com.gauravdalvi.noteit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gauravdalvi.noteit.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();


        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.signUpEmail.getText().toString();
                String password = binding.signUpPassword.getText().toString();

                if (email.length() == 0 || password.length() == 0) {
                    Toast.makeText(SignUp.this, "Enter Email and Password",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(SignUp.this, "Authentication Successful",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                                        finish();
                                        startActivity(intent);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignUp.this, task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


        binding.haveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
            }
        });

    }
}