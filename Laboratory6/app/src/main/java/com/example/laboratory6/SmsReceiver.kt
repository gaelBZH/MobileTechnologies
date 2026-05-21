package com.example.laboratory6

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.widget.Toast

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Si l'événement reçu est bien un SMS
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            // Afficher le Toast "Message received"
            Toast.makeText(context, "Message received", Toast.LENGTH_SHORT).show()
        }
    }
}