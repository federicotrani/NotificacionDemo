package com.example.notificaciondemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    NotificationCompat.Builder builder;
    int notificationId  = 1;
    // id del channel
    String channelId="my_channel_1";

    Button btnNotificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNotificar = findViewById(R.id.btnNotificar);
        btnNotificar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);

        builder = new NotificationCompat.Builder(this, null);

        //Desde Android Oreo (26) se require definir CHANNELS
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            // nombre visible al usuario del CHANNEL
            CharSequence name = "Ofertas";

            // descripcion visible del CHANNEL
            String description = "Comunicación de Ofertas a Usuarios";
            int importantce = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(channelId, name, importantce);

            // Configuracion del CHANNEL
            channel.setDescription(description);
            channel.enableLights(true);

            // Otras configuraciones
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});
            notificationManager.createNotificationChannel(channel);

            builder = new NotificationCompat.Builder(this, channelId);
        }

        // configuración igual para todas las versiones de SDK
        builder.setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("Novedades de Ofertas")
                .setContentText("Detalle de la Oferta.");

        // builder.setChannelId(channelId);

        // lanzamos la notificacion
        notificationManager.notify(notificationId, builder.build());
    }
}
