/* Mobile Development
   Bruna Heleno 3009733
 */

package com.example.brunaheleno_3009733_mdproject

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockpile.R


//this method creates a rounded corner button some of the parameters have default values - Default Size referent Menu on top of the screen
@Composable
fun ButtonsApp(text: String, onClick:() -> Unit, modifier: Modifier = Modifier, backgroundColor: Color? = null, contentColor: Color? = null, borderColor: Color? = null, corner: Dp = 10.dp, height: Dp = 50.dp, width: Dp = 90.dp, textSize: TextUnit = 15.sp){

    //default colors
    val backC = backgroundColor ?: colorResource(id = R.color.dark_blue)
    val textC = contentColor ?: colorResource(id = R.color.white)
    val borderC = borderColor ?: colorResource(id = R.color.black)

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