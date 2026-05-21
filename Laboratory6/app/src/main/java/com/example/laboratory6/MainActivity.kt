package com.example.laboratory6

import DataStoreManager
import android.Manifest
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Telephony
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var smsReceiver: SmsReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init SMS Detector
        smsReceiver = SmsReceiver()
        val dataStoreManager = DataStoreManager(this)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppScreen(dataStoreManager)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(smsReceiver, IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(smsReceiver)
    }
}

@Composable
fun AppScreen(dataStoreManager: DataStoreManager) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val savedText by dataStoreManager.userTextFlow.collectAsState(initial = "")
    var inputText by remember { mutableStateOf("") }

    LaunchedEffect(savedText) {
        if (savedText.isNotEmpty() && inputText.isEmpty()) {
            inputText = savedText
        }
    }

    // Perms
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all { it.value }
        if (!granted) {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    // Ask for Perms
    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS
            )
        )
    }

    // Interface
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Input
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Input Text") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Save Button
        Button(onClick = {
            coroutineScope.launch {
                dataStoreManager.saveUserText(inputText)
                Toast.makeText(context, "Data saved", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("SAVE DATA")
        }
    }
}