package com.example.laboratory4

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent{
            val context = LocalContext.current

            Column{
                // Text and Image
                Text(text = "First activity!")
                Image(
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = "Krakow"
                )

                // Button to Activity 2
                Button(onClick = {
                    val intent = Intent(context, SecondActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Text(text = "START NEW ACTIVITY")
                }
            }
        }
    }
}