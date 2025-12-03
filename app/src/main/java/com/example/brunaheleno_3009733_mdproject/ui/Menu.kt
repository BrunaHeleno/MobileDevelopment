/* Mobile Development
   Bruna Heleno 3009733
 */

package com.example.brunaheleno_3009733_mdproject

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.brunaheleno_3009733_mdproject.R
import kotlin.system.exitProcess


/*  This function creates the Menu on top of the screen.
    It has 4 buttons - 3 are gonna change according to screen and the last one is the Exit button
    It receives three Int - referent to string -> Text inside of the button and 3 activities to redirect to screen
 */

@Composable
fun Menu(btn1: Int, direct1: Class<out Activity>, btn2: Int, direct2: Class<out Activity>, btn3: Int, direct3: Class<out Activity>){
    val spaceBetween = 5.dp // space between items in the Row layout - Spacer
    val context = LocalContext.current //this variable is to be able to start an activity inside of the composable method

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.dark_green))
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Spacer(modifier = Modifier.width(2.dp))

        ButtonsApp(
            text = stringResource(btn1),
            onClick = {
                context.startActivity(Intent(context, direct1))
            }
        )
        Spacer(modifier = Modifier.width(spaceBetween))

        ButtonsApp(
            text = stringResource(btn2),
            onClick = {
                context.startActivity(Intent(context, direct2))
            }
        )
        Spacer(modifier = Modifier.width(spaceBetween))

        ButtonsApp(
            text = stringResource(btn3),
            onClick = {
                context.startActivity(Intent(context, direct3))
            }
        )
        Spacer(modifier = Modifier.width(spaceBetween))

        //button exit - close application
        ButtonsApp(
            text = stringResource(id = R.string.exit),
            onClick = {
                exitProcess(0)
            }
        )
        Spacer(modifier = Modifier.width(2.dp))
    }
}