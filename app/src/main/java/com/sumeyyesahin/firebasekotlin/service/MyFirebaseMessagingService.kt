package com.sumeyyesahin.firebasekotlin.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sumeyyesahin.firebasekotlin.R
import com.sumeyyesahin.firebasekotlin.view.FeedActivity


const val channel_id = "notification_channel"
const val channel_name = "com.sumeyyesahin.firebasekotlin"
class MyFirebaseMessagingService: FirebaseMessagingService() { // anlamı: MyFirebaseMessagingService, FirebaseMessagingService ile oluşturuldu (Firebase mesajlaşma hizmeti)

    override fun onMessageReceived(remoteMessage: RemoteMessage) { // anlamı: onMessageReceived, uzaktan mesaj alındığında çalışır (uzaktan mesaj alındı)
        if(remoteMessage.notification != null){ // anlamı: remoteMessage.notification, null değilse (uzaktan mesaj bildirimi)
            val title = remoteMessage.notification!!.title // anlamı: title, uzaktan mesaj bildirim başlığı (uzaktan mesaj bildirim başlığı)
            val message = remoteMessage.notification!!.body // anlamı: message, uzaktan mesaj bildirim içeriği (uzaktan mesaj bildirim içeriği)

            generateNotification(title!!, message!!) // anlamı: generateNotification, title ve message ile oluşturuldu (bildirim oluştur)
        }
    }


    @SuppressLint("RemoteViewLayout") // anlamı: RemoteViewLayout için kullanılan bir uyarıyı kaldırır
    fun getRemoteView(title: String, message: String): RemoteViews { // anlamı: getRemoteView, title ve message ile oluşturuldu (uzaktan görünümü al)
        val remoteView = RemoteViews(this.packageName, R.layout.notification) // anlamı: remoteView, bu paket adı ve bildirim ile oluşturuldu (uzaktan görünümü al)

        remoteView.setTextViewText(R.id.title, title) // anlamı: title, title ile oluşturuldu (başlık)
        remoteView.setTextViewText(R.id.message, message) // anlamı: message, message ile oluşturuldu (mesaj)
        remoteView.setImageViewResource(R.id.app_logo, R.drawable.bag) // anlamı: app_logo, bag ile oluşturuldu (uygulama logosu)

        return remoteView // anlamı: remoteView, döndürüldü (uzaktan görünümü al)
    }

    fun generateNotification(title: String, message: String) { // anlamı: generateNotification, title ve message ile oluşturuldu (bildirim oluştur)

        val intent = Intent(this, FeedActivity::class.java) // anlamı: intent, bu ve FeedActivity ile oluşturuldu (bildirim oluştur) // FeedActivity: Anasayfa
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Intent.FLAG_ACTIVITY_CLEAR_TOP: En üstteki aktiviteyi temizler ve yeni bir aktiviteyi başlatır

        val pendingIntent = PendingIntent.getActivity( // anlamı: pendingIntent, bu ve FeedActivity ile oluşturuldu (bekleyen bildirim oluştur)
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE // PendingIntent.FLAG_ONE_SHOT: Yalnızca bir kez kullanılabilir bir PendingIntent oluşturur // PendingIntent.FLAG_IMMUTABLE: PendingIntent'in içeriğinin değiştirilemeyeceğini belirtir
        )

        var notificationBuilder = NotificationCompat.Builder(this, channel_id) // anlamı: notificationBuilder, bu ve channel_id ile oluşturuldu (bildirim oluştur)
            .setOnlyAlertOnce(true) // anlamı: setOnlyAlertOnce, true ile oluşturuldu (yalnızca bir kez uyarı)
            .setSmallIcon(R.drawable.bag) // anlamı: setSmallIcon, bag ile oluşturuldu (küçük simge)
            .setAutoCancel(true) // anlamı: setAutoCancel, true ile oluşturuldu (otomatik iptal)
            .setContentIntent(pendingIntent) // anlamı: setContentIntent, pendingIntent ile oluşturuldu (içerik bildirimi)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000)) // anlamı: setVibrate, longArrayOf(1000, 1000, 1000, 1000, 1000) ile oluşturuldu (titreşim)

        notificationBuilder = notificationBuilder.setContent(getRemoteView(title, message))
        // anlamı: notificationBuilder, notificationBuilder.setContent(getRemoteView(title, message)) ile oluşturuldu (bildirim oluştur)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager // anlamı: notificationManager, getSystemService(NOTIFICATION_SERVICE) ile oluşturuldu (bildirim yöneticisi)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // anlamı: Build.VERSION.SDK_INT, Build.VERSION_CODES.O'dan büyükse (sürüm kodu)
            val channel = NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_HIGH)
            // anlamı: channel, channel_id, channel_name ve NotificationManager.IMPORTANCE_HIGH ile oluşturuldu (bildirim kanalı)

            notificationManager.createNotificationChannel(channel) // anlamı: notificationManager, channel ile oluşturuldu (bildirim yöneticisi)
        }

        notificationManager.notify(0, notificationBuilder.build()) // anlamı: notificationManager, 0 ve notificationBuilder.build() ile oluşturuldu (bildirim yöneticisi)
    }

}