/* Mobile Development Project
   Bruna Heleno 3009733
 */

package com.example.brunaheleno_3009733_mdproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.Intent
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brunaheleno_3009733_mdproject.R.color
import com.example.brunaheleno_3009733_mdproject.ui.ButtonsAppBig

//this class represents the home screen and redirects to the other activities
class MainActivity : ComponentActivity() {
    private val spaceBetween = 15.dp // space between items in the Column layout - Spacer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            //putting name of the app and buttons centered in the screen
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                //name of the App with shadow
                Text(
                    text = stringResource(id = R.string.app_title),
                    color = colorResource(id = color.light_purple),
                    fontSize = 70.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        shadow = Shadow(
                            colorResource(id = color.dark_blue),
                            offset = Offset(10f, 10f),
                            blurRadius = 3f
                        )
                    )
                )
                Spacer(modifier = Modifier.height(50.dp))

                //button Search redirects to Search screen
                ButtonsAppBig(
                    text = stringResource(id = R.string.search),
                    onClick = {
                        startActivity(Intent(this@MainActivity, Search::class.java))
                    }
                )
                Spacer(modifier = Modifier.height(spaceBetween))

                //button Camera redirects to Camera screen
                ButtonsAppBig(
                    text = stringResource(id = R.string.camera),
                    onClick = {
                        startActivity(Intent(this@MainActivity, Camera::class.java))
                    }
                )
                Spacer(modifier = Modifier.height(spaceBetween))

                ButtonsAppBig(
                    text = stringResource(id = R.string.files),
                    onClick = {
                        startActivity(Intent(this@MainActivity, Files::class.java))
                    }
                )
                Spacer(modifier = Modifier.height(spaceBetween))

                ButtonsAppBig(
                    text = stringResource(id = R.string.exit),
                    onClick = {
                        finishAffinity()
                    }
                )
            }
        }
    }
}