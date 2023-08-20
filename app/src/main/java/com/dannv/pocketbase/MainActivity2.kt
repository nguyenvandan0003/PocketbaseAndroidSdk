package com.dannv.pocketbase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dannv.pocketbase.ui.theme.MyApplicationTheme
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MyApplicationTheme {
				// Display the current time using the CurrentTimeScreen
				CurrentTimeScreen()
			}
		}
	}
}

@Composable
fun CurrentTimeScreen() {
	val currentTime by rememberUpdatedState(newValue = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()))

	Box(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp),
		contentAlignment = Alignment.Center
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier.fillMaxWidth()
		) {
			Text(
				text = "Current Time",
				style = MaterialTheme.typography.bodyLarge,
				color = MaterialTheme.colorScheme.primary,
				modifier = Modifier.padding(bottom = 16.dp)
			)

			Text(
				text = currentTime,
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.secondary,
				textAlign = TextAlign.Center
			)
		}
	}
}
