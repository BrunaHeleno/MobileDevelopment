/* Mobile Development Project
   Bruna Heleno 3009733
 */

package com.example.brunaheleno_3009733_mdproject

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Search : ComponentActivity() {
    private lateinit var homeBtn: Button
    private lateinit var cameraBtn: Button
    private lateinit var filesBtn: Button
    private lateinit var closeBtn: Button
    private lateinit var searchInputBtn: EditText
    //private lateinit var recycler: RecyclerView

    private lateinit var result: TextView //just to text input

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

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

        filesBtn = findViewById(R.id.fileMenu)
        filesBtn.setOnClickListener {
            val intent = Intent(this, Files::class.java)
            startActivity(intent)
        }

        closeBtn = findViewById(R.id.close)
        closeBtn.setOnClickListener {
            finishAffinity()
            System.exit(0)
        }

        //search
        result = findViewById(R.id.result)
        searchInputBtn = findViewById(R.id.inputSearch)
        searchInputBtn.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                result.text = searchInputBtn.text.toString()
                true
            }else{
                false
            }
        }

        //recycleView to show results
        //recycler = findViewById(R.id.recycler)
        //#TODO: DATABASE QUERY TO SHOW RESULTS HERE
        //#TODO: CREATE ADAPTER TO CHANGE CONTENT
        //#TODO: DELETE TEXTVIEW when done

    }
}