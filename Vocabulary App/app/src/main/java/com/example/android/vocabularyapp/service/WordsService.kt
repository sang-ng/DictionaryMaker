package com.example.android.vocabularyapp.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.speech.tts.TextToSpeech
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import com.example.android.vocabularyapp.R
import com.example.android.vocabularyapp.ui.learn.LearnActivity
import java.util.*

class WordsService : Service(), TextToSpeech.OnInitListener {

    private lateinit var textToSpeech: TextToSpeech
    private var input: String? = null

    override fun onCreate() {
        textToSpeech = TextToSpeech(this, this)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {


        input = intent.getStringExtra("inputExtra")
        val notificationIntent = Intent(this, LearnActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setContentTitle("Example Service")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .build()


        startForeground(1, notification)
        //do heavy work on a background thread
        //stopSelf();
        return START_NOT_STICKY
    }

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onInit(status: Int) {
        val result: Int = textToSpeech.setLanguage(Locale.US)
        if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
            textToSpeech.speak(input, TextToSpeech.QUEUE_FLUSH, null)
        }
    }

    override fun onDestroy() {
        textToSpeech.stop()
        textToSpeech.shutdown()

        super.onDestroy()
    }
}