/* Mobile Development Project
   Bruna Heleno 3009733
 */

package com.example.brunaheleno_3009733_mdproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.stockpile.ui.Menu

class Search : ComponentActivity() {
    private var home = R.string.home
    private var camera = R.string.camera
    private var files = R.string.files

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Menu(
                home,
                MainActivity::class.java,
                camera,
                Camera::class.java,
                files,
                Files::class.java
            )
        }
    }
}