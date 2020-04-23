package com.example.ad340

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // referencing elements to variables
        val zipcodeEditText: EditText = findViewById(R.id.zipcodeEditText)
        val enterButton: Button = findViewById(R.id.enterButton)
        //adding click listener to the button
        enterButton.setOnClickListener {
            //write "this"and context will pop, same with text just write the value ""
            //Toast.makeText(this,"Button Clicked!",Toast.LENGTH_SHORT).show()
            val zipcode: String = zipcodeEditText.text.toString()
            if (zipcode.length !=5){
                Toast.makeText(this,R.string.zipcode_entry_error,Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this,zipcode,Toast.LENGTH_SHORT).show()
            }

        }
    }
}
