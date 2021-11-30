package com.example.go4lunch;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class ProfilActivity extends AppCompatActivity {

    TextView tvUserName;
    TextView tvUserEmail;
    ImageView userImageView;
    Button btnSignOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        tvUserName = findViewById(R.id.userName);
        tvUserEmail = findViewById(R.id.userEmail);
        userImageView = findViewById(R.id.userImage);
        btnSignOut = findViewById(R.id.btnLogout);

        SharedPreferences preferences = this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userName = preferences.getString("username","");
        String userEmail = preferences.getString("useremail", "");
        String userImageUrl = preferences.getString("userPhoto","");

        tvUserName.setText(userName);
        tvUserEmail.setText(userEmail);
        Glide.with(this).load(userImageUrl).into(userImageView);

        btnSignOut.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            goToMainActivity();
        });
    }

    private void goToMainActivity() {
        startActivity(new Intent(ProfilActivity.this, MainActivity.class));
    }
}

