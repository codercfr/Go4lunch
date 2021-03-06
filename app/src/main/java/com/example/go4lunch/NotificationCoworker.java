package com.example.go4lunch;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.go4lunch.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class NotificationCoworker extends Worker  {

    private NotificationManagerCompat notificationManager;
    public static final String CHANNEL_1_ID = "channel1";
    private DatabaseReference databaseReference;
    private Users user;
    private final List<String>userName= new ArrayList<>();


    public NotificationCoworker(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


// channel id just un nombre pour instancier la notifiaction
    @NotNull
    @Override
    public Result doWork() {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://go4lunch-5272f-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = mDatabase.getReference("Users");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        databaseReference.child((Objects.requireNonNull(mAuth.getCurrentUser())).getUid()).get().addOnSuccessListener(dataSnapshot -> {
            user = dataSnapshot.getValue(Users.class);
            try {
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Users children = ds.getValue(Users.class);
                            assert children != null;
                            if (children.getRestaurantName().equals(user.getRestaurantName())) {
                                userName.add(children.getUsername());
                            }
                        }

                        notificationManager = NotificationManagerCompat.from(getApplicationContext());
                        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                                .setSmallIcon(R.drawable.restaurant_icon)
                                .setContentTitle(getApplicationContext().getString(R.string.notifcation))
                                .setContentText(user.getUsername() + " " + user.getRestaurantName() + " autres coll??gues :" + userName.toString())
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
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    }
                };
                databaseReference.orderByChild("restaurantName").equalTo(user.getRestaurantName()).addListenerForSingleValueEvent(eventListener);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        return Result.success();
    }
}



