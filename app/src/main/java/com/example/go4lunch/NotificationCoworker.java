package com.example.go4lunch;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.go4lunch.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.events.EventHandler;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class NotificationCoworker extends Worker  {

    private NotificationManagerCompat notificationManager;
    public static final String CHANNEL_1_ID = "channel1";
    private FirebaseDatabase mDatabase;
    private DatabaseReference databaseReference;
    private Users user;
    private FirebaseAuth mAuth;




    public NotificationCoworker(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


// channel id just un nombre pour instancier la notifiaction
    @NotNull
    @Override
    public Result doWork() {
        mDatabase=FirebaseDatabase.getInstance("https://go4lunch-5272f-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference= mDatabase.getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        databaseReference.child((mAuth.getCurrentUser()).getUid()).get().addOnSuccessListener(dataSnapshot ->  {
            user= dataSnapshot.getValue(Users.class);
        });

        // afficher la notif ici.
        //vérifier si la personne veut afficher les notfications ou non.
        // pour crée un notif channel on a besoin de faire extends Application.
        //Il rappellera à l'utilisateur le nom du restaurant
        //qu'il a choisi, l'adresse, ainsi que la liste des collègues qui viendront avec lui.

        notificationManager = NotificationManagerCompat.from(getApplicationContext());

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                .setSmallIcon(R.drawable.restaurant_icon)
                .setContentTitle(getApplicationContext().getString(R.string.notifcation))
                .setContentText(user.getRestaurantName())
                //.setContentText("Vous allez au"+user.getRestaurantName()+""+user.getAdresseRestaurant())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID, "Channel 1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This is Channel 1");
            NotificationManager manager = getApplicationContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }

        notificationManager.notify(1, notification);

        return Result.success();


    }


}
