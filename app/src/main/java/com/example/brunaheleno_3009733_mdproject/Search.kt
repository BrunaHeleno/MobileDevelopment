package com.example.brunaheleno_3009733_mdproject

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import android.widget.TextView
import android.view.inputmethod.EditorInfo



class Search : ComponentActivity() {
    private lateinit var searchInputBtn: EditText
    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchInputBtn = findViewById(R.id.inputSearch)


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