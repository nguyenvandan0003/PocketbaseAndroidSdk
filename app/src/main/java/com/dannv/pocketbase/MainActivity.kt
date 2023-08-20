@file:OptIn(ExperimentalMaterial3Api::class)

package com.dannv.pocketbase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dannv.pocketbase.model.LoginResponse
import com.dannv.pocketbase.model.Param
import com.dannv.pocketbase.ui.theme.MyApplicationTheme
import com.dannv.pocketbasesdk.api.PocketBaseServices
import com.dannv.pocketbasesdk.api.createRequestBody
import com.dannv.pocketbasesdk.impl.PocketbaseImpl
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	@Inject
	lateinit var p: PocketbaseImpl

	private val mViewmodel: MainViewmodel by viewModels()

	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MyApplicationTheme {
				// A surface container using the 'background' color from the theme
				Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
					LoginScreen(p) { acc, pass ->
						mViewmodel.login(acc, pass)
					}
				}
			}
		}

		mViewmodel.apiStatus.onEach {
			// show loading
		}.launchIn(lifecycleScope)
	}
}
@Composable
fun LoginScreen(p: PocketbaseImpl, callBackOnClick: ((String, String) -> Unit)) {
	var text by rememberSaveable { mutableStateOf("") }
	var password by rememberSaveable { mutableStateOf("") }

	Box(modifier = Modifier
		.fillMaxSize()
		.padding(16.dp), contentAlignment = Alignment.Center
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier.fillMaxWidth()
		) {
			Image(
				painter = painterResource(id = R.drawable.ic_launcher_foreground),
				contentDescription = null,
				modifier = Modifier
					.size(100.dp)
					.padding(bottom = 16.dp),
				contentScale = ContentScale.Fit
			)
			Text(
				modifier = Modifier
					.fillMaxWidth()
					.padding(0.dp, 10.dp),
				text = stringResource(id = R.string.login))

			OutlinedTextField(
				modifier = Modifier.fillMaxWidth(),
				value = text,
				onValueChange = {
					text = it
				},
				placeholder = {
					Text(text = "Input your account")
				},
				maxLines = 1
			)

			Text(text = stringResource(id = R.string.password),
				  modifier = Modifier
					  .fillMaxWidth()
					  .padding(0.dp, 10.dp))

			OutlinedTextField(
				modifier = Modifier.fillMaxWidth(),
				value = password,
				placeholder = {
					Text(text = "Input password")
				},
				onValueChange = {
					password = it
				})

			Button(
				onClick = {
					callBackOnClick.invoke(text, password)
				},
				modifier = Modifier
					.fillMaxWidth()
					.padding(8.dp)
			) {
				Text(text = "Login")
			}
		}
	}
}

@Composable
fun LoadingDialog(
	isLoading: Boolean,
	onDismiss: () -> Unit
) {
	if (isLoading) {
		Dialog(onDismissRequest = { /* Disable dismissing by clicking outside */ }) {
			Box(
				modifier = Modifier
					.padding(16.dp)
					.fillMaxSize(),
				contentAlignment = Alignment.Center
			) {
				Card(
					modifier = Modifier.wrapContentSize(),
				) {
					Column(
						modifier = Modifier
							.padding(16.dp)
							.fillMaxWidth(),
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						CircularProgressIndicator()
						Spacer(modifier = Modifier.height(16.dp))
						Text(text = "Loading...")
					}
				}
			}
		}
	}
}