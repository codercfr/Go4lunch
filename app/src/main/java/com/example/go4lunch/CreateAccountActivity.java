package com.example.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.go4lunch.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText email,password,confirmPassword;
    private Button createAccount,signIn;
    private FirebaseAuth firebaseAuth;
    private Users user;
    private DatabaseReference databaseReference;
    private FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        confirmPassword = findViewById(R.id.etConfirmPassword);
        createAccount = findViewById(R.id.btnCreateAccount);
        signIn = findViewById(R.id.btnSignIn2);
        mDatabase= FirebaseDatabase.getInstance("https://go4lunch-5272f-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference= mDatabase.getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();

        createAccount.setOnClickListener(view -> {
            createAccount();
        });
    }

    private void createAccount() {
        String user_email = email.getText().toString().trim();
        String user_password =
                password.getText().toString().trim();
        String confirm_paswword = confirmPassword.getText().toString().trim();
        firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                user=new Users();
                user.setEmail(user_email);
                user.setPassword(user_password);
                databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                        .setValue(user);
                startActivity(new Intent(getApplicationContext(),MapsActivity.class));
            }
            else{

            }
        });
    }



}