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

import com.google.firebase.events.EventHandler;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.example.go4lunch.AppNotification.CHANNEL_1_ID;


public class NotificationCoworker extends Worker  {

    private NotificationManagerCompat notificationManager;

    public NotificationCoworker(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


// channel id just un nombre pour instancier la notifiaction
    @NotNull
    @Override
    public Result doWork() {

        // afficher la notif ici.
        //vérifier si la personne veut afficher les notfications ou non.
        // pour crée un notif channel on a besoin de faire extends Application.
        //Il rappellera à l'utilisateur le nom du restaurant
        //qu'il a choisi, l'adresse, ainsi que la liste des collègues qui viendront avec lui.


    /*    notificationManager = NotificationManagerCompat.from(this);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setContentTitle(R.string.notifcation)
                .setContentText(R.string.notifcation)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);*/

        return Result.success();
    }


}
