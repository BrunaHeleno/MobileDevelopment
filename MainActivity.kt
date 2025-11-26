/* Mobile Development
   Bruna Heleno 3009733
 */

package com.example.brunaheleno_3009733_mdproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
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
import com.example.stockpile.ui.ButtonsApp
import kotlin.system.exitProcess
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockpile.R.color


//this class represents the Home Screen
class MainActivity : ComponentActivity() {
    private var spaceBetween = 15.dp // space between items in the Column layout - Spacer
    private var heightBtn = 70.dp //height for button
    private var widthBtn = 200.dp //width for button
    private var textSizeBtn = 30.sp //text size inside button

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
                ButtonsApp(
                    text = stringResource(id = R.string.search),
                    onClick = {
                        startActivity(Intent(this@MainActivity, Search::class.java))
                    },
                    height = heightBtn,
                    width = widthBtn,
                    textSize = textSizeBtn
                )
                Spacer(modifier = Modifier.height(spaceBetween))

                //button Camera redirects to Camera screen
                ButtonsApp(
                    text = stringResource(id = R.string.camera),
                    onClick = {
                        startActivity(Intent(this@MainActivity, Camera::class.java))
                    },
                    height = heightBtn,
                    width = widthBtn,
                    textSize = textSizeBtn
                )
                Spacer(modifier = Modifier.height(spaceBetween))

                ButtonsApp(
                    text = stringResource(id = R.string.files),
                    onClick = {
                        startActivity(Intent(this@MainActivity, Files::class.java))
                    },
                    height = heightBtn,
                    width = widthBtn,
                    textSize = textSizeBtn
                )
                Spacer(modifier = Modifier.height(spaceBetween))

                ButtonsApp(
                    text = stringResource(id = R.string.exit),
                    onClick = {
                        exitProcess(0)
                    },
                    height = heightBtn,
                    width = widthBtn,
                    textSize = textSizeBtn
                )
            }
        }
    }
}