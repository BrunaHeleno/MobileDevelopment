package com.example.brunaheleno_3009733_mdproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import android.widget.TextView
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton

class Search : ComponentActivity() {
    private lateinit var searchInputBtn: EditText
    private lateinit var addFile: ImageButton
    private lateinit var backHome: ImageButton
    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchInputBtn = findViewById(R.id.inputSearch)
        addFile = findViewById(R.id.addFile)
        backHome = findViewById(R.id.backHome)

        //#TODO changeactivity
        addFile.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        backHome.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        //testing input
        result = findViewById(R.id.result)

        searchInputBtn.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                 result.text = searchInputBtn.text.toString()
                true
            }else{
                false
            }
        }

    }
}