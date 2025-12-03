/* Mobile Development Project
   Bruna Heleno 3009733
 */
package com.example.brunaheleno_3009733_mdproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column

class Files : ComponentActivity() {

    private var home = R.string.home
    private var search = R.string.search
    private var camera = R.string.camera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                //Menu on top of the screen
                Menu(
                    home,
                    MainActivity::class.java,
                    search,
                    Search::class.java,
                    camera,
                    Camera::class.java
                )
            }
        }
    }
}
        //#TODO: DATABASE QUERY TO SHOW RESULTS HERE
        //#TODO: CREATE FILTERS (JUST VIDEO / IMAGE - ALPHABETIC ORDER)
