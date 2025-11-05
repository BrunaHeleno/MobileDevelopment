package com.example.brunaheleno_3009733_mdproject

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import android.content.Intent


class MainActivity : ComponentActivity() {
    //creating variables for buttons
    private lateinit var searchButton: Button
    private lateinit var cameraButton: Button
    private lateinit var filesButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        //searchButton redirects to Search Activity
        searchButton = findViewById(R.id.search)
        searchButton.setOnClickListener {
            val intent = Intent(this, Search::class.java)
            startActivity(intent)
        }

        //cameraButton open camera and redirect to SaveFiles
        cameraButton = findViewById(R.id.camera)
        cameraButton.setOnClickListener{
            val intent = Intent(this, Search::class.java)
            startActivity(intent)
        }

        //filesButton redirects to Files Activity
        filesButton = findViewById(R.id.files)
        filesButton.setOnClickListener{
            val intent = Intent(this, Search::class.java)
            startActivity(intent)
        }
    }
}
