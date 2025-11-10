/* Mobile Development Project
   Bruna Heleno 3009733
 */
package com.example.brunaheleno_3009733_mdproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class Files : ComponentActivity() {

    private lateinit var homeBtn: Button
    private lateinit var cameraBtn: Button
    private lateinit var searchBtn: Button
    private lateinit var closeBtn: Button

    //private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_files)

        //Menu Buttons
        homeBtn = findViewById(R.id.home)
        homeBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        cameraBtn = findViewById(R.id.cameraMenu)
        cameraBtn.setOnClickListener {
            val intent = Intent(this, Camera::class.java)
            startActivity(intent)
        }

        searchBtn = findViewById(R.id.searchMenu)
        searchBtn.setOnClickListener {
            val intent = Intent(this, Search::class.java)
            startActivity(intent)
        }

        closeBtn = findViewById(R.id.close)
        closeBtn.setOnClickListener {
            finishAffinity()
            System.exit(0)
        }


        //recycleView to show results
        //recycler = findViewById(R.id.recycler)
        //#TODO: DATABASE QUERY TO SHOW RESULTS HERE
        //#TODO: CREATE ADAPTER TO CHANGE CONTENT
        //#TODO: CREATE FILTERS (JUST VIDEO / IMAGE - ALPHABETIC ORDER)

    }
}