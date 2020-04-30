package com.example.ad340

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val forecastRepository = ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // referencing elements to variables
        val zipcodeEditText: EditText = findViewById(R.id.zipcodeEditText)
        val enterButton: Button = findViewById(R.id.enterButton)

        //adding click listener to the button
        enterButton.setOnClickListener {
            //write "this"and context will pop, same with text just write the value "", toast - to check if it works
            //Toast.makeText(this,"Button Clicked!",Toast.LENGTH_SHORT).show()
            val zipcode: String = zipcodeEditText.text.toString()
            if (zipcode.length !=5){
                Toast.makeText(this,R.string.zipcode_entry_error,Toast.LENGTH_SHORT).show()
            } else{
                //following toast will show what we entered
                //Toast.makeText(this,zipcode,Toast.LENGTH_SHORT).show()
                forecastRepository.loadForecast(zipcode)
            }
        }

        val forecastList: RecyclerView = findViewById(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(this)
        val dailyForecastAdapter = DailyForecastAdapter(){forecastItem ->
            //wherever is "forecastItem" was "it" which represents a value pass to lambda
            val msg = getString(R.string.forecast_clicked_format,forecastItem.temp, forecastItem.description)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        forecastList.adapter = dailyForecastAdapter



        val weeklyForecastObserver = Observer<List<DailyForecast>> {forecastItems ->
            //update our list adapter
            //Toast.makeText(this,"Loaded Items", Toast.LENGTH_SHORT).show()
            dailyForecastAdapter.submitList(forecastItems)

        }
        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)
    }
}
