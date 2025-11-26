/* Mobile Development Project
   Bruna Heleno 3009733
 */

package com.example.brunaheleno_3009733_mdproject

import Menu
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column



class Camera : ComponentActivity() {
    private var home = R.string.home
    private var search = R.string.search
    private var files = R.string.files

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                //Menu on top of the screen
                Menu(home, MainActivity::class.java,search, Search::class.java, files, Files::class.java)
            }
        }
    }
}

