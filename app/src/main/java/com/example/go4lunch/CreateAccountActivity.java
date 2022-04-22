package com.example.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.go4lunch.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;
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
        createAccount = findViewById(R.id.btnCreateAccount);
        signIn = findViewById(R.id.btnSignIn2);
        mDatabase= FirebaseDatabase.getInstance("https://go4lunch-5272f-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference= mDatabase.getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();

        createAccount.setOnClickListener(view -> {
            String user_email = email.getText().toString().trim();
            String user_password =
                    password.getText().toString().trim();
            if (TextUtils.isEmpty(user_email)){
                email.setError("Email cannot be empty");
                email.requestFocus();
            }else if (TextUtils.isEmpty(user_password)) {
                password.setError("Password cannot be empty");
                password.requestFocus();

            }else {
                firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user=new Users();
                        user.setEmail(user_email);
                        user.setPassword(user_password);
                        databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                                .setValue(user);
                        startActivity(new Intent(CreateAccountActivity.this, MapsActivity.class));
                    } else {
                        task.getException().getMessage();
                        Toast.makeText(this,  task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        task.getException().getMessage();
                    }
                });
            }
        });

        signIn.setOnClickListener(view -> {
            String user_email = email.getText().toString().trim();
            String user_password =
                    password.getText().toString().trim();
            if (TextUtils.isEmpty(user_email)){
                email.setError("Email cannot be empty");
                email.requestFocus();
            }else if (TextUtils.isEmpty(user_password)) {
                password.setError("Password cannot be empty");
                password.requestFocus();

            }
            firebaseAuth.signInWithEmailAndPassword(user_email, user_password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(new Intent(CreateAccountActivity.this, MapsActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                /*try{
                   ValueEventListener valueEventListener= new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                           for (DataSnapshot ds : snapshot.getChildren()) {
                               Users children = ds.getValue(Users.class);
                               if (children.getRestaurantName().equals(user.getRestaurantName())) {
                                   userName.add(children.getUsername());
                               }
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull @NotNull DatabaseError error) {

                       }
                   };

                }catch (Exception e) {
                    e.printStackTrace();
                }*/
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