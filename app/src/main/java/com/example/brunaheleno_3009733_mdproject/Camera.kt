/* Mobile Development Project
   Bruna Heleno 3009733
 */

package com.example.brunaheleno_3009733_mdproject

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity


class Camera : ComponentActivity() {
    private lateinit var homeBtn: Button
    private lateinit var searchBtn: Button
    private lateinit var closeBtn: Button
    private lateinit var cameraBtn: Button
    private lateinit var cancelBtn: Button
    private lateinit var saveBtn: Button
    private lateinit var titleField: EditText
    private lateinit var albumName: EditText


    private lateinit var result: TextView //just to text input

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        //Menu Buttons
        homeBtn = findViewById(R.id.home)
        homeBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
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

        //open camera
        cameraBtn = findViewById(R.id.activateCamera)
        //#TODO: CONNECT CAMERA FROM MOBILE DEVICE

        //input title
        titleField = findViewById(R.id.titleFile)
        //#TODO: QUERY TO INPUT FILE NAME

        //input album name - category
        albumName = findViewById(R.id.albumFile)
        //#TODO: QUERY TO INPUT ALBUM NAME

        //discard file
        cancelBtn = findViewById(R.id.cancel)
        //#TODO: QUERY TO DISCARD FILE
        //#TODO: CHANGE COLOR OF CANCEL BUTTON

        saveBtn = findViewById(R.id.save)
        //#TODO: QUERY TO SAVE FILE

    }
}