package com.calyrsoft.ucbp1.features.notification.data.service
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.calyrsoft.ucbp1.MainActivity
import com.calyrsoft.ucbp1.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseService : FirebaseMessagingService() {
    companion object {
        const val TAG = "FirebaseService"
        private const val PENDING_INTENT_REQUEST_CODE = 12345 // Fijo, no aleatorio

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "Mensaje recibido: ${remoteMessage.data}")

        val data = remoteMessage.data

        // DEBUG: Log los datos recibidos
        data.forEach { (key, value) ->
            Log.d(TAG, "Data: $key = $value")
        }

        showNotification(remoteMessage, data)
    }

    private fun showNotification(remoteMessage: RemoteMessage, data: Map<String, String>) {
        createNotificationChannel()

        val title = remoteMessage.notification?.title ?: getString(R.string.app_name)
        val body = remoteMessage.notification?.body ?: ""

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

            // **CORRECCIÓN IMPORTANTE**: Poner TODOS los datos en el intent
            // para que el ViewModel pueda procesarlos correctamente
            putExtra("navigateTo", data["navigateTo"])
            putExtra("click_action", data["click_action"])

            // Si hay deep_link, también lo añadimos
            data["deep_link"]?.let { deepLink ->
                this.data = android.net.Uri.parse(deepLink)
                action = Intent.ACTION_VIEW
            }

            // DEBUG: Log el intent que se está creando
            Log.d(TAG, "Intent creado - navigateTo: ${data["navigateTo"]}, click_action: ${data["click_action"]}")
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            PENDING_INTENT_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, "default_channel")
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Cambia por tu icono
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(generateNotificationId(), notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "default_channel",
                "Notificaciones principales",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Canal para notificaciones principales"
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun generateRequestCode(): Int {
        return System.currentTimeMillis().toInt() and 0xffff
    }

    private fun generateNotificationId(): Int {
        return System.currentTimeMillis().toInt() and 0xffff
    }
}