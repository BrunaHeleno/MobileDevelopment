/* Mobile Development
   Bruna Heleno 3009733
 */

package com.example.brunaheleno_3009733_mdproject.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brunaheleno_3009733_mdproject.R

//this method creates a rounded corner button
@Composable
fun ButtonsApp(text: String, onClick:() -> Unit, modifier: Modifier = Modifier, backgroundColor: Color? = null, contentColor: Color? = null, borderColor: Color? = null, type: String? = "small"){

    //default colors
    val backC = backgroundColor ?: colorResource(id = R.color.dark_blue)
    val textC = contentColor ?: colorResource(id = R.color.white)
    val borderC = borderColor ?: colorResource(id = R.color.black)

    val corner = 10.dp //default corner round

    //sizes for small button - used on Menu | Cancel/Save
    var height = 50.dp
    var width = 90.dp
    var textSize = 15.sp

    //size for big buttons - Home and Camera Screen
    if(type == "big"){
        height = 70.dp
        width = 300.dp
        textSize = 30.sp
    }


    Button(
        onClick = onClick,

        modifier = modifier
            .height(height)
            .width(width)
            .border(3.dp, borderC, RoundedCornerShape(corner)),

        colors = ButtonDefaults.buttonColors(
            containerColor = backC,
            contentColor = textC
        ),

        shape = RoundedCornerShape(corner),
        contentPadding = PaddingValues(10.dp) //inside
    ) {
        Text(
            text = text.uppercase(),
            fontSize = textSize,
            fontWeight = FontWeight.Bold
        )
    }
}